package com.multi.bbs.nongsaroEventList.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.nongsaroEventList.model.service.NongsaroEventListService;
import com.multi.bbs.nongsaroEventList.model.vo.NongsaroEventList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NongsaroEventListController {
	
	@Autowired
	private NongsaroEventListService service;
	
	@GetMapping("/life/eventList")
	public String list(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue);
			}else {
				paramMap.put("searchType", "all");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int nongsaroEventListCount = service.getNongsaroEventListCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, nongsaroEventListCount, 9);
		List<NongsaroEventList> list = service.getNongsaroEventListList(pageInfo, searchMap);
		
		
		model.addAttribute("list", list);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("nongsaroEventListCount", nongsaroEventListCount);
//		System.out.println(list);
		return "/life/eventList";
	}
	

}

