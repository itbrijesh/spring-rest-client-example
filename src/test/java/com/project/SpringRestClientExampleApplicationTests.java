package com.project;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import com.project.api.domain.User;
import com.project.api.service.ApiService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRestClientExampleApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	ApiService apiService;
	
	@Test
	public void testGetUsers() 
	{
		List<User> users = apiService.getUsers( 4 );
		System.out.println( "-----TEST--------Test get users------------------------------------------------" );
		System.out.println( "Size: " + users.size() + " Data: "+ users );
	}
	
	
	@Autowired
    ApplicationContext applicationContext;

    WebTestClient webTestClient;
    
    @Before
    public void setUp() throws Exception {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void index() throws Exception {

        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void formPost() throws Exception {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap();
        formData.add("limit", "3");

        webTestClient.post().uri("users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk();
    }
}
