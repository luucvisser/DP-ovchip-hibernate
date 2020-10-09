package daoHibernate;

import dao.OVChipkaartDAO;
import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;


    // Constructor (krijgt de sessie mee zodat die overal in de file gebruikt kan worden)
    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(OVChipkaart ov) throws SQLException {
        this.session.save(ov);
        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(OVChipkaart ov) throws SQLException {
        this.session.update(ov);
        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(OVChipkaart ov) throws SQLException {
        this.session.delete(ov);
        return true;
    }

    // Vindt een OV chipkaart aan de hand van een reiziger
    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        Query query = session.createQuery("SELECT * FROM OVChipkaart WHERE reiziger_id = " + reiziger.getId(), OVChipkaart.class);
        List<OVChipkaart> ovchipkaarten = query.list();

        return ovchipkaarten;
    }

    // Haalt alle OV Chipkaarten op uit de database
    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        Query query = session.createQuery("FROM OVChipkaart", OVChipkaart.class);
        List<OVChipkaart> ovchipkaarten = query.list();

        return ovchipkaarten;
    }
}
