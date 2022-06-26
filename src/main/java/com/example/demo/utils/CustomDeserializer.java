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
import java.util.Iterator;
import java.util.List;

public class CustomDeserializer extends StdDeserializer<Article> {

    private static final String DT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

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

        JsonNode titleNode = node.get("title");
        String title = titleNode.asText();
        JsonNode languageNode = node.get("language");
        String language = languageNode.asText();
        JsonNode wikiNode = node.get("wiki");
        String wiki = wikiNode.asText();

        JsonNode createTimestampNode = node.get("create_timestamp");
        String createTimestampString = createTimestampNode.asText();
        LocalDateTime cTLocalDateTime = LocalDateTime.parse(createTimestampString, DateTimeFormatter.ofPattern(DT_PATTERN));
        Timestamp create_timestamp = Timestamp.valueOf(cTLocalDateTime);

        JsonNode timestampNode = node.get("timestamp");
        String timestampString = timestampNode.asText();
        LocalDateTime tLocalDateTime = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern(DT_PATTERN));
        Timestamp timestamp = Timestamp.valueOf(tLocalDateTime);

        List<AuxiliaryText> auxiliaryTexts = new ArrayList<>();
        JsonNode auxiliaryTextNode = node.get("auxiliary_text");
        if (auxiliaryTextNode != null && !auxiliaryTextNode.isNull()) {
            Iterator<JsonNode> iterator = auxiliaryTextNode.elements();
            while (iterator.hasNext()) {
                String s = iterator.next().asText();
                if (!s.isBlank()) {
                    auxiliaryTexts.add(new AuxiliaryText(s));
                }
            }
        }

        List<Category> categories = new ArrayList<>();
        JsonNode categoriesTextNode = node.get("category");
        if (categoriesTextNode != null && !categoriesTextNode.isNull()) {
            Iterator<JsonNode> iterator = categoriesTextNode.elements();
            while (iterator.hasNext()) {
                String s = iterator.next().asText();
                if (!s.isBlank()) {
                    categories.add(new Category(s));
                }
            }
        }

        Article article = Article.builder()
                .title(title).language(language).wiki(wiki)
                .create_timestamp(create_timestamp).timestamp(timestamp)
                .auxiliary_text(auxiliaryTexts).category(categories).build();
        return article;
    }
}
