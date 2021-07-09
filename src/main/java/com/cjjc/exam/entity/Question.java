package com.cjjc.exam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: cj
 * @date: 2020/11/19 22:46
 * @description:
 */

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qid")
    private int qid;

    @Column(name = "content")
    private String content;

    @Column(name = "answer")
    private String answer;

    @Column(name = "couid")
    private int couid;


}
