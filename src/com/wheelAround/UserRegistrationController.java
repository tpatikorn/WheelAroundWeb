package com.wheelAround;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wheelAround.model.LoginDelegate;
import com.wheelAround.model.dao.RegistrationBean;

@Controller
@RequestMapping("/wheelAround")
public class UserRegistrationController {

@ Autowired private LoginDelegate loginDelegate;


@RequestMapping(value="/register",method=RequestMethod.POST)
public ModelAndView createNewUser(HttpServletRequest request, HttpServletResponse response, 
		@ModelAttribute("loginBean")RegistrationBean registrationBean)
{	
	ModelAndView model= null;
	try
	{	boolean registerForNewCustomer = loginDelegate.registerNewUser(registrationBean);
		if(registerForNewCustomer)	
		{	
			model = new ModelAndView("vehicle_rent");
			System.out.println("User Registration Successful");
		}
	}
	catch(Exception e)
	
	{	e.printStackTrace();
	}
	return model;
}

}
