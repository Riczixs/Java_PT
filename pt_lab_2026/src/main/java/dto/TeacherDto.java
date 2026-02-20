package dto;

import lombok.Builder;

public record TeacherDto(String name, Integer age, String gender, String specialization, Integer yearsOfWork){}
