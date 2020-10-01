package dao;

import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

// Wordt geimplementeerd door ReizigerDAOPsql
public interface ReizigerDAO {

    // Alle functies die ReizigerDAOPsql moet overnemen
    boolean save(Reiziger reiziger) throws SQLException;

    boolean update(Reiziger reiziger) throws SQLException;

    boolean delete(Reiziger reiziger) throws SQLException;

    Reiziger findById(int id) throws SQLException;

    List<Reiziger> findByGbdatum(String datum) throws SQLException;

    List<Reiziger> findAll() throws SQLException;
}
