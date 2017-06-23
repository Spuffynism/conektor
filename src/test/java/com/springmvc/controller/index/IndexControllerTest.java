package com.springmvc.controller.index;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void overrideHostnameVerifier() {
        setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost")
        );
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(this.restTemplate.getForObject("https://localhost:8443", String.class))
                .contains("this is conektor");
    }
}
