package com.example.demo.services;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.model.Article;
import com.example.demo.utils.CustomDeserializer;
import com.example.demo.utils.CustomSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class AppService {
    @Autowired
    ArticleRepository repository;
    @Value("${app.file.path}")
    private String FILE_PATH;

    public String findArticleByTitle(String title, String isPretty) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        if (isPretty.equals("1")) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        module.addSerializer(Article.class, new CustomSerializer());
        mapper.registerModule(module);
        Article article = repository.findArticleByTitle(title);

        try {
            return article != null ?
                    mapper.writeValueAsString(article) : "There is no article with the title: " + title;
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    public String produce() {
        List<Article> titles = new ArrayList<>(12000);
        try {
            File file = new File(FILE_PATH);
            Scanner s = new Scanner(new FileInputStream(file));
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Article.class, new CustomDeserializer());
            mapper.registerModule(module);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith("{\"index\"")) {
                    continue;
                }
                titles.add(
                        mapper.readValue(line, Article.class));
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        System.out.println("Reading is done");
        repository.saveAll(titles);
        return String.valueOf(titles.size());
    }
}
