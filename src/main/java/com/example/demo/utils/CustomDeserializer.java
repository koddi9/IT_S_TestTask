package com.example.demo.utils;

import com.example.demo.models.Article;
import com.example.demo.models.AuxiliaryText;
import com.example.demo.models.Category;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CustomDeserializer extends StdDeserializer<Article> {

    private static final String DT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String JSON_ARRAY_EXCEPTION = "Json array can't be handled: ";

    public CustomDeserializer() {
        this(null);
    }

    public CustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Article deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        Article article = new Article();

        String title = node.get("title").asText();
        article.setTitle(title);
        String language = node.get("language").asText();
        article.setLanguage(language);
        String wiki = node.get("wiki").asText();
        article.setWiki(wiki);

        Timestamp create_timestamp = getTimestampFromJsonField(node.get("create_timestamp"));
        article.setCreate_timestamp(create_timestamp);
        Timestamp timestamp = getTimestampFromJsonField(node.get("timestamp"));
        article.setTimestamp(timestamp);

        JsonNode auxiliaryTextNode = node.get("auxiliary_text");
        List<AuxiliaryText> auxiliaryTexts = getListFromJsonArray(auxiliaryTextNode, AuxiliaryText.class, article);
        article.setAuxiliary_text(auxiliaryTexts);

        JsonNode categoriesTextNode = node.get("category");
        List<Category> categories = getListFromJsonArray(categoriesTextNode, Category.class, article);
        article.setCategory(categories);

        return article;
    }

    private Timestamp getTimestampFromJsonField(JsonNode node) {
        LocalDateTime localDateTime = LocalDateTime.parse(node.asText(), DateTimeFormatter.ofPattern(DT_PATTERN));
        return Timestamp.valueOf(localDateTime);
    }

    private <T> List<T> getListFromJsonArray(JsonNode node, Class<T> c, Article a) {
        if (node == null || node.isNull()) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>();
        try {
            Iterator<JsonNode> iterator = node.elements();
            while (iterator.hasNext()) {
                String arrayElement = iterator.next().asText();
                if (!arrayElement.isBlank()) {
                    list.add(c.getDeclaredConstructor(String.class, Article.class).newInstance(arrayElement, a));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(JSON_ARRAY_EXCEPTION + e.getMessage());
        }
        return list;
    }
}
