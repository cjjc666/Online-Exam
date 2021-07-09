package com.cjjc.exam.service;

import com.cjjc.exam.dao.Studao;
import com.cjjc.exam.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: cj
 * @date: 2020/11/10 11:37
 * @description:
 */
@Service
public class Stuservice {
    @Autowired
    Studao studao;

    public Student add(Student student){
        studao.save(student);
        return student;
        //注册方法
    }

    public Student login(String sid,String name){
        return studao.getBySidAndName(sid,name);
        //登录方法
    }

    /*public Student getinfo(int id){
        return studao.findById(id);
        //通过存入session的id获取student的信息
    }*/
    public Student getinfo(int id){
        return studao.findById(id);
        //通过id获取学生详细信息
    }


}
