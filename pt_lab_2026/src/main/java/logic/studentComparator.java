package logic;

import model.Student;

import java.io.Serializable;
import java.util.Comparator;

public class studentComparator implements Comparator<Student>, Serializable {
    @Override
    public int compare(Student o1, Student o2){
        if(o1 == null) return -1;
        if(o2 == null) return 1;
        if(o1.getAge() != o2.getAge()) {
            return (o1.getAge() >= o2.getAge()) ? 1 : -1;
        }
        return o1.getYearOfStudy().compareTo(o2.getYearOfStudy());
    }
}
