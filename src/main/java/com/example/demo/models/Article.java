package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuxiliaryText> auxiliary_text;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
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
