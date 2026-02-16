package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.SessionFactory;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "students")
@NoArgsConstructor
public class Student implements Comparable<Student>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private Integer age;
    private String gender;
    private String instrument;
    private Integer yearOfStudy;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Student(Integer id, String name, Integer age, String gender, String instrument, Integer yearOfStudy, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.instrument = instrument;
        this.yearOfStudy = yearOfStudy;
        this.teacher = teacher;
    }


    //    @ManyToOne
//    @JoinColumn(name = "teacher_id") //choosing which column of Teacher entity
//    private Teacher teacher;
    /*
    * @Equals obj has the same hashCode
    * @HashCode is made of un-mutable fields only
    * */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Student)) return false;
        return getInstrument().equals(((Student) o).getInstrument())
                && getYearOfStudy().equals(((Student) o).getYearOfStudy())
                && getName().equals(((Student) o).getName())
                && getAge().equals(((Student) o).getAge())
                && getGender().equals(((Student) o).getGender())
                ;
    }
    @Override
    public int hashCode(){
        return (this.getInstrument() + this.getName() + this.getGender()).hashCode(); //only un-mutable fields
    }
    @Override
    public int compareTo(Student o) {
        if(this.equals(o)) return 0;
        if(!this.getYearOfStudy().equals(o.getYearOfStudy())) return this.getYearOfStudy().compareTo(o.getYearOfStudy());
        return this.getInstrument().compareTo(o.getInstrument());
    }
    @Override
    public String toString() {
        return "Student => {" +
                "name= " + getName() + " <> " +
                "age= " + getAge() + " <> " +
                "gender= " + getGender() + " <> " +
                "instrument= " + instrument + " <> " +
                "yearOfStudy= " + yearOfStudy +
                '}';
    }
}
