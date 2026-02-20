package mapper;

import dto.TeacherDto;
import lombok.Builder;
import model.Teacher;

@Builder
public class DtoToTeacher {
    public Teacher map(TeacherDto dto){
        if(dto == null) throw new NullPointerException("TeacherDto is null");
        return Teacher.builder()
                .name(dto.name())
                .age(dto.age())
                .specialization(dto.specialization())
                .yearsOfWork(dto.yearsOfWork())
                .build();
    }
}
