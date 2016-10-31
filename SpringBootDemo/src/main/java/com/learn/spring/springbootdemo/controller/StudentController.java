/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.spring.springbootdemo.controller;

import com.learn.spring.springbootdemo.entity.Student;
import com.learn.spring.springbootdemo.service.StudentService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    @RequestMapping(value="/", method = RequestMethod.GET)
     public Collection<Student> getAllStudents(){
        return studentService.getAllStudents();
    }
}
