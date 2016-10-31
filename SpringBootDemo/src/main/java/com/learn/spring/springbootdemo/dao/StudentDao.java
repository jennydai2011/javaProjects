/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.spring.springbootdemo.dao;

import com.learn.spring.springbootdemo.entity.Student;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {
    private static Map<Integer, Student> students;
    
    static{
       students = new HashMap<Integer, Student>(){
           {
               put(1, new Student(1, "Aneca", "Finance"));
               put(2, new Student(2, "Bretta", "Biology"));
               put(1, new Student(3, "Jenny", "Computer Science"));
           } 
       } ;
    }
    
    public Collection<Student> getAllStudents(){
        return this.students.values();
    }
}
