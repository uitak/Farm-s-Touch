package com.multi.bbs.agriculture.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.agriculture.model.service.VarietyService;
import com.multi.bbs.agriculture.model.vo.Variety;
import com.multi.bbs.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/agriculture")
@Controller
public class VarietyController {
	
	@Autowired
	private VarietyService service;
	
	@GetMapping("farmingVarietiesList")
	public String list(Model model, @RequestParam Map<String, String> paramMap ) {
		int page = 1;
		
		Map<String, String> searchMap = new HashMap<String, String>();
		
		try {
			String searchName = paramMap.get("searchName");
			String searchType = paramMap.get("searchType");
			String searchSubType = paramMap.get("searchSubType");
			String searchYear = paramMap.get("searchYear");
			
			if(searchType != null && searchType.length()>0) {
				searchMap.put("searchType", searchType);
			}
			if(searchName != null && searchName.length()>0 ) {
				searchMap.put("searchName", searchName);
			}
			if(searchSubType != null && searchSubType.length()>0) {
				searchMap.put("searchSubType", searchSubType);
			}
			if(searchYear != null && searchYear.length()>0) {
				searchMap.put("searchYear",searchYear);
			}
			
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int boardCount = service.getVarietyCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, boardCount, 9);
		List<Variety> list = service.getVarietyList(pageInfo, searchMap);
		model.addAttribute("listSize",list.size());
		model.addAttribute("list", list);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("boardCount", boardCount);
		
		return "/agriculture/farmingVarietiesList";
	}
	
	@RequestMapping("/farmingVarietiesDetails")
	public String view(Model model, @RequestParam("no") String no) {
		Variety variety = service.findByNo(no);
		if(variety == null) {
			return "redirect:error";
		}
		
		model.addAttribute("variety", variety);
		return "agriculture/farmingVarietiesDetails";
	}
	
	
	
	
}
