package domein;

import daoHibernate.AdresDAOHibernate;
import daoHibernate.OVChipkaartDAOHibernate;
import daoHibernate.ProductDAOHibernate;
import daoHibernate.ReizigerDAOHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.List;

public class TestHiberDao {
    public static void main(String[] args) throws SQLException {
        // Maakt een session factory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Maakt een session
        Session session = sessionFactory.openSession();

        // Roep de tests aan
        testAdresDAOHibernate(sessionFactory, session);
        testReizigerDAOHibernate(sessionFactory, session);
        testProductDAOHibernate(sessionFactory, session);
        testOVChipkaartDAOHibernate(sessionFactory, session);

        // Close de session en sessionFactory
        sessionFactory.close();
        session.close();
    }

    public static void testAdresDAOHibernate(SessionFactory sessionFactory, Session session) throws SQLException {
        System.out.println("---------- Test AdresDAOHibernate ----------");

        AdresDAOHibernate adao = new AdresDAOHibernate(session);

        // Test findAll()
        System.out.println("[TEST] findAll()");

        List<Adres> adressen = adao.findAll();
        for (Adres adres : adressen) {
            System.out.println(adres);
        }
    }



    public static void testReizigerDAOHibernate(SessionFactory sessionFactory, Session session) throws SQLException {
        System.out.println("---------- Test ReizigerDAOHibernate ----------");

        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);

        // Test findAll()
        System.out.println("[TEST] findAll()");

        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger reiziger : reizigers) {
            System.out.println(reiziger);
        }
    }



    public static void testProductDAOHibernate(SessionFactory sessionFactory, Session session) throws SQLException {
        System.out.println("---------- Test ProductDAOHibernate ----------");

        ProductDAOHibernate pdao = new ProductDAOHibernate(session);

        // Test findAll()
        System.out.println("[TEST] findAll()");

        List<Product> producten = pdao.findAll();
        for (Product product : producten) {
            System.out.println(product);
        }
    }



    public static void testOVChipkaartDAOHibernate(SessionFactory sessionFactory, Session session) throws SQLException {
        System.out.println("---------- Test OVChipkaartDAOHibernate ----------");

        OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);

        // Test findAll()
        System.out.println("[TEST] findAll()");

        List<OVChipkaart> ovchipkaarten = ovdao.findAll();
        for (OVChipkaart ovchipkaart : ovchipkaarten) {
            System.out.println(ovchipkaart);
        }
    }
}
