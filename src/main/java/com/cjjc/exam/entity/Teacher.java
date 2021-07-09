package com.cjjc.exam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: cj
 * @date: 2020/11/14 17:54
 * @description:
 */

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tid")
    private String tid;

    @Column(name = "name")
    private String name;

}
