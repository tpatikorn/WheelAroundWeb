package com.wheelAround;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wheelAround.model.LoginDelegate;
import com.wheelAround.model.VehicleTpeListBean;
import com.wheelAround.model.dao.LoginBean;
import com.wheelAround.model.dao.RegistrationBean;



@Controller
@RequestMapping("/")
public class LoginController {

@ Autowired private LoginDelegate loginDelegate;




@RequestMapping(value="/newUserRegister",method=RequestMethod.GET)
public ModelAndView createNewUser(HttpServletRequest request, HttpServletResponse response)
{	ModelAndView model = new ModelAndView("newUserRegistrationForm");
	RegistrationBean registrationBean = new RegistrationBean();
	model.addObject("registrationBean", registrationBean);
	return model;
}


@RequestMapping(value="/login",method=RequestMethod.GET)
public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response)
{	ModelAndView model = new ModelAndView("validateLogin");
	LoginBean loginBean = new LoginBean();
	model.addObject("loginBean", loginBean);
	return model;
}

@RequestMapping(value="/login",method=RequestMethod.POST)
public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
{
	ModelAndView model= null;
	try
	{	String isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
		if(isValidUser != null)
		{
			System.out.println("User Login Successful");
			loginBean.setCid(isValidUser);
			request.setAttribute("loggedInUser", loginBean.getUsername());
			Map<String,String> vehicleTypes = loginDelegate.getTypeList();
			List<VehicleTpeListBean> vType = new ArrayList<VehicleTpeListBean>();
			
			for (Map.Entry<String, String> entry : vehicleTypes.entrySet()) {
				VehicleTpeListBean tp = new VehicleTpeListBean();
				String[] values = entry.getValue().split("~~");
				tp.setTypeId(values[0]);
				tp.setTypeName(values[1]);
				tp.setTypePrice(values[2]);	
				vType.add(tp);
			}
			loginBean.setvTypeBean(vType);
			
			model = new ModelAndView("vehicle_rent", "loginBean", loginBean);
			request.getSession().setAttribute("credentials", loginBean.getCid());
			model.addObject("credentials", loginBean.getCid());
		}
		else
		{
			model = new ModelAndView("Login");
			model.addObject("loginBean", loginBean);
			request.setAttribute("message", "Invalid credentials!!");
		}
	}
	catch(Exception e)
	
	{	e.printStackTrace();
	}
	return model;
}
}