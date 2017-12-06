package com.project.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.domain.User;
import com.project.api.domain.UserData;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {

	private String api_url;
	private RestTemplate template;
	
	public ApiServiceImpl(RestTemplate template, @Value("${api.url}") String api_url) {
		super();
		this.template = template;
		this.api_url = api_url;
	}

	@Override
	public List<User> getUsers(Integer limit) {
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(api_url).queryParam("limit", limit);
		UserData data = template.getForObject( uriBuilder.toUriString() , UserData.class);
		
		if( data == null ) return null;
		
		return data.getData();
	}
	
	@Override
	public Flux<User> getUsers(Mono<Integer> limit)
	{
		return WebClient
				.create( api_url )
				.get()
				.uri( uriBuilder -> uriBuilder.queryParam("limit", limit.block()).build() )
				.accept( MediaType.APPLICATION_JSON )
				.exchange()
				.flatMap(resp -> resp.bodyToMono(UserData.class))
                .flatMapIterable(UserData::getData);
	}

}
