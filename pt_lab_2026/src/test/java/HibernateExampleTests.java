import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
public class HibernateExampleTests {

    private SessionFactory sessionFactory;


    @BeforeEach
    protected void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @AfterEach
    protected void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void should_fetch_all_teachers(){
        try{
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(new Teacher("Franek", 10, "woman", "Saxophone", 1));
            session.persist(new Teacher("Rysiek", 21, "man", "Piano", 2));
            List<Teacher> teachers = session.createQuery("from Teacher", Teacher.class).list();
            teachers.forEach(System.out::println);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void save_test_object_to_db() {
        Teacher teacher = new Teacher();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(teacher);
            session.getTransaction().commit();
        }

    }





}
