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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("staticData")
public class StaticStudentDaoImpl implements StudentDao {
    private static Map<Integer, Student> students;
    
    static{
       students = new HashMap<Integer, Student>(){
           {
               put(1, new Student(1, "Aneca", "Finance"));
               put(2, new Student(2, "Bretta", "Biology"));
               put(3, new Student(3, "Jenny", "Computer Science"));
           } 
       } ;
    }
    
    @Override
    public Collection<Student> getAllStudents(){
        return this.students.values();                                            
    }
    
    @Override
    public Student getStudentById(int id){
        return this.students.get(id);
    }

    @Override
    public void deleteStudentById(int id) {
        this.students.remove(id);
    }
    
    @Override
    public void updateStudent(Student student){
        Student s= students.get(student.getId());
        s.setName(student.getName());
        s.setCourse(student.getCourse());
        students.put(student.getId(), s);
    }

    @Override
    public void insertStudent(Student student) {
       students.put(student.getId(), student);
    }
}
