package com.cjjc.exam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: cj
 * @date: 2020/11/10 11:03
 * @description:学生类
 */
@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sid")
    private String sid;

    @Column(name = "name")
    private String name;

    @Column(name = "school")
    private String school;

    @Column(name = "college")
    private String college;

    @Column(name = "grade")
    private int grade;

    @Column(name = "class")
    private String classes;

    @Column(name = "role")
    private int role;

    //int申明的字段不能为null，故为Integer

}
