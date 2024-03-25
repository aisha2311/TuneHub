package com.tunehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.entities.Users;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class UsersController {
	@Autowired
	UsersService service;
   @PostMapping("/registration")
   public String addUsers(@ModelAttribute Users user ) {
	   boolean userStatus = service.emailExists(user.getEmail());
	if(userStatus == false) {
		 service.addUser(user);
		 System.out.println("user added");
		 return "home";
	}
	else {
		System.out.println("user exist");
		return "login";
	}
		   
	 //  return "home";
   }
   
   @PostMapping("/validate")
   public String validate(@RequestParam("email")String email,
		   @RequestParam("password") String password , HttpSession session 
		    ) {
	   if(service.validateUser(email,password)== true) {
		   String role = service.getRole(email);
		   
		   session.setAttribute("email", email);
			if(role.equals("admin")) {
				return "adminHome";			
			}
			else {
				return "usersHome";
			}
	   }
	   else {
		   return "login";
	   }
   }
   
  
   @GetMapping("/logout")
   public String logout(HttpSession session) {
	   session.invalidate();
	   return"login";
   }
   
}
