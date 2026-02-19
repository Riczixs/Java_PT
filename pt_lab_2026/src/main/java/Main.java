import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.studentComparator;
import model.Student;
import model.Teacher;
import mvc.StudentRepository;
import mvc.TeacherId;
import mvc.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.util.*;

public class Main {
//    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static List<Student> students = new ArrayList<>();
    private static final Map<Integer, Teacher> teachers = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepository studentRepository = new StudentRepository();
    private static final TeacherRepository teacherRepository = new TeacherRepository();

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

    public static void initDatabase() throws IOException{
        //Teachers
        JsonNode json = mapper.readTree(new File(ClassLoader.getSystemResource("teachers.json").getPath()));
        for(JsonNode node:json){
            Teacher teacher =new Teacher(node.get("name").asText(), node.get("age").asInt(),
                    node.get("gender").asText(), node.get("specialization").asText(), node.get("yearsOfWork").asInt());
            teacherRepository.save(teacher, new TeacherId(teacher.getId()));
        }
        //Students
        json = mapper.readTree(new File(ClassLoader.getSystemResource("students.json").getPath()));
        if(session.createQuery("select s from Student s").list().isEmpty()){
            for(JsonNode node:json){
                Integer id = node.get("teacher").asInt();
                Teacher teacher = session.createQuery("select t from Teacher t where t.id = :id", Teacher.class).setParameter("id", id).uniqueResult();
                session.persist(new Student(node.get("name").asText(), node.get("age").asInt(),
                        node.get("gender").asText(), node.get("instrument").asText(), node.get("yearsOfStudy").asInt(), teacher));
            }
            students = session.createQuery("select s from Student s", Student.class).list();
        }
        session.getTransaction().commit();
        session.close();
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
//
//    public static void executeQuery(Integer q){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        if(q == 1){ //Fetch all students of given Teacher
//            System.out.println("Choose Teachers id: \n");
//            List<Teacher> teachers = session.createQuery("from Teacher", Teacher.class).list();
//            teachers.forEach(System.out::println);
//            Integer id = Integer.parseInt(scanner.nextLine());
//            List<Student> s = session.createQuery("from Student s where s.teacher.id = :id", Student.class).setParameter("id", id).list();
//            if(s.isEmpty()){
//                System.out.println("Teacher has not any students!");
//            }
//            s.forEach(System.out::println);
//        }else if(q == 2){ //Get Teacher with the longest practice
//            Teacher t = session.createQuery("from Teacher t ORDER BY t.yearsOfWork desc", Teacher.class).getSingleResult();
//            System.out.println(t.toString());
//        }else if(q == 3){ //Get 5 Students with the longest period of study
//            List<Student> students = session.createQuery("from Student s ORDER BY s.yearOfStudy desc", Student.class).setMaxResults(5).list();
//            students.forEach(System.out::println);
//        }else if(q == 4){ //Get all saxophone players
//            String str = "Saxophone";
//            List<Student> students = session.createQuery("from Student s where s.instrument = :str", Student.class).setParameter("str", str).list();
//            students.forEach(System.out::println);
//        }else if(q == 5){ //Get girls studying ranked by age
//            List<Student> students = session.createQuery("from Student s where s.gender = :str ORDER BY age desc",Student.class).setParameter("str", "female").list();
//            students.forEach(System.out::println);
//        }else if(q == 6){ //Get all Teachers and Students
//            //Teachers
//            List<Teacher> teach = session.createQuery("from Teacher", Teacher.class).list();
//            System.out.println("Teachers: \n");
//            teach.forEach(System.out::println);
//            //Students
//            List<Student> stud = session.createQuery("from Student", Student.class).list();
//            System.out.println("Students: \n");
//            stud.forEach(System.out::println);
//        }
//        session.getTransaction().commit();
//        session.close();
//    }
////
//    public static void addEntity(){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        System.out.println("""
//                Choose an entity:\s
//                1. Teacher
//                2. Student
//                """);
//        int type = Integer.parseInt(scanner.nextLine());
//        if(type == 1){
//            System.out.print("Enter Teachers data (name age gender specialization years_of_work)\n");
//            String message = scanner.nextLine();
//            String[] messageSplit = message.split(" ");
//            Teacher t = new Teacher(messageSplit[0], Integer.parseInt(messageSplit[1]), messageSplit[2], messageSplit[3], Integer.parseInt(messageSplit[4]));
//            teachers.put(t.getId(), t);
//            session.persist(t);
//            System.out.println("New teacher has been added! \n" +
//                    t.toString());
//        }
//        else if(type == 2){
//            System.out.println("Choose which Teachers student you want to add:\n");
//            teachers.values().forEach(System.out::println);
//            Integer id = Integer.parseInt(scanner.nextLine());
//            System.out.println("Enter Student data (name age gender instrument year_of_study)\n");
//            String[] message = scanner.nextLine().split(" ");
//            Teacher teacher = session.createQuery("select t from Teacher t where t.id = :id", Teacher.class).setParameter("id", id).uniqueResult();
//            Student s = new Student(message[0], Integer.parseInt(message[1]), message[2], message[3], Integer.parseInt(message[4]), teacher);
//            session.persist(s);
//            System.out.println("New student has been added! \n" +
//                    s.toString());
//        }
//        session.getTransaction().commit();
//        session.close();
//    }
//
//    public static void deleteEntity(){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        System.out.println("""
//                Choose an entity to delete:\s
//                1. Teacher
//                2. Student
//                """);
//        int type = Integer.parseInt(scanner.nextLine());
//        if(type == 1){
//            session.createQuery("from Teacher t", Teacher.class).list().forEach(System.out::println);
//            Integer id = Integer.parseInt(scanner.nextLine());
//            session.createQuery("delete Teacher t where id = :id").setParameter("id", id).executeUpdate();
//            System.out.println("Teacher has been deleted!");
//        }else if(type == 2){
//            students.forEach(System.out::println);
//            session.createQuery("from Student s", Student.class).list().forEach(System.out::println);
//            Integer id = Integer.parseInt(scanner.nextLine());
//            if(session.createQuery("delete Student s where id = :id").setParameter("id", id).executeUpdate() > 0){
//                System.out.println("Student has been deleted!");
//            }else{
//                System.out.println("ERROR: Student has not been deleted!");
//            }
//        }
//        session.getTransaction().commit();
//        session.close();
//    }

    public static void printCommands() {
        System.out.println("""
                'help' => Print this help message.
                'query' => Show available queries
                'add' => Add new element
                'delete' => Delete element
                'update' => Update element
                'exit' => Exit program
                """);
    }
//
//    public static void updateEntity(){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        System.out.println("""
//                Choose an entity to update:\s
//                1. Teacher
//                2. Student
//                """);
//        int id = Integer.parseInt(scanner.nextLine());
//        if(id == 1){
//            System.out.println("Choose Teachers id: \n");
//            List<Teacher> teachers = session.createQuery("from Teacher", Teacher.class).list();
//            teachers.forEach(System.out::println);
//            id = Integer.parseInt(scanner.nextLine());
//            Teacher teacher = session.createQuery("from Teacher t where t.id = :id", Teacher.class).setParameter("id", id).uniqueResult();
//            System.out.println("""
//                    Enter field for update with new value
//                    name <value>
//                    age <value>
//                    gender <value>
//                    specialization <value>
//                    years_of_work <value>
//                    """);
//            String[] message = scanner.nextLine().split(" ");
//            Object value;
//            if(message[0].equals("age") || message[0].equals("yearOfStudy") || message[0].equals("yearsOfWork")){
//                value = Integer.parseInt(message[1]);
//            }else{
//                value = message[1];
//            }
//            session.createQuery("update Teacher t set t."+message[0] +" = :val where t.id = :id").setParameter("id", id).setParameter("val",value).executeUpdate();
//            System.out.println("Teacher has been updated!");
//            Teacher teach = session.createQuery("from Teacher t where t.id = :id", Teacher.class).setParameter("id", id).uniqueResult();
//            System.out.println(teach.toString());
//        }else if(id == 2){
//            System.out.println("Choose Student id: \n");
//            List<Student> stud = session.createQuery("from Student",Student.class).list();
//            stud.forEach(System.out::println);
//            id = Integer.parseInt(scanner.nextLine());
//            Student student = session.createQuery("from Student s where s.id = :id", Student.class).setParameter("id", id).uniqueResult();
//            System.out.println("""
//                    Enter field for update with new value
//                    name <value>
//                    age <value>
//                    gender <value>
//                    instrument <value>
//                    yearOfStudy <value>
//                    """);
//            String[] message = scanner.nextLine().split(" ");
//            Object value; //TODO fix this
//            if(message[0].equals("age") || message[0].equals("yearOfStudy") || message[0].equals("yearsOfWork")){
//                value = Integer.parseInt(message[1]);
//            }else{
//                value = message[1];
//            }
//            session.createQuery("update Student s set s."+message[0] +" = :val where s.id = :id").setParameter("id", id).setParameter("val",value).executeUpdate();
//            System.out.println("Student has been updated!");
//        }
//        session.getTransaction().commit();
//        session.close();
//    }
//

    public static void main(String[] args) {




    }
}
