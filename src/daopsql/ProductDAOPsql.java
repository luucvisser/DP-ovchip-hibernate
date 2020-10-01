package daopsql;

import dao.ProductDAO;
import domein.OVChipkaart;
import domein.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    protected Connection connection;
    private OVChipkaartDAOPsql ovdao;

    // Constructor (krijgt de database connectie mee zodat die overal in de file gebruikt kan worden)
    public ProductDAOPsql(Connection connection) { this.connection = connection; }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Product product) throws SQLException {
        int nummer = product.getNummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        double prijs = product.getPrijs();

        // Slaat de gegevens op in de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (" + nummer + ", '" + naam + "', '" + beschrijving + "', " + prijs + ")");

        // Slaat de relaties van het product op
        if (!product.getOVChipkaarten().isEmpty()) {
            for (OVChipkaart ov : product.getOVChipkaarten()) {
                java.util.Date vandaag = Calendar.getInstance().getTime();
                stmt.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + ov.getNummer() + ", " + product.getNummer() + ", 'actief', '" + vandaag + "')");
            }
        }

        stmt.close();

        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Product product) throws SQLException {
        int nummer = product.getNummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        double prijs = product.getPrijs();

        // Update de database met de nieuwe gegevens
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE product SET naam = '" + naam + "', beschrijving = '" + beschrijving + "', prijs = " + prijs + " WHERE product_nummer = " + nummer);

        // Als het product relaties heeft
        if (!product.getOVChipkaarten().isEmpty()) {
            // Maakt een query die zorgt dat alle relaties van het product uit de database gehaald worden
            String query = "SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer = " + nummer;

            // Voert de query uit op de database
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Maakt een lijst aan waar de kaart_nummers in komen van alle OV chipkaarten uit de query
            List<Integer> kaartnummersUitQuery = new ArrayList<>();

            // Maakt een lijst aan waar de kaart_nummers in komen van alle OV chipkaarten uit de OVChipkaarten lijst van product
            List<Integer> kaartnummersUitLijst = new ArrayList<>();

            // Vul de kaartnummersUitLijst
            for (OVChipkaart OVChipkaart : product.getOVChipkaarten()) {
                kaartnummersUitLijst.add(OVChipkaart.getNummer());
            }

            // Als de query van line 59 resultaat heeft
            while(rs.next()) {
                int kaart_nummer = rs.getInt(1);

                // Vul de kaartnummersUitQuery lijst
                kaartnummersUitQuery.add(kaart_nummer);

                // Als int kaart_nummer ook voorkomt in de kaartnummersUitQuery lijst, dan wordt die uit zowel de kaartnummersUitQuery lijst als de kaartnummersUitLijst lijst verwijderd
                for (OVChipkaart ov : product.getOVChipkaarten()) {
                    if (kaart_nummer == ov.getNummer()) {
                        kaartnummersUitQuery.remove(kaart_nummer);
                        kaartnummersUitLijst.remove(kaart_nummer);
                    }
                }
            }

            // Alle kaart_nummers die nog overblijven in de kaartnummersUitQuery lijst worden uit de database verwijderd
            for (int k : kaartnummersUitQuery) {
                stmt.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + k + " AND product_nummer = " + nummer);
            }

            // Alle kaart_nummers die nog overblijven in de kaartnummersUitLijst lijst worden in de database toegevoegd
            for (int k : kaartnummersUitLijst) {
                java.util.Date vandaag = Calendar.getInstance().getTime();
                stmt.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + k + ", " + nummer + ", 'actief', '" + vandaag + "')");
            }
        }
        // Als het product geen relaties heeft (in het domein), verwijder dan alle bestaande relaties (in de database)
        else {
            stmt.executeUpdate("DELETE FROM ov_chipkaart_product WHERE product_nummer = " + nummer);
        }

        stmt.close();

        return true;
    }

    // Verwijder het meegegeven object uit de database
    @Override
    public boolean delete(Product product) throws SQLException {
        int nummer = product.getNummer();

        // Maakt een statement
        Statement stmt = connection.createStatement();

        // Verwijderd alle relaties van het product
        if (!product.getOVChipkaarten().isEmpty()) {
            for (OVChipkaart ov : product.getOVChipkaarten()) {
                stmt.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + ov.getNummer() + " AND product_nummer = " + nummer);
            }
        }

        // Verwijderd het product zelf
        stmt.executeUpdate("DELETE FROM product WHERE product_nummer = " + nummer);

        stmt.close();

        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException {
        // Maakt een query
        String query = "SELECT *" +
                       "FROM product p " +
                       "INNER JOIN ov_chipkaart_product ovp ON ovp.product_nummer = p.product_nummer " +
                       "WHERE ovp.kaart_nummer =" + ov.getNummer();

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Product> lijst = new ArrayList<>();

        // Als de select wat opbrengt
        while(rs.next()) {
            int nummer = rs.getInt(1);
            String naam = rs.getString(2);
            String beschrijving = rs.getString(3);
            double prijs = rs.getDouble(4);

            // Zet het product in een product object
            Product product = new Product(nummer, naam, beschrijving, prijs);

            // Set de OV chipkaart van het product
            product.setOVChipkaarten(ov);

            lijst.add(product);
        }

        ps.close();
        rs.close();

        return lijst;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM product";

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Product> lijst = new ArrayList<>();

        // Als de select wat opbrengt
        while(rs.next()) {
            int nummer = rs.getInt(1);
            String naam = rs.getString(2);
            String beschrijving = rs.getString(3);
            double prijs = rs.getDouble(4);

            // Zet het product in een product object
            Product product = new Product(nummer, naam, beschrijving, prijs);

            // Set de OV chipkaarten van het product
            List<OVChipkaart> OVChipkaarten = ovdao.findAll();
            if (!OVChipkaarten.isEmpty()) {
                for (OVChipkaart ov : OVChipkaarten) {
                    product.setOVChipkaarten(ov);
                }
            }

            lijst.add(product);
        }

        ps.close();
        rs.close();

        return lijst;
    }

    public void setOvdao(OVChipkaartDAOPsql ovdao) {
        this.ovdao = ovdao;
    }
}