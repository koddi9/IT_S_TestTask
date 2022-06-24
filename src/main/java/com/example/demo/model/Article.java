package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
//@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 1024)
    private String title;
    private String language;
    private String wiki;

    private Timestamp create_timestamp;
    private Timestamp timestamp;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AuxiliaryText> auxiliary_text;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> category;

    @Override
    public String toString() {
        return "Title: " + title +
                "\nLanguage: " + language +
                "\nWiki: " + wiki +
                "\nAuxiliary_text: " + auxiliary_text +
                "\nCreate timestamp: " + create_timestamp +
                "\nTimestamp: " + timestamp;
    }
}
