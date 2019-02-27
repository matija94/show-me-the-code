package com.matija.softtehn.repository.integration;

import com.matija.softtehn.model.Template;
import com.matija.softtehn.repository.TemplateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TemplateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        Template template = new Template();
        template.setName("test template");
        entityManager.persistAndFlush(template);

        Template fetchedTemplate = templateRepository.findByName(template.getName());

        assertThat(template).isEqualTo(fetchedTemplate);
    }

}
