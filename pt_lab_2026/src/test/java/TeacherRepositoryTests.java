import model.Teacher;
import mvc.repository.TeacherRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TeacherRepositoryTests {
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
    void saving_new_teacher_should_return_optional_teacher() {
        Teacher t = new Teacher(3, "Testowy", 22, "male", "Trombone", 5);
        try{
            Optional<Teacher> ot = teacherRepository.save(t,t.getId());
            Assertions.assertThat(ot)
                    .as("Saved teacher must not return Optional.empty()")
                    .isEqualTo(Optional.of(t));
        }catch (IllegalArgumentException e){
            //
        }
    }

    @Test
    void saving_teacher_with_existing_id_should_throw_IllegalArgumentException() {
        Teacher t = new Teacher(1, "Testowy", 22, "male", "Trombone", 5);
        Assertions.assertThatIllegalArgumentException()
                .as("Attempt of saving a teacher with an existing id must not be succeed!")
                .isThrownBy(
                () -> teacherRepository.save(t,t.getId())
        );
    }

    @Test
    void saving_null_object_should_return_empty_optional() {
        Assertions.assertThat(teacherRepository.save(null,null))
                .as("Attempt of saving a null object must return Optional.empty()!")
                .isEqualTo(Optional.empty());
    }

    @Test
    void deleting_not_existing_teacher_should_throw_IllegalArgumentException() {
        Assertions.assertThatIllegalArgumentException()
                .as("Attempt of deleting a non-existing teacher must throw an IllegalArgumentException!")
                .isThrownBy(()-> teacherRepository.delete(5));
    }
}
