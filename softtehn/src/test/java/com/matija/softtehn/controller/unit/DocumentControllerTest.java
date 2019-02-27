package com.matija.softtehn.controller.unit;

import com.matija.softtehn.controller.DocumentController;
import com.matija.softtehn.model.Document;
import com.matija.softtehn.model.Template;
import com.matija.softtehn.service.DocumentService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Test
    public void givenTemplate_whenGetDocumentsByTemplateName_thenReturnDocumentJsonArray() throws Exception {
        Document doc1 = new Document();
        Document doc2 = new Document();
        Template template = new Template();
        template.setName("test template");

        Mockito
            .when(documentService.findDocumentsByTemplateName(template.getName()))
            .thenReturn(Arrays.asList(doc1, doc2));

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/document/" + template.getName()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(2)));
    }
}
