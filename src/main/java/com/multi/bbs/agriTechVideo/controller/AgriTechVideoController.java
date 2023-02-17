package com.multi.bbs.agriTechVideo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.agriTechVideo.model.service.AgriTechVideoService;
import com.multi.bbs.agriTechVideo.model.vo.AgriTechVideo;
import com.multi.bbs.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j 선언을 대신 선언 해주는 lombok 어노테이션
@Controller
public class AgriTechVideoController {

	@Autowired
	private AgriTechVideoService vservice;

//	@GetMapping("agriculture/agriTechVideo")
//	String agriTechVideo(Model model) {
//	return "agriculture/agriTechVideo";
//	}

	@GetMapping("/agriculture/agriTechVideo")
	public String vlist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchValue = paramMap.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue);
			}else {
				paramMap.put("searchType", "videoOriginInstt");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}

		int vCount = vservice.getVideoCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, vCount, 15);
		List<AgriTechVideo> vlist = vservice.getVideoList(pageInfo, searchMap);

		model.addAttribute("vlist", vlist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("vCount", vCount);
		return "agriculture/agriTechVideo";
	}

}
