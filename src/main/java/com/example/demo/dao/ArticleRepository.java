package com.example.demo.dao;

import com.example.demo.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findArticleByTitle(String title);
}
