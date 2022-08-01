package com.example.securitysimple.student;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "fayçal"),
            new Student(2, "Imene")
    );

    @GetMapping
    public List<Student> getStudents(){
        return STUDENTS;
    }

    @PostMapping
    public void registreStrudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable Integer studentId){
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable Integer studentId,@RequestBody Student student){
        System.out.println(String.format("%s %s" , studentId,student));
    }
}
