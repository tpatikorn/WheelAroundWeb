package com.wheelAround;

import java.util.ArrayList;
import java.util.List;

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
public class PriceMappingCalculator {
	@Autowired private LoginDelegate loginDelegate;

	@RequestMapping(value = "/calPrice", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean) {
		try {
 			ModelAndView model = null;
			String typeid="";
			ArrayList<String> fids = new ArrayList<String>();
			List<FeatureList> finalFeaturesList = new ArrayList<FeatureList>();
			for(VehiclesListBean v : loginBean.getvAndPriceListBean() ){
				typeid = v.getKeysForVehicles().split("@@")[2];
			}
			for(FeatureList f : loginBean.getFeatureList()){
				if(f.isCheckBoxForVehicle()){
					finalFeaturesList.add(f);
					fids.add(f.getFeatureId());
				}
			}
			double finalAmount = loginDelegate.calculateFinalAmount(fids, typeid);
			model = new ModelAndView("reserveAndPurchase", "loginBean", loginBean);
			model.addObject("amtPerHour", finalAmount);
			model.addObject("vehDetails", loginBean.getvAndPriceListBean());
			model.addObject("featureDetails", finalFeaturesList);
			return model;

		} catch (Exception e)

		{
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	@RequestMapping(value = "/reserveAndFinalize", method = RequestMethod.POST)
	public ModelAndView reserveSelectedVehice(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean) {
		try {
			ModelAndView model = null;
			String vid = loginBean.getRid().split("@@")[0];
			String cid = (String) request.getSession().getAttribute("credentials");
			String finalAmount = loginDelegate.reserveVehicleForCustomer(cid, vid, loginBean.getStartDate(), loginBean.getEndDate(), loginBean.getPricePerHour());
			model = new ModelAndView("thanksAndReturn", "loginBean", loginBean);
			model.addObject("fullPrice", finalAmount.split("@@@")[0]);
			model.addObject("keyCode", finalAmount.split("@@@")[1]);
			return model;

		} catch (Exception e)

		{
			e.printStackTrace();
			return null;
		}

	}

}
