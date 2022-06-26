package com.example.demo.services;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.exceptions.ArticleNotFoundException;
import com.example.demo.models.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
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
    @Autowired
    ObjectMapper mapper;

    private static final String NOT_FOUND_MSG = "There is no article with the title: %s";


    public String findArticleByTitle(String title, String isPretty) throws Exception {

        Article article = repository.findArticleByTitle(title);
        if (article == null) {
            throw new ArticleNotFoundException(String.format(NOT_FOUND_MSG, title));
        }

        ObjectWriter objectWriter = mapper.writer();
        if (isPretty.equals("1")) {
            objectWriter = mapper.writerWithDefaultPrettyPrinter();
        }
        return objectWriter.writeValueAsString(article);
    }

    public String produceImportData() throws IOException {
        List<Article> titles = new ArrayList<>(12000);
        try (FileReader fr = new FileReader(FILE_PATH)) {
            Scanner s = new Scanner(fr);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith("{\"index\"")) {
                    continue;
                }
                titles.add(
                        mapper.readValue(line, Article.class));
            }
        }
        System.out.println("Reading is done");
        repository.saveAll(titles);
        return String.valueOf(titles.size());
    }
}
