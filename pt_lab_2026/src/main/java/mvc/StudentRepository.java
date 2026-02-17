package mvc;

import model.Student;
import java.util.*;

public class StudentRepository implements Repository<Student>{
    private final Map<Integer, Student> students = new HashMap<>();

    public boolean save(Student student) {
        if(student == null) return false;
        if(students.containsKey(student.getId())) return false;

        return true;
    }



    public Student findById(Integer id) {
        return null;
    }
    public List<Student> findAll() {
        return null;
    }
    public void delete(Student student) {}
    public Student update(Student student, Integer id) {
        return null;
    }
}
