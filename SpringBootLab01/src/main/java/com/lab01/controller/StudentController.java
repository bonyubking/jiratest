package com.lab01.controller;

import com.lab01.entity.Student;
import com.lab01.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
    /* 학생 목록 조회
    @GetMapping
    public List<Student> getAllStudents() {
  
        return studentService.findAll();
    }
*/
    // 학생 등록
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
 
        return studentService.save(student);
    }

    // 학생 상세 조회 GET / students/{id}
    /*@GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {

     
    	return studentService.findById(id);
   }
    */

    // 학생 정보 업데이트
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
    	Student res = studentService.findById(id);
        res.setGrade(student.getGrade());
        res.setName(student.getName());
        
    	return studentService.save(res);
    }

    // 학생 삭제
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }
    
    @GetMapping
    public CollectionModel<EntityModel<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();

 // 추가 코드
        List<EntityModel<Student>> studentModels = students.stream().map(
                student -> EntityModel.of(student,
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getStudentById(student.getId()))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getAllStudents())
                        .withRel("all-students")))
                .collect(Collectors.toList());

        return CollectionModel.of(studentModels, WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(StudentController.class).getAllStudents()).withSelfRel());
    }
    
    @GetMapping("/{id}")
    public EntityModel<Student> getStudentById(@PathVariable Long id){
    	Student student = studentService.findById(id);
    	
    	EntityModel<Student> resource = EntityModel.of(student);
    	
    	resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class, null).getStudentById(id)).withSelfRel()); 
    
    	resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class, null).getAllStudents()).withRel("all-students")); 
    	
    	return resource;
    }
    
}