package com.cjjc.exam.service;

import com.cjjc.exam.dao.Quesdao;
import com.cjjc.exam.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/19 22:54
 * @description:
 */
@Service
public class Queservice {
    @Autowired
    Quesdao quesdao;

    public Question addoralterques(Question question){
        return quesdao.save(question);
    }

    public Question query(int qid){
        return quesdao.findByQid(qid);
    }

    public void delques(int qid){
        quesdao.deleteByQid(qid);
    }

    public List<Question> getall(){
        return quesdao.findAll();
    }


    public Page<Question> getpage(Integer page, Integer size){
        Sort sort=Sort.by(Sort.Direction.DESC,"qid");
        //id倒序排列
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Question> questions=quesdao.findAll(pageable);
        return questions;
        //分页
    }

    public List<Question> findbycouid(int couid){
        return quesdao.findAllByCouid(couid);
    }
}
