package com.jslss.board.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.RegistrationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jslss.board.consts.Constants;
import com.jslss.board.consts.dto.RegistrationForm;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;
import com.jslss.board.repository.RoleRepository;
import com.jslss.board.repository.UserRepository;

@Controller
public class UserController implements Constants{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value= "/register", method = RequestMethod.GET)
	public String showRegisterUser(Model model) {
		
		LOGGER.debug("Rendering view for register user ");

		return "new_user";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerBoard(@Valid RegistrationForm registrationForm,
			BindingResult result, WebRequest request,
			RedirectAttributes redirectAttributes) {

		System.out.println("firstName: " + request.getParameter("firstName"));
		System.out.println("lastName: " + request.getParameter("lastName"));
		System.out.println("email: " + request.getParameter("email"));
		System.out.println("password: " + request.getParameter("password"));
		
		if(result.hasErrors()){
			System.out.println(result.getAllErrors());
			//redirectAttributes.addAttribute("error", "Password should be more than 3 characters and less than 40");
			
			return "new_user";
		}

		User user = User.createUser(
				request.getParameter("firstName"), 
				request.getParameter("lastName"),
				request.getParameter("email"),
				request.getParameter("password"));
		userRepository.save(user);
		
		Role role = new Role();
		role.setRole(ROLE_USER);
		role.setUser(user);
		roleRepository.save(role);
		
		return "home";
	}
}
