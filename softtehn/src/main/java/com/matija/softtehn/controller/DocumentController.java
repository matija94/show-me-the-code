package com.matija.softtehn.controller;

import com.matija.softtehn.model.Document;
import com.matija.softtehn.model.UserPrincipal;
import com.matija.softtehn.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @PostMapping(Urls.DOCUMENT)
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        documentService.createDocument(document);
        return new ResponseEntity(document, HttpStatus.CREATED);
    }

    @GetMapping(Urls.DOCUMENT_BY_TEMPLATE_NAME)
    public ResponseEntity<List<Document>> getDocumentsByTemplateName(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("template_name") String templateName) {

        List<Document> documents = documentService.findDocumentsByGroupAndTemplateName(
                principal.getUser().getGroup(), templateName);
        return new ResponseEntity(documents, HttpStatus.OK);
    }
}
