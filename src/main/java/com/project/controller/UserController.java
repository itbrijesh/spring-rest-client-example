package com.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

import com.project.api.service.ApiService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	private ApiService apiService;

	public UserController(ApiService apiService) {
		super();
		this.apiService = apiService;
	}
	
	@GetMapping( {"", "/", "/index"} )
	public String index()
	{
		return "index";
	}
	
	@PostMapping("/users")
	/*public String formPost(Model model, ServerWebExchange exchange)
	{
		System.out.println( "UserController >> formPost !!! ");
		
		MultiValueMap<String, String> map = exchange.getFormData().block();
		
		Integer limit = new Integer( map.get("limit").get( 0 ) );
		
		System.out.println( "Limit : " + limit );
		
		limit = limit == null || limit == 0 ? 10 : limit;
		
		model.addAttribute("users", apiService.getUsers(limit));
		
		
		return "userlist";
	}*/
	public String formPost(Model model, HttpServletRequest request ) // )ServerWebExchange serverWebExchange
	{
		Integer limit = new Integer( request.getParameter( "limit" ) );
		
		System.out.println( "Limit : " + limit );
		
		limit = limit == null || limit == 0 ? 10 : limit;
		
		model.addAttribute("users", apiService.getUsers(limit)); 
		
		/* -- Reactive Programming --
		model.addAttribute("users",
                apiService
                        .getUsers(serverWebExchange
                                .getFormData()
                                .map(data -> new Integer(data.getFirst("limit")))));
		*/
		return "userlist";
	}
}
