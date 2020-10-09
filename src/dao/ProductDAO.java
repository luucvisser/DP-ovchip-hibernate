package dao;

import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

// Wordt geimplementeerd door ProductDAOHibernate
public interface ProductDAO {

    // Alle functies die ProductDAOHibernate moet overnemen
    boolean save(Product product) throws SQLException;

    boolean update(Product product) throws SQLException;

    boolean delete(Product product) throws SQLException;

    List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException;

    List<Product> findAll() throws SQLException;
}
