package daoHibernate;

import dao.AdresDAO;
import domein.Adres;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;


    // Constructor (krijgt de sessie mee zodat die overal in de file gebruikt kan worden)
    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Adres adres) throws SQLException {
        session.save(adres);
        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Adres adres) throws SQLException {
        session.update(adres);
        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(Adres adres) throws SQLException {
        session.delete(adres);
        return true;
    }

    // Vindt een adres aan de hand van een reiziger
    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres adres = session.get(Adres.class, reiziger.getId());
        return adres;
    }

    // Haalt alle addressen op uit de database
    @Override
    public List<Adres> findAll() throws SQLException {
        Query query = session.createQuery("FROM Adres", Adres.class);
        List<Adres> adressen = query.list();

        return adressen;
    }
}
