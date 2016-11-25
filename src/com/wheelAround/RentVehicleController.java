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

import com.wheelAround.model.FeatureList;
import com.wheelAround.model.LoginDelegate;
import com.wheelAround.model.VehiclesListBean;
import com.wheelAround.model.dao.LoginBean;

@Controller
@RequestMapping("/")
public class RentVehicleController {
	@ Autowired private LoginDelegate loginDelegate;
	
	
	@RequestMapping(value="/availableVehicles",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	{
		ModelAndView model= null;
		if(loginBean.getVlBean() != null){
			
		}
		else{
		List<VehiclesListBean> vListBean = new ArrayList<VehiclesListBean>();
		
		try
		{	Map<String,String> availableVehicles = loginDelegate.getValuesList(loginBean);
			
		for (Map.Entry<String, String> entry : availableVehicles.entrySet()) {
			VehiclesListBean vBean = new VehiclesListBean();
			
			String[] splitEntryValues = entry.getValue().split("~~");
			String entryKey = entry.getKey(); 
			vBean.setModelName(splitEntryValues[0]);
			//vBean.setModelType(splitEntryValues[1]);
			vBean.setBasePrice(splitEntryValues[2]);
			vBean.setGarageName(splitEntryValues[3]);
			vBean.setGarageLocation(splitEntryValues[4]);
			vBean.setKeysForVehicles(entryKey);
			vListBean.add(vBean);
			
		}
		
		loginBean.setvAndPriceListBean(vListBean);
		
		model = new ModelAndView("vehicleAndPrice", "loginBean", loginBean);
		
		}
		catch(Exception e)
		
		{	e.printStackTrace();
		}
		}
		return model;
	}

}
