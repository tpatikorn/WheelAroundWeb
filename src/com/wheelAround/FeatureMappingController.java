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
public class FeatureMappingController {

	@Autowired
	private LoginDelegate loginDelegate;

	@RequestMapping(value = "/rentSelectedVehicle", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean loginBean) {
		try {
			ModelAndView model = null;
			List<VehiclesListBean> vListBean = loginBean.getvAndPriceListBean();
			List<VehiclesListBean> vListBeannew = new ArrayList<VehiclesListBean>();
			List<FeatureList> featureList = null;
			for (VehiclesListBean vbean : vListBean) {
				if (vbean.isCheckBoxForVehicle()) {
					String[] key = vbean.getKeysForVehicles().split("@@");
					featureList = loginDelegate.getFeatureList(key[0]);
					vListBeannew.add(vbean);
				}
			}
			loginBean.setFeatureList(featureList);
			loginBean.setvAndPriceListBean(vListBeannew);
			model = new ModelAndView("selectedVehicleFeature", "loginBean", loginBean);
			return model;

		} catch (Exception e)

		{
			e.printStackTrace();
			return null;
		}

	}
}
