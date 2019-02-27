package com.matija.softtehn.repository.unit;

import com.matija.softtehn.model.Template;
import com.matija.softtehn.repository.TemplateRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TemplateRepositoryTest {

    @MockBean
    private TemplateRepository templateRepository;

    @Before
    public void setup() {
        Template template = new Template();
        template.setName("test name");

        Mockito
            .when(templateRepository.findByName(template.getName()))
            .thenReturn(template);
    }

    @Test
    public void templateShouldBeFound() {
        Template template = new Template();
        template.setName("test name");

        Template byName = templateRepository.findByName(template.getName());

        Assertions
                .assertThat(template.getName())
                .isEqualTo(byName.getName());

    }

}
