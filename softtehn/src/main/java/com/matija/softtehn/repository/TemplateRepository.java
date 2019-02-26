package com.matija.softtehn.repository;

import com.matija.softtehn.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    public Template findByName(String name);

}
