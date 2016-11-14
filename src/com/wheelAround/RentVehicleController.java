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
public class RentVehicleController {
	@ Autowired private LoginDelegate loginDelegate;
	
	
	@RequestMapping(value="/availableVehicles",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginBean")LoginBean loginBean)
	{
		ModelAndView model= null;
		List<VehiclesListBean> vListBean = new ArrayList<VehiclesListBean>();
		List<FeatureList> featureList = new ArrayList<FeatureList>();
		try
		{	Map<String,String> availableVehicles = loginDelegate.getValuesList(loginBean);
			List<String> availableFeature = loginDelegate.getFeatureList();
		for (Map.Entry<String, String> entry : availableVehicles.entrySet()) {
			VehiclesListBean vBean = new VehiclesListBean();
			
			String[] splitEntryValues = entry.getValue().split("~~");
			String entryKey = entry.getKey(); 
			vBean.setModelName(splitEntryValues[0]);
			vBean.setModelType(splitEntryValues[1]);
			vBean.setBasePrice(splitEntryValues[2]);
			vBean.setGarageName(splitEntryValues[3]);
			vBean.setGarageLocation(splitEntryValues[4]);
			vBean.setKeysForVehicles(entryKey);
			vListBean.add(vBean);
			
		}
		for(String availableFea : availableFeature){
			FeatureList fList = new FeatureList();
			String[] availableFeatureArray = availableFea.split("~~");
			fList.setFeatureId(availableFeatureArray[1]);
			fList.setFeatureName(availableFeatureArray[0]);
			fList.setFeaturePrice(availableFeatureArray[2]);
			featureList.add(fList);
		}
		
		loginBean.setvAndPriceListBean(vListBean);
		loginBean.setFeatureList(featureList);
		
		
		model = new ModelAndView("vehicleAndPrice", "loginBean", loginBean);
		
			
		}
		catch(Exception e)
		
		{	e.printStackTrace();
		}
		return model;
	}

}
