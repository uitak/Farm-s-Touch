package com.multi.bbs.safety.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.safety.model.service.SafetyService;
import com.multi.bbs.safety.model.vo.Safety;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j 선언을 대신 선언 해주는 lombok 어노테이션
@Controller
public class SafetyController {

	@Autowired
	private SafetyService sservice;
	
//	@GetMapping("agriculture/safety")
//	String safety(Model model) {
//	return "agriculture/safety";
//	}
	
	@GetMapping("/agriculture/safety")
	public String slist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
//				String searchType = paramMap.get("searchType");
				String searchType = "all";
				searchMap.put(searchType, searchValue);
			}else {
//				paramMap.put("searchType", "cntntsSj");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		int safetyCount = sservice.getSafetyCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 7, safetyCount, 8);
		List<Safety> slist = sservice.getSafetyList(pageInfo, searchMap);
		
		model.addAttribute("slist", slist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("safetyCount", safetyCount);
		return "agriculture/safety";
	}

	
}
