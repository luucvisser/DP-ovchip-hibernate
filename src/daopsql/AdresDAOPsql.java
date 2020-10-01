package daopsql;

import dao.AdresDAO;
import domein.Adres;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    protected Connection connection;
    private ReizigerDAOPsql rdao;

    // Constructor (krijgt de database connectie mee zodat die overal in de file gebruikt kan worden)
    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Adres adres) throws SQLException {
        int id = adres.getId();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int reiziger_id = adres.getReiziger().getId();

        // Slaat de gegevens op in de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (" + id + ", '" + postcode + "', '" + huisnummer + "', '" + straat + "', '" + woonplaats + "', " + reiziger_id + ")");

        stmt.close();

        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Adres adres) throws SQLException {
        int id = adres.getId();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int reiziger_id = adres.getReiziger().getId();

        // Update de database met de nieuwe gegevens
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE adres SET postcode = '" + postcode + "', huisnummer = '" + huisnummer + "', straat = '" + straat + "', woonplaats = '" + woonplaats + "', reiziger_id = " + reiziger_id + " WHERE adres_id = " + id);

        stmt.close();

        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(Adres adres) throws SQLException {
        int id = adres.getId();

        // Doet de delete query op de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM adres WHERE adres_id = " + id);

        stmt.close();

        return true;
    }

    // Vindt een adres aan de hand van een reiziger
    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId();

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        // Als de select wat opbrengt
        if (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);

            // Zet het adres in een adres object
            Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, reiziger);

            ps.close();
            rs.close();

            return a;
        }
        else {
            return null;
        }
    }

    // Haalt alle addressen op uit de database
    @Override
    public List<Adres> findAll() throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM adres";

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Adres> adressen = new ArrayList<>();

        // Als de select wat opbrengt
        while (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);
            int reiziger_id = rs.getInt(6);

            // Zoekt een reiziger aan de hand van zijn ID en zet hem in een lijst
            // Je hebt namelijk een reiziger object nodig om een adres object aan te maken
            List<Reiziger> lijst = rdao.findAll();

            // Voor elke reiziger in de lijst
            for (var r : lijst) {
                if (r.getId() == reiziger_id) {
                    // Zet het adres in een adres object
                    Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, r);
                    adressen.add(a);
                }
            }
        }

        ps.close();
        rs.close();

        return adressen;
    }

    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }
}
