import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.studentComparator;
import model.Student;
import model.Teacher;
import mvc.controller.TeacherController;
import mvc.repository.StudentRepository;
import mvc.repository.TeacherRepository;

import java.io.*;
import java.util.*;

public class Main {
    //    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Scanner scanner = new Scanner(System.in);
    private static final TeacherRepository teacherRepository = new TeacherRepository();
    private static final TeacherController teacherController = new TeacherController(teacherRepository);

    public static Set<Student> chooseSet(int type) {
        if (type == 1) { //Unsorted mode
            return new HashSet<Student>();
        } else if (type == 2) {
            return new TreeSet<>();
        } else if (type == 3) { //Natural ordering {1} or alternative ordering {2}
            return new TreeSet<Student>(new studentComparator());
        }
        return null;
    }

    public static void generateMessage(int type) {
        if (type == 2) {
            System.out.println("Students ordered by years of study: \n");
        } else if (type == 3) {
            System.out.println("Students ordered by age: \n");
        } else {
            System.out.println("Students without an order: \n");
        }
    }

    public static void initDatabase() throws IOException {
        //Teachers
        JsonNode json = mapper.readTree(new File(ClassLoader.getSystemResource("teachers.json").getPath()));
        for (JsonNode node : json) {
            Teacher teacher = new Teacher(node.get("id").asInt(), node.get("name").asText(), node.get("age").asInt(),
                    node.get("gender").asText(), node.get("specialization").asText(), node.get("yearsOfWork").asInt());
            teacherRepository.save(teacher, teacher.getId());
        }
        //Students
        json = mapper.readTree(new File(ClassLoader.getSystemResource("students.json").getPath()));
        for (JsonNode node : json) {
            Integer id = node.get("teacher").asInt();
            //Teacher teacher = session.createQuery("select t from Teacher t where t.id = :id", Teacher.class).setParameter("id", id).uniqueResult();
            //session.persist(new Student(node.get("name").asText(), node.get("age").asInt(),
              //      node.get("gender").asText(), node.get("instrument").asText(), node.get("yearsOfStudy").asInt(), teacher));
        }
     }

    public static void printQueries() {
            System.out.println("""
                    1. Get all students of chosen teacher
                    2. Get Teacher with longest practice
                    3. Get 5 Students with longest period of study
                    4. Get all saxophone players
                    5. Get girls studying ranked by age
                    6. Get all Teachers and Students
                    """
            );
    }


    public static void main(String[] args) {
        String message;
        boolean done = false;
        try{
            initDatabase();
            while((message = scanner.nextLine()) != null){
                if(message.contains("/show")){

                    teacherRepository.findAll()
                            .forEach(System.out::println);

                }else if(message.contains("/exit")){

                    done = true;

                }else if(message.contains("/update")){ //TODO Validate input

                    System.out.println("Choose Teacher to update: \n");
                    teacherRepository.findAll().forEach(System.out::println);
                    int id = Integer.parseInt(scanner.nextLine());
                    Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
                    System.out.println("""
                            Provide new values of chosen fields: \n
                            [name age gender specialization yearsOfWork]
                            """);
                    teacherController.updateTeacher(scanner.nextLine(), id);

                }else if(message.contains("/add")){

                    System.out.println("""
                            Provide field for new Teacher:
                            [id name age gender specialization yearsOfWork]
                            """);
                    message = scanner.nextLine();
                    System.out.println(teacherController.addTeacher(message));

                }else if(message.contains("/delete")){

                    teacherController.getAllTeachers()
                            .forEach(System.out::println);
                    System.out.println("Choose id of Teacher to delete");
                    int id = Integer.parseInt(scanner.nextLine());
                    teacherController.deleteTeacher(id);
                    System.out.println("Teacher successfully deleted");

                }
            }
        }catch (IOException e){
            //
        }
    }
}
