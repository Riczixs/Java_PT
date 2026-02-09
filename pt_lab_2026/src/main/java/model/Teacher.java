package model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Teacher extends Person implements Comparator<Teacher>, Comparable<Teacher>{

    private String specialization;
    private Integer yearsOfWork;
    private Set<Student> students;

    public Teacher(){
        super();
    };
    public Teacher(String name, Integer age, String gender, String specialization, Integer yearsOfWork) {
        super(name, age, gender);
        this.specialization = specialization;
        this.yearsOfWork = yearsOfWork;
        this.students = new HashSet<>();
    }

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

    public String getSpecialization() {
        return specialization;
    }

    public Integer getYearsOfWork() {
        return yearsOfWork;
    }

    public void setYearsOfWork(Integer yearsOfWork) {
        this.yearsOfWork = yearsOfWork;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
