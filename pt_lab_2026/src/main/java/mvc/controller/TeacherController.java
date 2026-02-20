package mvc.controller;

import lombok.RequiredArgsConstructor;
import model.Teacher;
import mvc.repository.TeacherRepository;
import java.util.*;
import java.util.Optional;

@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public String addTeacher(String teacherString){
        //id name age gender specialization yearsOfWork
        String[] fields = teacherString.split(" ");
        Teacher teacher = new Teacher(Integer.parseInt(fields[0]), fields[1],Integer.parseInt(fields[2]),fields[3],fields[4],Integer.parseInt(fields[5]));
        try{
            Optional<Teacher> t = teacherRepository.save(teacher, teacher.getId());
            if(t.isPresent()){
                return "done";
            }else{
                return "empty";
            }
        }catch(IllegalArgumentException e){
            return "bad request";
        }
    }

    public String getTeacherById(int id){
        Optional<Teacher> t = teacherRepository.findById(id);
        if(t.isEmpty()){
            return "not found";
        }else{
            return t.get().toString();
        }
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public String updateTeacher(String teacherString, Integer id){
        //id name age gender specialization yearsOfWork
        String[] fields = teacherString.split(" ");
        Teacher teacher = new Teacher(id, fields[0],Integer.parseInt(fields[1]),fields[2],fields[3],Integer.parseInt(fields[4]));
        try{
            Optional<Teacher> t =  teacherRepository.update(teacher, id);
            if(t.isPresent()){
                return t.get().toString();
            }else{
                return "empty";
            }
        }catch(IllegalArgumentException e){
            return "bad request";
        }
    }

    public String deleteTeacher(int id){
        try{
            teacherRepository.delete(id);
            return "done";
        }catch(IllegalArgumentException e){
            return "not found";
        }
    }
}
