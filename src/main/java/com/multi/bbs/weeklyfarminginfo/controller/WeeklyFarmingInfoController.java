package com.multi.bbs.weeklyfarminginfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.weeklyfarminginfo.model.service.WeeklyFarmingInfoService;
import com.multi.bbs.weeklyfarminginfo.model.vo.WeeklyFarmingInfo;

import lombok.extern.slf4j.Slf4j;

//@RequestMapping("/agriculture") // 요청 url의 상위 url을 모두 처리할때 사용
@Slf4j
@Controller
public class WeeklyFarmingInfoController {
	
	@Autowired
	private WeeklyFarmingInfoService weeklyService;
	
//	@GetMapping("/weeklyFarmingInfo")
//	String weeklyFarmingInfo(Model model, String userId, String userPwd) {
//		return "agriculture/weeklyFarmingInfo";
//	}
	
	@GetMapping("/agriculture/weeklyFarmingInfo")
	public String wlist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
//			String searchType = paramMap.get("searchType");
//			if(searchType != null && searchType.length() > 0) {
//				searchMap.put("searchType", searchType);
//			}
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				System.out.println(searchType);
				searchMap.put(searchType, searchValue);
			}
			else {
				paramMap.put("searchType", "subject");
			}
			
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int wboardCount = weeklyService.getWeeklyFarmingInfoCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, wboardCount, 10);
		List<WeeklyFarmingInfo> wlist = weeklyService.getWeeklyFarimgInfoList(pageInfo, searchMap);
		
		model.addAttribute("wlist", wlist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("wboardCount", wboardCount);
		return "agriculture/weeklyFarmingInfo";
	}

}
