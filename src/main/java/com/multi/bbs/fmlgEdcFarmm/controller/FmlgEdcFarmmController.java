package com.multi.bbs.fmlgEdcFarmm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.fmlgEdcFarmm.model.service.FmlgEdcFarmmService;
import com.multi.bbs.fmlgEdcFarmm.model.vo.FmlgEdcFarmm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FmlgEdcFarmmController {
	
	@Autowired
	private FmlgEdcFarmmService service;
	
	@GetMapping("/life/ruralEducationCenterList")
	public String list(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			String searchValue2 = paramMap.get("searchValue2");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue);
			}else if(searchValue2 != null && searchValue2.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue2);
			}else {
				paramMap.put("searchType", "all");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		if(paramMap.containsKey("sort") == false) {
			searchMap.put("sort", "normal");
		}
		searchMap.put("sort", "bycntntsSj");
		searchMap.put("sort", "byTheme");
		searchMap.put("sort", "byLocplc");
		
		int fmlgEdcFarmmDtlCount = service.getfmlgEdcFarmmDtCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, fmlgEdcFarmmDtlCount, 9);
		List<FmlgEdcFarmm> list = service.getFmlgEdcFarmmList(pageInfo, searchMap);
		
		model.addAttribute("list", list);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("fmlgEdcFarmmDtlCount", fmlgEdcFarmmDtlCount);
//		System.out.println(list);
		return "/life/ruralEducationCenterList";
	}
	
	// view 페이지
	@RequestMapping("/life/ruralEducationCenterDetails")
	public String view(Model model, @RequestParam("cntntsNo") String cntntsNo) {
		FmlgEdcFarmm fmlgEdcFarmm = service.findByNo(cntntsNo);
		
		// 여기서부터는 다른 약초 알아보기에 필요한 코드
		List<FmlgEdcFarmm> otherList = service.getfmlgEdcFarmmDtlOtherList(cntntsNo);
		
		model.addAttribute("fmlgEdcFarmm", fmlgEdcFarmm);
		model.addAttribute("otherList", otherList);
//		System.out.println(fmlgEdcFarmm);
		return "/life/ruralEducationCenterDetails";
	}
	
}

