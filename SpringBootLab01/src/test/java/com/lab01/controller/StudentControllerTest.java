package com.lab01.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab01.entity.Student;

@SpringBootTest
@AutoConfigureMockMvc // spirng boot로 MVC 테스트
public class StudentControllerTest {
	
	@Autowired
	private MockMvc mocMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private Student student;
	//테스트 이전에 하나 레코드 인서트 테스트.
	@BeforeEach
	void setUp() {
		student = new Student();
		student.setGrade(0);
		student.setName("본엽");
	}
	
	/*
	 * 1. 학생 등록 테스트
	 * (POST /students)
	 */
	
	void createStudent() throws Exception{
		
		mocMvc.perform(post("/students")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(mapper.writeValueAsString(student)))
		    .andExpect(status().isCreated())
		    .andExpect(jsonPath("$.name").value(student.getName()))
		    .andExpect(jsonPath("$.grade").value(student.getGrade()));
	}
	
	/*
	 * 2.학생 목록 테스트
	 */
	
	@Test
	void getAll() throws Exception{
		
		mocMvc.perform(get("/students"))
				.andExpect(status().isOk());
		
	}

	}


