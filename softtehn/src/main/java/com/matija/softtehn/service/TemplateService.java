package com.matija.softtehn.service;

import com.matija.softtehn.model.Template;
import com.matija.softtehn.model.embeddables.DateTime;
import com.matija.softtehn.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public Template findByName(String name) {
        return templateRepository.findByName(name);
    }

    public List<Template> findAll() {
        return templateRepository.findAll();
    }

    public Template createTemplate(Template template) {
        DateTime dateTime = DateTime.createDateTime();
        template.setDateTime(dateTime);
        templateRepository.save(template);
        return template;
    }
}
