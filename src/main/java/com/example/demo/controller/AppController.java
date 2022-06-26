package com.example.demo.controller;

import com.example.demo.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @Autowired
    AppService service;

    @GetMapping("/wiki/{title}")
    public ResponseEntity<String> getArticleByTitle(
            @PathVariable(name = "title") String title,
            @RequestParam(name = "pretty", defaultValue = "0", required = false) String isPretty) throws Exception {

        return ResponseEntity.ok(
                service.findArticleByTitle(title, isPretty));
    }

    @GetMapping("/wiki/import")
    public ResponseEntity<String> produceImportDataToDB() throws Exception {
        return ResponseEntity.ok(service.produceImportData() + " titles has been recorded");
    }

}
