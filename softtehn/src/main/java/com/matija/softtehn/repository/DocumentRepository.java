package com.matija.softtehn.repository;

import com.matija.softtehn.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d from Document d where d.template.id = :templateId")
    List<Document> findDocumensByTemplateId(@Param("templateId") Long templateId);
}
