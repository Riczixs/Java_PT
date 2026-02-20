package mapper;

import dto.TeacherDto;
import model.Teacher;

public class TeacherToDto {

    public TeacherDto map(Teacher teacher){
        if(teacher == null) throw new NullPointerException("teacher is null");
        return new TeacherDto(teacher.getName(),
                teacher.getAge(),
                teacher.getGender(),
                teacher.getSpecialization(),
                teacher.getYearsOfWork());
    }
}
