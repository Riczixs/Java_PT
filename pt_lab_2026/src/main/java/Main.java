import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.studentComparator;
import model.Student;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
    public static int type;
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static ObjectMapper mapper = new ObjectMapper();
    //public static Student getColleagues(JsonNode node){
//        Set<Student> colls = chooseSet(type);
//        Student result = new Student((node.get("name").asText()), node.get("age").asInt(), node.get("gender").asText(),
//                node.get("instrument").asText(), node.get("yearsOfStudy").asInt(), colls);
//        if(node.get("colleagues") == null){return  result;}
//        for(JsonNode col : node.get("colleagues")){
//            result.getColleagues().add(getColleagues(col));
//        }
//        return result;
    //}

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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Teachers
        JsonNode json = mapper.readTree(new File(ClassLoader.getSystemResource("teachers.json").getPath()));
        for(JsonNode node:json){
            session.persist(new Teacher(node.get("id").asInt(),node.get("name").asText(), node.get("age").asInt(),
                    node.get("gender").asText(), node.get("specialization").asText(), node.get("yearsOfWork").asInt()));
        }
        List<Teacher> teachers = session.createQuery("select t from Teacher t").list();
        teachers.forEach(System.out::println);
        //Students
        json = mapper.readTree(new File(ClassLoader.getSystemResource("students.json").getPath()));
        for(JsonNode node:json){
            Integer id = node.get("teacher").asInt();
            Teacher teacher = (Teacher) session.createQuery("select t from Teacher t where t.id = :id").getSingleResult();
            session.persist(new Student(node.get("id").asInt(),node.get("name").asText(), node.get("age").asInt(),
                    node.get("gender").asText(), node.get("instrument").asText(), node.get("yearsOfStudy").asInt(),teacher));
        }
        List<Teacher> students = session.createQuery("select s from Student s").list();
        students.forEach(System.out::println);
        session.getTransaction().commit();
    }


    public static void main(String[] args){
        type = Integer.parseInt(args[0]);
        generateMessage(type);
        Set<Student> students = chooseSet(type);
        try{
            initDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
