/**
 * Project name : slyak-web
 * File name : AccountController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slyak.core.util.JsonUtils;
import com.slyak.model.AccountLog;
import com.slyak.model.AccountLogMember;
import com.slyak.model.AccountLogType;
import com.slyak.model.Report;
import com.slyak.service.AccountService;
import com.slyak.user.model.User;
import com.slyak.user.util.LoginUserHelper;
import com.slyak.util.DateUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-6
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	private static final String RESULT_BY_TYPE="t";
	private static final String DATE_BY_YEAR="y";
	private static final String DEFAULT_OWNER_NAME="_slyak_default_";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@Autowired
	private AccountService accountService;
	
	@ModelAttribute
	public void populateModel(Model model) {
       model.addAttribute("nav", "account");
    }
	
	@RequestMapping("/log")
	public String log(Pageable pageable,ModelMap modelMap){
		return logByType(pageable,modelMap,0);
	}
	
	@RequestMapping("/log/{logTypeId}")
	public String logByType(Pageable pageable,ModelMap modelMap,@PathVariable long logTypeId){
		User user = LoginUserHelper.getLoginUser();
		AccountLogType checked = accountService.findAccountLogType(logTypeId,user.getId());
		List<AccountLogType> rootLogTypes = accountService.findRootAccountLogTypes(user.getId());
		if(checked == null && !CollectionUtils.isEmpty(rootLogTypes)){
			checked = rootLogTypes.get(0);
		}
		if(checked!= null){
			modelMap.put("rootTypes", rootLogTypes);
			modelMap.put("logPage", accountService.findAccountLogs(pageable, checked.getId(), user.getId()));
			AccountLogType parent = checked.getParent();
			if(parent == null){
				modelMap.put("checkedRt", checked);
			}else{
				modelMap.put("checkedRt", parent);
				modelMap.put("checkedCt", checked);
			}
		}
		modelMap.put("subnav", "log");
		return "account.log";
	}
	
	
	@RequestMapping("/addLog")
	public String addLog(AccountLog accountLog){
		accountLog.setUser(LoginUserHelper.getLoginUser());
		accountService.saveAccountLog(accountLog);
		return "redirect:/account/log";
	}
	
	@RequestMapping("/logForm")
	public String logForm(ModelMap modelMap,Long typeId){
		List<AccountLogType> rootLogTypes =  accountService.findRootAccountLogTypes(LoginUserHelper.getLoginUser().getId());
		
		modelMap.put("rootLogTypes", rootLogTypes);
		
		if(typeId!=null&&typeId>0){
			lable: for (AccountLogType rt : rootLogTypes) {
				List<AccountLogType> ctypes = rt.getChildren();
				for (AccountLogType ct : ctypes) {
					if(ct.getId().longValue() == typeId.longValue()){
						modelMap.put("activeRootTypeId", rt.getId());
						break lable;
					}
				}
			}
		}
		
		modelMap.put("logMembers", accountService.getUserAccountLogMembers(LoginUserHelper.getLoginUserId()));
		
		modelMap.put("subnav", "logForm");
		return "account.logForm";
	}
	
	@RequestMapping("/logTypes")
	public String logTypes(ModelMap modelMap){
		List<AccountLogType> rootLogTypes =  accountService.findRootAccountLogTypes(LoginUserHelper.getLoginUser().getId());
		modelMap.put("rootLogTypes", rootLogTypes);
		modelMap.put("subnav", "logTypes");
		return "account.logTypes";
	}
	
	@RequestMapping("/logMembers")
	public String logMembers(ModelMap modelMap){
		modelMap.put("logMembers", accountService.getUserAccountLogMembers(LoginUserHelper.getLoginUserId()));
		modelMap.put("subnav", "logMembers");
		return "account.logMembers";
	}
	
	@RequestMapping("/nameForm/{type}")
	public String nameForm(@PathVariable String type,ModelMap map){
		map.put("formType", type);
		return "alone:account.nameForm";
	}
	
	@RequestMapping("/logReport")
	public String logReport(ModelMap modelMap) throws ParseException{
		return logReportByLogTypeIdAndResultBy(0,null, modelMap);
	}
	
	@RequestMapping("/logReport/{logTypeId}")
	public String logReportByTypeId(@PathVariable long logTypeId,ModelMap modelMap) throws ParseException{
		return logReportByLogTypeIdAndResultBy(logTypeId,null,modelMap);
	}
	
	private Comparator<Report> reportComparator = new Comparator<Report>() {
		@Override
		public int compare(Report r1, Report r2) {
			if(r1.getOwnerId() == null){
				return -1;
			}else{
				return 1;
			}
		}
	};
	
	@RequestMapping("/logReport/{logTypeId}/{resultBy}")
	public String logReportByLogTypeIdAndResultBy(@PathVariable long logTypeId,@PathVariable String resultBy,ModelMap modelMap) throws ParseException{
		User user = LoginUserHelper.getLoginUser();
		AccountLogType checked = null;
		if(logTypeId>0){
			checked = accountService.findAccountLogType(logTypeId,user.getId());
		}
		List<AccountLogType> rootLogTypes = accountService.findRootAccountLogTypes(user.getId());
		modelMap.put("rootTypes", rootLogTypes);
		if(checked==null){
			checked = rootLogTypes.get(0);
		}
		modelMap.put("checked", checked);
		
		boolean resultByMember = true;
		boolean dateByMonth = true;
		Date from= null,to = null;
		Date current = new Date();
		
		
		int field = dateByMonth ? Calendar.DAY_OF_MONTH : Calendar.DAY_OF_YEAR;
		
		//eg  t;y;2012-2013
		if(StringUtils.isNotBlank(resultBy)){
			String[] paramValues = StringUtils.split(resultBy,';');
			if(paramValues!=null&&paramValues.length==3){
				if(StringUtils.equals(paramValues[0], RESULT_BY_TYPE)){
					resultByMember = false;
				}
				if(StringUtils.equals(paramValues[1], DATE_BY_YEAR)){
					dateByMonth = false;
				}
				String[] dateRegionStrs = StringUtils.split(paramValues[2],'-');
				int len = 0;
				if(dateRegionStrs!=null&& (len = dateRegionStrs.length)>0){
					DateFormat df = dateByMonth? new SimpleDateFormat("yyyy:MM"):new SimpleDateFormat("yyyy");
					Date date = df.parse(dateRegionStrs[0]);
					if(len==1){
						from = DateUtils.getFirstDayOf(date,field).getTime();
						to = from;
					}else{
						from = date;
						to = df.parse(dateRegionStrs[1]);
					}
				}
			}
		}
		
		//若from和to都为空时，默认统计当前月的天数
		if(from == null && to == null){
			field = Calendar.DAY_OF_MONTH;
		}
		
		if(from == null){
			from = DateUtils.getFirstDayOf(current, field).getTime();
		}
		
		if(to == null){
			to = from;
		}
		
		Date tmp = null;
		//switch
		if(from.getTime()>to.getTime()){
			tmp = from;
			from = to;
			to = tmp;
		}
		
		List<Report> reports = null;
 		if(resultByMember){
			reports = accountService.getLogReportByParentLogTypeIdGroupByMember(user.getId(),checked==null?null:checked.getId(),from,to, dateByMonth);
			modelMap.put("by","member");
		} else{
			reports =accountService.getLogReportByParentLogTypeIdGroupByType(user.getId(),checked==null?null:checked.getId(),from,to, dateByMonth);
			modelMap.put("by", "type");
		}
		
		modelMap.put("subnav", "logReport");
		
		modelMap.put("reports", reports);
		
		int fromTime = DateUtils.get(from, field);
		int toTime = DateUtils.get(to, field);

		if(fromTime==toTime){
			fromTime = 1;
			if(dateByMonth){
				toTime = DateUtils.getLastDayBegining(from, Calendar.DAY_OF_MONTH).get(Calendar.DAY_OF_MONTH);
			}else{
				toTime = 12;
			}
		}
		
		List<Integer> categories = new ArrayList<Integer>();
		for(int i= fromTime;i<=toTime;i++){
			//categories ,time region
			categories.add(i);
		}
		
		modelMap.put("categories", JsonUtils.toJSON(categories));
		

		List<String> owners = new ArrayList<String>();
		
		//series
		//name and data
		Map<String,List<BigDecimal>> nameAndData = new LinkedHashMap<String,List<BigDecimal>>();
		if(!CollectionUtils.isEmpty(reports)){
			//让没有ownerId的放后面
			Collections.sort(reports, reportComparator);
			String ownerName = DEFAULT_OWNER_NAME;
			for (Report report : reports) {
				Long ownerId = report.getOwnerId();
				if(ownerId != null){
					if(resultByMember){
						AccountLogMember accountLogMember = accountService.findAccountLogMember(ownerId);
						ownerName = accountLogMember.getName();
					} else{
						AccountLogType accountLogType = accountService.findAccountLogType(ownerId);
						ownerName = accountLogType.getName();
					}
				}
				List<BigDecimal> unitList = nameAndData.get(ownerName);
				if(unitList==null){
					unitList = new ArrayList<BigDecimal>();
					for(int i= fromTime;i<=toTime;i++){
						unitList.add(BigDecimal.valueOf(0));
					}
					nameAndData.put(ownerName, unitList);
				}
				unitList.set(report.getTime()-fromTime, report.getUnits());
				
				if(!owners.contains(ownerName)){
					owners.add(ownerName);
				}
			}
		}
		
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		if(owners.size()>0){
			for (int i = 0; i < owners.size(); i++) {
				Map<String,Object> serieItem = new HashMap<String, Object>();
				serieItem.put("name", owners.get(i));
				serieItem.put("data", nameAndData.get(owners.get(i)));
				series.add(serieItem);
			}
		}
		
		modelMap.put("series", JsonUtils.toJSON(series));
		
		//averages
		
		if(owners.size()>1){
			List<BigDecimal> averages = new ArrayList<BigDecimal>();
			for(int i= 0;i<=toTime-fromTime;i++){
				averages.add(BigDecimal.valueOf(0));
			}
			Iterator<List<BigDecimal>> avgIt = nameAndData.values().iterator();
			BigDecimal divisor = BigDecimal.valueOf(owners.size());
			while(avgIt.hasNext()){
				List<BigDecimal> unitsList = avgIt.next();
				for(int i= 0;i<=toTime-fromTime;i++){
					averages.set(i, averages.get(i).add(unitsList.get(i).divide(divisor,BigDecimal.ROUND_HALF_UP)));
				}
			}
			modelMap.put("averages", JsonUtils.toJSON(averages));
		}
		//pie
		Map<String,BigDecimal> pieData = new HashMap<String, BigDecimal>();
		Iterator<List<BigDecimal>> pieIt = nameAndData.values().iterator();
		int n = 0;
		while(pieIt.hasNext()){
			List<BigDecimal> unitsList = pieIt.next();
			for(int j= 0;j<unitsList.size();j++){
				String owner = owners.get(n);
				BigDecimal result = pieData.get(owner);
				pieData.put(owner, result==null?BigDecimal.valueOf(0):result.add(unitsList.get(j)));
			}
			n++;
		}
		List<Map<String,Object>> pie = new ArrayList<Map<String,Object>>();
		if(owners.size()>0){
			for (int i = 0; i < owners.size(); i++) {
				Map<String,Object> pieItem = new HashMap<String, Object>();
				pieItem.put("name", owners.get(i));
				pieItem.put("y", pieData.get(owners.get(i)));
				pie.add(pieItem);
			}
		}
		modelMap.put("pie", JsonUtils.toJSON(pie));
		
		return "account.logReport";
	}
	
	
	@RequestMapping("/type/save")
	public String saveType(AccountLogType accountLogType){
		accountLogType.setChildren(null);
		accountLogType.setUser(LoginUserHelper.getLoginUser());
		accountService.saveAccountLogType(accountLogType);
		return "redirect:/account/logTypes";
	}
	
	@RequestMapping("/type/delete")
	@ResponseBody
	public boolean deleteTypes(@RequestBody AccountLogType[] accountLogTypes){
		accountService.deleteTypes(Arrays.asList(accountLogTypes));
		return true;
	}
	
	@RequestMapping("/member/save")
	public String saveMember(AccountLogMember accountLogMember){
		accountLogMember.setUser(LoginUserHelper.getLoginUser());
		accountService.saveAccountLogMember(accountLogMember);
		return "redirect:/account/logMembers";
	}
	
	@RequestMapping("/member/delete")
	@ResponseBody
	public boolean deleteMembers(@RequestBody AccountLogMember[] accountLogMembers){
		accountService.deleteMembers(Arrays.asList(accountLogMembers));
		return true;
	}
}
