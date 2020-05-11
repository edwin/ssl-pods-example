package com.redhat.edwin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 *     com.redhat.edwin.controller.IndexController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 09 Mei 2020 15:38
 */
@RestController
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWorld() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("https://ssl-pods-example-2:8443/", String.class);

        return response.getBody();
    }
}