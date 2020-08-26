package com.dimageshare.hrm.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code", length = 45, nullable = false)
    private String code;
    @Column(name = "desc", length = 45)
    private String desc;
}
