package com.example.demo.utils;

import com.example.demo.models.Article;
import com.example.demo.models.AuxiliaryText;
import com.example.demo.models.Category;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializer extends StdSerializer<Article> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Article> a) {
        super(a);
    }

    @Override
    public void serialize(Article article, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("title", article.getTitle());
        gen.writeStringField("language", article.getLanguage());
        gen.writeStringField("wiki", article.getWiki());

        long t = article.getCreate_timestamp().toInstant().getEpochSecond();
        gen.writeNumberField("create_timestamp", t);
        t = article.getTimestamp().toInstant().getEpochSecond();
        gen.writeNumberField("timestamp", t);

        gen.writeArrayFieldStart("auxiliary_text");
        for (AuxiliaryText a : article.getAuxiliary_text()) {
            gen.writeString(a.getValue());
        }
        gen.writeEndArray();

        gen.writeArrayFieldStart("category");
        for (Category c : article.getCategory()) {
            gen.writeString(c.getValue());
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
