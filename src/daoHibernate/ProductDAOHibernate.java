package daoHibernate;

import dao.ProductDAO;
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

public class ProductDAOHibernate implements ProductDAO {
    private Session session;


    // Constructor (krijgt de sessie mee zodat die overal in de file gebruikt kan worden)
    public ProductDAOHibernate(Session session) { this.session = session; }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(Product product) throws SQLException {
        this.session.save(product);
        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(Product product) throws SQLException {
        this.session.update(product);
        return true;
    }

    // Verwijder het meegegeven object uit de database
    @Override
    public boolean delete(Product product) throws SQLException {
        this.session.update(product);
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException {
        Query query = session.createQuery("SELECT * FROM ov_chipkaart_product WHERE kaart_nummer = " + ov.getNummer(), Product.class);
        List<Product> producten = query.list();

        return producten;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Query query = session.createQuery("FROM Product", Product.class);
        List<Product> producten = query.list();

        return producten;
    }
}