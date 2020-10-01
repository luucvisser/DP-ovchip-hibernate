package daopsql;

import dao.ReizigerDAO;
import domein.Adres;
import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    protected Connection connection;
    private AdresDAOPsql adao;
    private OVChipkaartDAOPsql ovdao;

    // Constructor (krijgt de database connectie mee zodat die overal in de file gebruikt kan worden)
    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        // Slaat de gegevens op in de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (" + id + ", '" + voorletters + "', '" + tussenvoegsel + "', '" + achternaam + "', '" + geboortedatum + "')");

        stmt.close();

        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        // Update de database met de nieuwe gegevens
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE reiziger SET voorletters = '" + voorletters + "', tussenvoegsel = '" + tussenvoegsel + "', achternaam = '" + achternaam + "', geboortedatum = '" + geboortedatum + "' WHERE reiziger_id = " + id);

        stmt.close();

        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();

        // Moet uitgebreidt worden, OV chipkaarten moeten ook verwijderd worden
        // Doet de delete query op de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + id);

        // Verwijderd het adres van de reiziger
        Adres adres = reiziger.getAdres();
        if (adres != null) {
            adao.delete(adres);
        }

        // Verwijderd de OV Chipkaarten van de reiziger
        if (!reiziger.getOVChipkaarten().isEmpty()) {
            for (OVChipkaart ov : reiziger.getOVChipkaarten()) {
                ovdao.delete(ov);
            }
        }

        stmt.close();

        return true;
    }

    // Vindt een reiziger aan de hand van een ID
    @Override
    public Reiziger findById(int id) throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM reiziger WHERE reiziger_id = " + id;

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        // Als de select wat opbrengt
        if (rs.next()) {
            int reiziger_id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            // Zet de reiziger in een reiziger object
            Reiziger r = new Reiziger(reiziger_id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            // Set het adres van de reiziger
            Adres adres = adao.findByReiziger(r);
            if (adres != null) {
                r.setAdres(adres);
            }

            // Set de OV chipkaarten van de reiziger
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            if (!OVChipkaarten.isEmpty()) {
                for (OVChipkaart ov : OVChipkaarten) {
                    r.setOVChipkaarten(ov);
                }
            }

            ps.close();
            rs.close();

            return r;
        }
        else {
                return null;
        }
    }

    // Vindt een reiziger of meerdere reiziger aan de hand van een geboortedatum
    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Reiziger> lijst = new ArrayList<>();

        // Als de select wat opbrengt
        while (rs.next()) {
            int id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            // Zet de reiziger in een reiziger object
            Reiziger r = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            // Set het adres van de reiziger
            Adres adres = adao.findByReiziger(r);
            if (adres != null) {
                r.setAdres(adres);
            }

            // Set de OV chipkaarten van de reiziger
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            if (!OVChipkaarten.isEmpty()) {
                for (OVChipkaart ov : OVChipkaarten) {
                    r.setOVChipkaarten(ov);
                }
            }

            lijst.add(r);
        }

        ps.close();
        rs.close();

        return lijst;
    }

    // Haalt alle reizigers op uit de database
    @Override
    public List<Reiziger> findAll() throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM reiziger";

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Reiziger> lijst = new ArrayList<>();

        // Als de select wat opbrengt
        while (rs.next()) {
            int id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            // Zet de reiziger in een reiziger object
            Reiziger r = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            // Set het adres van de reiziger
            Adres adres = adao.findByReiziger(r);
            if (adres != null) {
                r.setAdres(adres);
            }

            // Set de OV chipkaarten van de reiziger
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            if (!OVChipkaarten.isEmpty()) {
                for (OVChipkaart ov : OVChipkaarten) {
                    r.setOVChipkaarten(ov);
                }
            }

            lijst.add(r);
        }

        ps.close();
        rs.close();

        return lijst;
    }

    public void setAdao(AdresDAOPsql adao) {
        this.adao = adao;
    }

    public void setOvdao(OVChipkaartDAOPsql ovdao) {
        this.ovdao = ovdao;
    }
}
