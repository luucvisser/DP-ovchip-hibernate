package daoHibernate;

import dao.ReizigerDAO;
import domein.Adres;
import domein.OVChipkaart;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;
    private Transaction transaction;

    // Constructor (krijgt de sessie mee zodat die overal in de file gebruikt kan worden)
    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }


    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        this.session.save(reiziger);
        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        this.session.update(reiziger);
        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        this.session.delete(reiziger);

        this.session.delete(reiziger.getAdres());

        for (OVChipkaart ov : reiziger.getOVChipkaarten()) {
            this.session.delete(ov);
        }

        return true;
    }

    // Vindt een reiziger aan de hand van een ID
    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger reiziger = session.get(Reiziger.class, id);
        return reiziger;
    }

    // Vindt een reiziger of meerdere reiziger aan de hand van een geboortedatum
    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        Query query = session.createQuery("SELECT * FROM Reiziger WHERE geboortedatum = " + datum, Reiziger.class);
        List<Reiziger> reiziger = query.list();

        return reiziger;
    }

    // Haalt alle reizigers op uit de database
    @Override
    public List<Reiziger> findAll() throws SQLException {
        Query query = session.createQuery("FROM Reiziger ", Reiziger.class);
        List<Reiziger> reiziger = query.list();

        return reiziger;
    }
}
