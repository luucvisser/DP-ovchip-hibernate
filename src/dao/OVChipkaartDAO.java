package dao;

import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

// Wordt geimplementeerd door OVChipkaartDAOPsql
public interface OVChipkaartDAO {

    // Alle functies die OVChipkaartDAOPsql moet overnemen
    boolean save(OVChipkaart ov) throws SQLException;

    boolean update(OVChipkaart ov) throws SQLException;

    boolean delete(OVChipkaart ov) throws SQLException;

    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    List<OVChipkaart> findAll() throws SQLException;
}
