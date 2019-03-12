package com.matija.softtehn.repository;

import com.matija.softtehn.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Template findByName(String name);

    @Query("SELECT t FROM Template t where t.group.id = :groupId and t.name = :templateName")
    Template findByGroupIDAndTemplateName(@Param("groupId") Long groupId, @Param("templateName") String templateName);

    @Query("SELECT t FROM Template t where t.group.id = :groupId")
    List<Template> findByGroupID(@Param("groupId") Long groupId);
}
