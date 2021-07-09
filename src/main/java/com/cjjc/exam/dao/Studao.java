package com.cjjc.exam.dao;

import com.cjjc.exam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/10 11:32
 * @description:
 */

public interface Studao extends JpaRepository<Student,Integer> {
    Student findByName(String name);

    Student getBySidAndName(String sid,String name);

    List<Student> findAll();

    //Student getById(String id);

    Student findById(int id);

    Student save(Student student);

    void deleteBySid(String id);

}
