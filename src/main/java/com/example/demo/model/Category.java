package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Document
@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 2048)
    private String value;

    public Category(String value) {
        this.value = value;
    }
}
