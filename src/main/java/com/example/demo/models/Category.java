package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 2048)
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    public Category(String value, Article article) {
        this.value = value;
        this.article = article;
    }
}
