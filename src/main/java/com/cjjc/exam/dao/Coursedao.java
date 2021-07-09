package com.cjjc.exam.dao;

import com.cjjc.exam.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/16 16:30
 * @description:
 */
public interface Coursedao extends JpaRepository<Course,Integer> {
    Course findByCidAndCname(String cid,String name);
    //Student getBySidAndName(String sid,String name);
    //List<Course> findAllByTeaid(int teaid, Pageable pageable);
    List<Course> findAll();
    //Student getBySid(String id);

    Course findById(int id);

    Course save(Course course);

    void deleteById(int id);
}
