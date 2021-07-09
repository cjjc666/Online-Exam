package com.cjjc.exam.dao;

import com.cjjc.exam.entity.Student;
import com.cjjc.exam.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/14 17:58
 * @description:
 */
public interface Teacherdao extends JpaRepository<Teacher,Integer> {
    Teacher findByName(String name);

    Teacher getByTidAndName(String sid,String name);

    List<Teacher> findAll();

    Teacher getByTid(String id);

    Teacher save(Teacher teacher);

    void deleteByTid(String id);

}
