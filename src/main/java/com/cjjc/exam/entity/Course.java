package com.cjjc.exam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: cj
 * @date: 2020/11/16 16:25
 * @description:
 */

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cid")
    private String cid;

    @Column(name = "name")
    private String cname;

    @Column(name = "teaid")
    private int teaid;
    //外键

    @Column(name = "stuid")
    private Integer stuid;
    //foreign key

    @Column(name = "score")
    private Integer score;
    //int申明的字段不能为null，故为Integer
}
