package model;

import jakarta.persistence.*;
import lombok.*;
import mvc.Id.TeacherId;

import java.util.Comparator;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher implements Comparator<Teacher>, Comparable<Teacher>{

    @Id
    @EmbeddedId
    private TeacherId id;
    private String name;
    private Integer age;
    private String gender;
    private String specialization;
    @Column(name="years_of_work")
    private Integer yearsOfWork;

    public Teacher(int id, String name, Integer age, String gender, String specialization, Integer yearsOfWork) {
        this.id = new TeacherId(id);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
        this.yearsOfWork = yearsOfWork;
        this.students = new ArrayList<>();
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    private List<Student> students;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Teacher)) return false;
        return this.getName().equals(((Teacher) o).getName())
                && this.getGender().equals(((Teacher) o).getGender())
                && this.getAge() == ((Teacher) o).getAge()
                && this.specialization.equals(((Teacher) o).getSpecialization())
                && this.yearsOfWork.equals(((Teacher) o).getYearsOfWork());
    }

    @Override
    public int hashCode() {
        return (this.getName() + this.getGender()).hashCode();
    }

    @Override
    public int compareTo(Teacher o){
        if(o == null) return 1;
        if(!getSpecialization().equals(o.getSpecialization())) return getSpecialization().compareTo(o.getSpecialization());
        if(!getYearsOfWork().equals(o.getYearsOfWork())) return o.getYearsOfWork().compareTo(this.getYearsOfWork());
        if(!getName().equals(o.getName())) return getName().compareTo(o.getName());
        if(!getGender().equals(o.getGender())) return getGender().compareTo(o.getGender());
        return (this.getAge() > o.getAge()) ? 1 : -1;
    }

    @Override
    public int compare(Teacher o1, Teacher o) {
        if(o1 == null) return -1;
        if(o == null) return 1;
        if(!o1.getSpecialization().equals(o.getSpecialization())) return o1.getSpecialization().compareTo(o.getSpecialization());
        if(!o1.getYearsOfWork().equals(o.getYearsOfWork())) return o1.getYearsOfWork().compareTo(o.getYearsOfWork());
        if(!o1.getName().equals(o.getName())) return o1.getName().compareTo(o.getName());
        if(!o1.getGender().equals(o.getGender())) return o1.getGender().compareTo(o.getGender());
        return (o1.getAge() > o.getAge()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id.getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", specialization='" + specialization + '\'' +
                ", yearsOfWork=" + yearsOfWork +
                '}';
    }
}
