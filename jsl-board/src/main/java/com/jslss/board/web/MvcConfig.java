package com.jslss.board.web;

import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Controller
public class MvcConfig extends WebMvcConfigurerAdapter {
    
	@RequestMapping({"/", "/home"})
	public String home(Map<String, Object> model) {
		model.put("message", "JSL Portal");
		model.put("title", "Welcome");
		model.put("date", new Date());
		return "home";
	}
	
	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/login").setViewName("login");
    }
}
