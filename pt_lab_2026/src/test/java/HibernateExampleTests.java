import model.Teacher;
import mvc.repository.TeacherRepository;
import org.assertj.core.api.Assertions;
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

    TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository = new TeacherRepository();
        Teacher t1 = new Teacher(1,"Andrzej",40, "male", "Saxophone", 15);
        Teacher t2 = new Teacher(2, "Grzegorz", 35, "male", "Piano", 10);
        teacherRepository.save(t1,t1.getId());
        teacherRepository.save(t2,t2.getId());
    }

    @Test
    public void teacher_should_not_be_null(){
        Teacher t = teacherRepository.findById(1).get();
        Assertions.assertThat(t).isNull();
    }

}
