package com.slyak.cms.widgets.slider;

import java.util.Map;

import org.apache.shiro.util.StringUtils;
import org.springframework.ui.ModelMap;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.enums.InputType;

@Widgets("slider")
public class SliderWidgets {

	@Widget(settings = {
			@Setting(key = "items", value = "",inputType=InputType.TEXTAREA),
			@Setting(key = "itemWidth", value = "") })
	public String flexSlider(com.slyak.cms.core.model.Widget widget, ModelMap modelMap){
		Map<String,String> settings = widget.getSettings();
		String items = settings.get("items");
		modelMap.put("items", items.split(","));
		String itemWidth = settings.get("itemWidth");
		if (StringUtils.hasText(itemWidth)) {
			modelMap.put("itemWidth", itemWidth);
		}
		return "flexSlider.tpl";
	}
	
	@Widget(settings = {
			@Setting(key = "images", value = "/widgetResource/slider/demo/slide1.jpg,/widgetResource/slider/demo/slide2.jpg,/widgetResource/slider/demo/slide3.jpg") })
	public String carousel(com.slyak.cms.core.model.Widget widget, ModelMap modelMap){
		Map<String,String> settings = widget.getSettings();
		String images = settings.get("images");
		modelMap.put("images", images.split(","));
		return "carousel.tpl";
	}
	
	
	@Widget(settings = {@Setting(key="content",value=" <a href=''><img src='/widgetResource/slider/nivo-slider/demo/images/up.jpg' data-thumb='/widgetResource/slider/nivo-slider/demo/images/up.jpg' alt='' title='This is an example of a caption' /></a>",inputType=InputType.TEXTAREA),@Setting(key="options",value="{controlNav:false}",inputType=InputType.TEXTAREA)})
	public String nivoSlider(){
		return "nivoSlider.tpl";
	}

}
