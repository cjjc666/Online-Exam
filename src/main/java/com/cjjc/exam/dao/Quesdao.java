package com.cjjc.exam.dao;

import com.cjjc.exam.entity.Course;
import com.cjjc.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: cj
 * @date: 2020/11/19 22:49
 * @description:
 */
public interface Quesdao extends JpaRepository<Question,Integer> {
    Question save(Question question);
    List<Question> findAll();
    void deleteByQid(int qid);

    List<Question> findAllByCouid(int couid);

    Question findByQid(int qid);

}
