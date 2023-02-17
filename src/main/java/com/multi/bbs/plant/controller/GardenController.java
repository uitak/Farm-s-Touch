package com.multi.bbs.plant.controller;

import java.util.Arrays;
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
import com.multi.bbs.plant.model.service.GardenService;
import com.multi.bbs.plant.model.vo.DryGarden;
import com.multi.bbs.plant.model.vo.Garden;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/plant")
@Controller
public class GardenController {
	
	@Autowired
	private GardenService service;
	
	@GetMapping("/gardenList")
	public String gardenList(Model model,@RequestParam Map<String, String> param, 
			String[] smell, String[] growth, String[] grwhTp, 
			String[] manageLevel) {
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();
		
		try {
			if(smell != null) {
				for(int i=0; i< smell.length; i++) {
					if(i==0) {
						searchMap.put("smell", "smell");
					}
					searchMap.put(smell[i], smell[i]);
					
				}
			}
			if(growth != null) {
				for(int i=0; i< growth.length; i++) {
					if(i==0) {
						searchMap.put("growth", "growth");
					}
					searchMap.put(growth[i], growth[i]);
				}
			}
			if(grwhTp != null) {
				for(int i=0; i< grwhTp.length; i++) {
					if(i==0) {
						searchMap.put("grwhTp", "grwhTp");
					}
					searchMap.put(grwhTp[i], grwhTp[i]);
				}
			}
			if(manageLevel != null) {
				for(int i=0; i< manageLevel.length; i++) {
					if(i==0) {
						searchMap.put("manageLevel", "manageLevel");
					}
					searchMap.put(manageLevel[i], manageLevel[i]);
				}
			}
			String searchName = param.get("searchName");
			String searchNameType = param.get("searchNameType");
			String waterCycle = param.get("waterCycle");
			
			if(searchName != null) {
				if(searchNameType != null) {
					searchMap.put(searchNameType, searchName);
				}
			}
			
			if(waterCycle != null) {
				searchMap.put("waterCycle", waterCycle);
			}
			

			page = Integer.parseInt(param.get("page"));
		}catch (Exception e) {}
		int gardenCount = service.getGardenCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 5, gardenCount, 5);
		List<Garden> list = service.getGardenList(pageInfo, searchMap);
		
		model.addAttribute("smell", Arrays.toString(smell));
		model.addAttribute("growth", Arrays.toString(growth));
		model.addAttribute("grwhTp", Arrays.toString(grwhTp));
		model.addAttribute("manageLevel", Arrays.toString(manageLevel));
		model.addAttribute("gardenCount", gardenCount);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		model.addAttribute("list", list);
		model.addAttribute("listSize",list.size());
		
		return "/plant/gardenList";
	}
	
	@RequestMapping("/gardenDtl")
	public String gardenView(Model model, @RequestParam("no") String no) {
		Garden garden = service.findGardenByNo(no);
		if(garden == null) {
			return "redirect:error"; 
		}
		model.addAttribute("garden", garden);
		return "/plant/gardenDtl";
		
	}
	
	@GetMapping("/drygardenList")
	public String dryGardenList(Model model,@RequestParam Map<String, String> param, 
			String[] shape, String[] growth, String[] demand, 
			String[] level, String[] period) {
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();
		
		try {
			if(shape != null) {
				for(int i=0; i< shape.length; i++) {
					if(i==0) {
						searchMap.put("shape", "shape");
					}
					searchMap.put(shape[i], shape[i]);
					
				}
			}
			if(growth != null) {
				for(int i=0; i< growth.length; i++) {
					if(i==0) {
						searchMap.put("growth", "growth");
					}
					searchMap.put(growth[i], growth[i]);
				}
			}
			if(demand != null) {
				for(int i=0; i< demand.length; i++) {
					if(i==0) {
						searchMap.put("demand", "demand");
					}
					searchMap.put(demand[i], demand[i]);
				}
			}
			if(level != null) {
				for(int i=0; i< level.length; i++) {
					if(i==0) {
						searchMap.put("level", "level");
					}
					searchMap.put(level[i], level[i]);
				}
			}
			if(period != null) {
				for(int i=0; i< period.length; i++) {
					if(i==0) {
						searchMap.put("period", "period");
					}
					searchMap.put(period[i], period[i]);
				}
			}
			String searchName = param.get("searchName");
			String searchNameType = param.get("searchNameType");
			String waterCycle = param.get("waterCycle");
			
			if(searchName != null) {
				if(searchNameType != null) {
					searchMap.put(searchNameType, searchName);
				}
			}
			
			if(waterCycle != null) {
				searchMap.put("waterCycle", waterCycle);
			}
			

			page = Integer.parseInt(param.get("page"));
		}catch (Exception e) {}
		int drygardenCount = service.getDryGardenCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 5, drygardenCount, 5);
		List<DryGarden> list = service.getDryGardenList(pageInfo, searchMap);
		
		model.addAttribute("shape", Arrays.toString(shape));
		model.addAttribute("growth", Arrays.toString(growth));
		model.addAttribute("demand", Arrays.toString(demand));
		model.addAttribute("level", Arrays.toString(level));
		model.addAttribute("period", Arrays.toString(period));
		model.addAttribute("gardenCount", drygardenCount);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("param", param);
		model.addAttribute("list", list);
		model.addAttribute("listSize",list.size());
		
		return "/plant/drygardenList";
	}
	
	@RequestMapping("/drygardenDtl")
	public String dryGardenView(Model model, @RequestParam("no") String no) {
		DryGarden drygarden = service.findDryGardenByNo(no);
		if(drygarden == null) {
			return "redirect:error"; 
		}
		model.addAttribute("drygarden", drygarden);
		return "/plant/drygardenDtl";
		
	}
}
