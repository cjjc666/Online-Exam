package com.cjjc.exam.service;

import com.cjjc.exam.dao.Teacherdao;
import com.cjjc.exam.entity.Student;
import com.cjjc.exam.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: cj
 * @date: 2020/11/14 18:02
 * @description:
 */
@Service
public class Teaservice {
    @Autowired
    Teacherdao teacherdao;

    public Teacher login(String tid, String name){
        return teacherdao.getByTidAndName(tid,name);
        //登录方法
    }



}
