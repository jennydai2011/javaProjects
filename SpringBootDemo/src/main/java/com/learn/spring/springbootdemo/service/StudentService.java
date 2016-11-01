/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.spring.springbootdemo.service;

import com.learn.spring.springbootdemo.dao.StaticStudentDaoImpl;
import com.learn.spring.springbootdemo.dao.StudentDao;
import com.learn.spring.springbootdemo.entity.Student;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    @Qualifier("mongoData")
    private StudentDao studentDao;
    
    public Collection<Student> getAllStudents(){
        return this.studentDao.getAllStudents();
    }
    
    public Student getStudentById(int id){
        return this.studentDao.getStudentById(id);
    }

    public void deleteStudentById(int id) {
        this.studentDao.deleteStudentById(id);
    }
    
     public void updateStudent(Student student){
          this.studentDao.updateStudent(student);
    }

    public void insertStudent(Student student) {
       this.studentDao.insertStudent(student);
    }
}
