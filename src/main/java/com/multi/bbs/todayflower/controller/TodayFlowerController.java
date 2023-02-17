package com.multi.bbs.todayflower.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.todayflower.model.service.TodayFlowerService;
import com.multi.bbs.todayflower.model.vo.TodayFlower;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TodayFlowerController {
	
	@Autowired
	private TodayFlowerService todayFlowerService;
	
	@GetMapping("/life/todayFlowerList")
	public String flist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			String searchMonth = paramMap.get("searchMonth");
			
			// 제목 검색만 있음
			if(searchValue != null && searchValue.length() > 0) {
				searchMap.put("searchValue", searchValue);
			}
			// 월별 검색 결과 보여줘야지
			if(searchMonth != null) {
				searchMap.put("searchMonth", searchMonth);
			}
			
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		
		int fboardCount = todayFlowerService.getTodayFlowerCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 5, fboardCount, 6);
		List<TodayFlower> flist = todayFlowerService.getTodayFlowerList(pageInfo, searchMap);
		
		model.addAttribute("flist", flist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("fboardCount", fboardCount);
		return "life/todayFlowerList";
	}
	
	
	// view 페이지
	@GetMapping("/life/todayFlowerDetail")
	public String fview(Model model, @RequestParam("dataNo") String dataNo) {
		TodayFlower todayFlower = todayFlowerService.findByNo(dataNo);
		
		// 여기서부터는 다른 꽃 알아보기에 필요한 코드
		List<TodayFlower> otherList = todayFlowerService.getTodayFlowerOtherList(dataNo);
		
		// 여기는 이미지 리스트 추출
		List<TodayFlower> imgList = todayFlowerService.getTodayFlowerImgList(dataNo);
		
		
		model.addAttribute("todayFlower", todayFlower);
		model.addAttribute("otherList", otherList);
		model.addAttribute("imgList", imgList);
		
		return "life/todayFlowerDetail";
	}

}
