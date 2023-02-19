package com.multi.bbs.prvateTherpy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.prvateTherpy.model.service.PrvateTherpyService;
import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PrvateTherpyController {
	
//	@GetMapping("/life/herbList")
//	String prvateHerb(Model model, String userId, String userPwd) {
//		return "life/herbList";
//	}
	
//	@GetMapping("/life/herbDetails")
//	String prvateTherpy(Model model, String userId, String userPwd) {
//		return "/life/herbDetails";
//	}
	
	@Autowired
	private PrvateTherpyService prvateTherpyService;
	
	@GetMapping("/life/herbList")
	public String hlist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue);
			}else {
				paramMap.put("searchType", "cntntsSj");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int hboardCount = prvateTherpyService.getPrvateTherpyCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, hboardCount, 9);
		List<PrvateTherpy> hlist = prvateTherpyService.getPrvateTherpyList(pageInfo, searchMap);
		
		
		
		model.addAttribute("hlist", hlist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("hboardCount", hboardCount);
		return "life/herbList";
	}
	
	
	// view 페이지
	@GetMapping("/life/herbDetails")
	public String view(Model model, @RequestParam("cntntsNo") String cntntsNo) {
		PrvateTherpy prvateTherpy = prvateTherpyService.findByNo(cntntsNo);
		
		// 여기서부터는 다른 약초 알아보기에 필요한 코드
		List<PrvateTherpy> otherList = prvateTherpyService.getPrvateTherpyOtherList(cntntsNo);
		
		model.addAttribute("prvateTherpy", prvateTherpy);
		model.addAttribute("otherList", otherList);
		
		return "life/herbDetails";
	}
	

}
