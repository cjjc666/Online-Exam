package com.cjjc.exam.service;

import com.cjjc.exam.dao.Coursedao;
import com.cjjc.exam.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/16 16:32
 * @description:
 */

@Service
public class Couservice {
    @Autowired
    Coursedao coursedao;

    public Course getbyid(int id){
        return coursedao.findById(id);
    }

    public Course addoraltercou(Course course){
        coursedao.save(course);
        return course;
        //添加、修改课程
    }

    public Course query(String cid,String cname){
        return coursedao.findByCidAndCname(cid,cname);
        //through cid and cname to query course informations!!!
    }


    public void delcou(int id){
        coursedao.deleteById(id);
        //删除课程
    }

    public List<Course> findall(int teaid){
        return coursedao.findAll();
        //get all courses
    }


    public Page<Course> getpage(Integer page,Integer size){
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        //id倒序排列
        Pageable pageable=PageRequest.of(page,size,sort);
        Page<Course> courses=coursedao.findAll(pageable);
        return courses;
        //分页
    }

    /*public Page<Course> findpage(int teaid,Integer page,Integer size){
        Sort sort=Sort.by(Sort.Direction.DESC,"cid");
        //id倒序排列
        Pageable pageable= (Pageable) PageRequest.of(page,size,sort);
        Page<Course> courses=coursedao.findAllByTeaid(teaid,pageable);
        return courses;
        //条件分页
    }*/
}
