package com.matija.softtehn.controller;

import com.matija.softtehn.model.Template;
import com.matija.softtehn.repository.TemplateRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemplateRepository templateRepository;

    @Test
    public void givenTemplates_whenGetTemplates_thenReturnJsonArray() throws Exception {
        Template template = new Template();
        template.setName("test template");

        List<Template> templates = Arrays.asList(template);

        BDDMockito.given(templateRepository.findAll()).willReturn(templates);

        mockMvc.perform(MockMvcRequestBuilders
                .get(Urls.TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(template.getName())));
    }
}
