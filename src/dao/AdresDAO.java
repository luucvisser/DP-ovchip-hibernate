package dao;

import domein.Adres;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

// Wordt geimplementeerd door AdresDAOPsql
public interface AdresDAO {

    // Alle functies die AdresDAOPsql moet overnemen
    boolean save(Adres adres) throws SQLException;

    boolean update(Adres adres) throws SQLException;

    boolean delete(Adres adres) throws SQLException;

    Adres findByReiziger(Reiziger reiziger) throws SQLException;

    List<Adres> findAll() throws SQLException;
}
