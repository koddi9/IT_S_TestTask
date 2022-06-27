package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class AuxiliaryText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Lob
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    public AuxiliaryText(String value, Article article) {
        this.value = value;
        this.article = article;
    }
}
