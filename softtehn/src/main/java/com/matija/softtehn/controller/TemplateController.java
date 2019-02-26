package com.matija.softtehn.controller;

import com.matija.softtehn.model.Template;
import com.matija.softtehn.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping(Urls.TEMPLATE)
    public ResponseEntity<List<Template>> getTemplates() {
        return new ResponseEntity(templateService.findAll(), HttpStatus.OK);
    }

    @PostMapping(Urls.TEMPLATE)
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        templateService.createTemplate(template);
        return new ResponseEntity(template, HttpStatus.CREATED);
    }


}
