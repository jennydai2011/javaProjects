/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.spring.springbootdemo.dao;

import com.learn.spring.springbootdemo.entity.Student;
import java.util.Collection;

public interface StudentDao {

    void deleteStudentById(int id);

    Collection<Student> getAllStudents();

    Student getStudentById(int id);

    void insertStudent(Student student);

    void updateStudent(Student student);
    
}
