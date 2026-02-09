package model;

import java.util.*;
public class Student extends Person implements Comparable<Student>{

    private String instrument;
    private Integer yearOfStudy;
    private Set<Student> colleagues;
    public Student() {}
    public Student(String name, Integer age, String gender, String instrument, Integer yearOfStudy) {
        super(name, age, gender);
        this.instrument = instrument;
        this.yearOfStudy = yearOfStudy;
    }
    public Student(String name, Integer age, String gender, String instrument, Integer yearOfStudy, Set<Student> colleagues) {
        super(name, age, gender);
        this.instrument = instrument;
        this.yearOfStudy = yearOfStudy;
        this.colleagues = colleagues;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Student)) return false;
        return getInstrument().equals(((Student) o).getInstrument())
                && getYearOfStudy().equals(((Student) o).getYearOfStudy())
                && getName().equals(((Student) o).getName())
                && getAge()==((Student) o).getAge()
                && getGender().equals(((Student) o).getGender())
                ;
    }
    /*
    * @Equals obj has the same hashCode
    * @HashCode is made of un-mutable fields only
    * */
    @Override
    public int hashCode(){
        return (this.instrument + this.getName() + this.getGender()).hashCode(); //only un-mutable fields
    }
    @Override
    public int compareTo(Student o) {
        if(this.equals(o)) return 0;
        if(!this.getYearOfStudy().equals(o.getYearOfStudy())) return this.getYearOfStudy().compareTo(o.getYearOfStudy());
        return this.getInstrument().compareTo(o.getInstrument());
    }


    public int printStudents(String prefix) {
        int tmpCounter = 0;
        if(getColleagues().isEmpty()) return tmpCounter;
        for(Student student : getColleagues()) {
            System.out.println(prefix + student);
            tmpCounter += 1;
            tmpCounter += student.printStudents((prefix + "<>"));
        }
        return tmpCounter;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Set<Student> getColleagues() {
        return colleagues;
    }

    public void setColleagues(Set<Student> colleagues) {
        this.colleagues = colleagues;
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
