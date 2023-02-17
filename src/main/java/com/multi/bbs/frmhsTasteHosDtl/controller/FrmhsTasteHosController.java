package com.multi.bbs.frmhsTasteHosDtl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.frmhsTasteHosDtl.model.service.FrmhsTasteHosService;
import com.multi.bbs.frmhsTasteHosDtl.model.vo.FrmhsTasteHos;
import com.multi.bbs.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FrmhsTasteHosController{
	
//	@GetMapping("/life/farmHouseRestaurantList")
//	public String rmhsTasteHos(Model model) {
//		return "life/farmHouseRestaurantList";
//	}
	
//	@GetMapping("/life/farmHouseRestaurantDetails")
//	public String rmhsTasteHos(Model model) {
//		return "life/farmHouseRestaurantDetails";
//	}
	
	@Autowired
	private FrmhsTasteHosService frmhsTasteHosService;
	
	@GetMapping("/life/farmHouseRestaurantList")
	public String flist(Model model, @RequestParam Map<String, String> paramMap) {
		int page = 1;

		// 탐색할 맵을 선언
		Map<String, String> searchMap = new HashMap<String, String>();
		try {
			String searchSort = paramMap.get("searchSort");
			String searchValue = paramMap.get("searchValue");
			String searchCity = paramMap.get("searchCity");
			
			// 정렬 기능 > ssj인지, star인지, reviews인지를 받아온다
			if(searchSort != null) {
				searchMap.put(searchSort, searchSort);
			}
			// 제목 검색만 있음
			if(searchValue != null && searchValue.length() > 0) {
				searchMap.put("searchValue", searchValue);
			}
			// 지역별 검색 결과 보여줘야지
			if(searchCity != null) {
				searchMap.put("searchCity", searchCity);
			}
			
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int fboardCount = frmhsTasteHosService.getFrmhsTasteHosCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 10, fboardCount, 9);
		List<FrmhsTasteHos> flist = frmhsTasteHosService.getFrmhsTasteHosList(pageInfo, searchMap);
		
		model.addAttribute("flist", flist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("fboardCount", fboardCount);
		return "life/farmHouseRestaurantList";
	}
	
	
	// view 페이지
	@GetMapping("/life/farmHouseRestaurantDetails")
	public String fview(Model model, @RequestParam("cntntsNo") String cntntsNo) {
		FrmhsTasteHos frmhsTasteHos = frmhsTasteHosService.findByNo(cntntsNo);
		
		// 여기서부터는 다른 맛집 알아보기에 필요한 코드
		List<FrmhsTasteHos> otherList = frmhsTasteHosService.getFrmhsTasteHosOtherList(cntntsNo);
		
		// 여기는 이미지 리스트 추출
		List<FrmhsTasteHos> imgList = frmhsTasteHosService.getFrmhsTasteHosImgList(cntntsNo);
		int imgListSize = imgList.size();
		
		
		model.addAttribute("frmhsTasteHos", frmhsTasteHos);
		model.addAttribute("otherList", otherList);
		model.addAttribute("imgList", imgList);
		model.addAttribute("imgListSize", imgListSize);
		
		return "life/farmHouseRestaurantDetails";
	}
	
	
}

