package com.matija.softtehn.controller.integration;

import com.matija.softtehn.SofttehnApplication;
import com.matija.softtehn.controller.Urls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SofttehnApplication.class)
@AutoConfigureMockMvc
public class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String templateJson;

    @Before
    public void setUp() {
        templateJson = "{\n" +
                "    \"name\": \"racun\",\n" +
                "    \"description\": \"Fiskalni racun\",\n" +
                "    \"templateFields\": [\n" +
                "        {\n" +
                "            \"name\": \"prodavac\",\n" +
                "            \"type\": \"STRING\",\n" +
                "            \"required\": true\n" +
                "        },\n" +
                "        {\n" +
                "        \t\"name\": \"stavka\",\n" +
                "        \t\"type\": \"COMPLEX\",\n" +
                "        \t\"isList\": true,\n" +
                "        \t\"fields\": [\n" +
                "        \t\t{\n" +
                "        \t\t\t\"name\": \"ime\",\n" +
                "        \t\t\t\"type\": \"STRING\",\n" +
                "        \t\t\t\"required\": true\n" +
                "        \t\t},\n" +
                "        \t\t{\n" +
                "        \t\t\t\"name\": \"cena\",\n" +
                "        \t\t\t\"type\": \"NUMBER\",\n" +
                "        \t\t\t\"required\": true\n" +
                "        \t\t}\n" +
                "        \t]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"kupac\",\n" +
                "            \"description\": \"Podaci o kupcu\",\n" +
                "            \"type\": \"COMPLEX\",\n" +
                "            \"required\": true,\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"name\": \"ime\",\n" +
                "                    \"type\": \"STRING\",\n" +
                "                    \"required\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"prezime\",\n" +
                "                    \"type\": \"STRING\",\n" +
                "                    \"required\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"datum rodjenja\",\n" +
                "                    \"type\": \"DATE\",\n" +
                "                    \"required\": true\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    @Test
    public void testTemplateCreateMockMvc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(Urls.TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(templateJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
