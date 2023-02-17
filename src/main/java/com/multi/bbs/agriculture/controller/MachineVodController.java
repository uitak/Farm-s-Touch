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

import com.multi.bbs.agriculture.model.service.MachineVodService;
import com.multi.bbs.agriculture.model.vo.MachineVodAni;
import com.multi.bbs.agriculture.model.vo.MachineVodImg;
import com.multi.bbs.agriculture.model.vo.MachineVodMov;
import com.multi.bbs.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/agriculture")
@Controller
public class MachineVodController {
	
	@Autowired
	private MachineVodService service;
	
	@GetMapping("agriculturalMachineryVideoList")
	public String list(Model model, @RequestParam Map<String, String> paramMap ) {
		int page = 1;
		
		Map<String, String> searchMap = new HashMap<String, String>();
		String searchType= null;
		try {
			String searchName = paramMap.get("searchName");
			searchType = paramMap.get("searchType");
			if(searchName != null && searchName.length()>0 ) {
				searchMap.put("searchName", searchName);
			}
			
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		if(searchType != null && searchType.equals("Mov")) {
			int boardCount = service.getMovCount(searchMap);
			PageInfo pageInfo = new PageInfo(page, 5, boardCount, 6);
			List<MachineVodMov> list1 = service.getMovList(pageInfo, searchMap);
			
			model.addAttribute("listSize",list1.size());
			model.addAttribute("list1", list1);
			model.addAttribute("paramMap", paramMap);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("boardCount", boardCount);
		}else if(searchType != null && searchType.equals("Img")) {
			int boardCount = service.getImgCount(searchMap);
			PageInfo pageInfo = new PageInfo(page, 5, boardCount, 6);
			List<MachineVodImg> list2 = service.getImgList(pageInfo, searchMap);
			
			model.addAttribute("listSize",list2.size());
			model.addAttribute("list2", list2);
			model.addAttribute("paramMap", paramMap);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("boardCount", boardCount);
		}else {
			int boardCount = service.getAniCount(searchMap);
			PageInfo pageInfo = new PageInfo(page, 5, boardCount, 6);
			List<MachineVodAni> list3 = service.getAniList(pageInfo, searchMap);
			
			model.addAttribute("listSize",list3.size());
			model.addAttribute("list3", list3);
			model.addAttribute("paramMap", paramMap);
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("boardCount", boardCount);
		}
		
		
		
		
		return "/agriculture/agriculturalMachineryVideoList";
	}
	
	@RequestMapping("/agriculturalMachineryVideoDetails")
	public String viewAni(Model model, @RequestParam("no") String no) {
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();	
		searchMap.put("cntntsNo", no);
		
		// 관련영상 랜덤 추출 용도
		int boardCount = service.getAniCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 1, boardCount, 5);
		List<MachineVodAni> list = service.getAniList(pageInfo, searchMap);
		model.addAttribute("list", list);
		
		// 상세내용 가져오기
		MachineVodAni mva = service.findAniByNo(no);
		
		if(mva == null) {
			return "redirect:error";
		}
		
		model.addAttribute("mva",mva);
		
		return "/agriculture/agriculturalMachineryVideoDetails";
	}
	
	@RequestMapping("/agriculturalMachineryMovDetails")
	public String viewMov(Model model, @RequestParam("no") String no) {
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();	
		searchMap.put("cntntsNo", no);
		
		int boardCount = service.getAniCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 1, boardCount, 5);
		List<MachineVodMov> list = service.getMovList(pageInfo, searchMap);
		
		model.addAttribute("list", list);

		MachineVodMov mvm = service.findMovByNo(no);
		
		if(mvm == null) {
			return "redirect:error";
		}
		
		model.addAttribute("mvm",mvm);
		
		return "/agriculture/agriculturalMachineryMovDetails";
	}
	
	@RequestMapping("/agriculturalMachineryImgDetails")
	public String viewImg(Model model, @RequestParam("no") String no) {
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();	
		searchMap.put("cntntsNo", no);
		
		int boardCount = service.getImgCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 1, boardCount, 5);
		List<MachineVodImg> list = service.getImgList(pageInfo, searchMap);
		model.addAttribute("list", list);

		MachineVodImg mvi = service.findImgByNo(no);
		
		if(mvi == null) {
			return "redirect:error";
		}
		
		model.addAttribute("mvi",mvi);
		
		return "/agriculture/agriculturalMachineryImgDetails";
	}
}
