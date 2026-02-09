import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.studentComparator;
import model.Student;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static int type;

    public static Student getColleagues(JsonNode node){
        Set<Student> colls = chooseSet(type);
        Student result = new Student((node.get("name").asText()), node.get("age").asInt(), node.get("gender").asText(),
                node.get("instrument").asText(), node.get("yearsOfStudy").asInt(), colls);
        if(node.get("colleagues") == null){return  result;}
        for(JsonNode col : node.get("colleagues")){
            result.getColleagues().add(getColleagues(col));
        }
        return result;
    }

    public static Set<Student> chooseSet(int type){
        if(type == 1){ //Unsorted mode
            return new HashSet<Student>();
        }else if(type == 2){
            return new TreeSet<>();
        }
        else if(type==3) { //Natural ordering {1} or alternative ordering {2}
            return new TreeSet<Student>(new studentComparator());
        }
        return null;
    }

    public static void generateMessage(int type) {
        if(type == 2){
            System.out.println("Students ordered by years of study: \n");
        }else if(type == 3){
            System.out.println("Students ordered by age: \n");
        }else{
            System.out.println("Students without an order: \n");
        }
    }

    public static void main(String[] args) throws IOException {
        type = Integer.parseInt(args[0]);
        generateMessage(type);
        Set<Student> students = chooseSet(type);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(new File(ClassLoader.getSystemResource("students.json").getPath()));
        Set<Student> colleagues = chooseSet(type);
        if(colleagues == null) return;
        for(JsonNode node : json.get("students")){
            Student s = getColleagues(node);
            students.add(s);
        }
        for(Student s : students){
            System.out.println(s.toString() +"\n");
            int counter = s.printStudents("<>");
            System.out.println("Liczba potomków " + counter + "\n");
        }
    }
}
