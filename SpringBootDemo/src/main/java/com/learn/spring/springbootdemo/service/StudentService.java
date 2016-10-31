/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.spring.springbootdemo.service;

import com.learn.spring.springbootdemo.dao.StudentDao;
import com.learn.spring.springbootdemo.entity.Student;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    
    public Collection<Student> getAllStudents(){
        return studentDao.getAllStudents();
    }
}
