package com.multi.bbs.mlrdcuration.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.mlrdcuration.model.service.MlrdcurationService;
import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationAnwrList;
import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MlrdcurationController {
	
//	@GetMapping("/agriculture/agriculturalPlusMinusList")
//	public String mlrdcuration(Model model) {
//		return "agriculture/agriculturalPlusMinusList";
//	} 
	
//	@GetMapping("/agriculture/agriculturalPlusMinusDetails")
//	String prvateTherpy(Model model, String userId, String userPwd) {
//		return "/agriculture/agriculturalPlusMinusDetails";
//	}
	
	@Autowired
	private MlrdcurationService mlrdcurationService;
	
	@GetMapping("/agriculture/agriculturalPlusMinusList") 
	public String mlist(Model model, @RequestParam Map<String, String> paramMap, String[] areaCodeNm, String[] ctgCodeNm) {
		// 내가 원하는 url
		//http://localhost/agriculture/agriculturalPlusMinusList?areaCodeNm=popolarType10&ctgCodeNm=lauoverType1&searchType=curationNm&searchValue=갈색
		
		int page = 1;
		Map<String, String> searchMap = new HashMap<String, String>();	// 탐색할 맵을 선언
		try {	// 여기서 input은 타임리프에 있는 <input type=checkbox>를 뜻한다.
			// 체크박스는 중복이 체킹이 가능하므로 매개변수로 배열 형태로 중복된 input을 받아온다
			if(areaCodeNm != null) {	// 체크박스에 체크가 되어 있다면
				log.info("arrayCheck-areaCodeNm : " + Arrays.toString(areaCodeNm));	// 받아온 input 확인
				for(int i=0 ; i<areaCodeNm.length ; i++) {
					if(i == 0) {	// 체크된게 없다면
						searchMap.put("areaCodeNm", "areaCodeNm");	// areaCodeNm으로 초기화
					}
					searchMap.put(areaCodeNm[i], areaCodeNm[i]); // 체크된 것이 있다면 input 값을 Map에 넣어 mapper에서 select 해옴
				}
			}
			
			if(ctgCodeNm != null) {	// 위의 areaCodeNm과 같은 원리
				log.info("arrayCheck-ctgCodeNm : " + Arrays.toString(ctgCodeNm));
				for(int i=0 ; i<ctgCodeNm.length ; i++) {
					if(i == 0) {
						searchMap.put("ctgCodeNm", "ctgCodeNm");
					}
					searchMap.put(ctgCodeNm[i], ctgCodeNm[i]);
				}
			}
			
			String searchValue = paramMap.get("searchValue");
			if(searchValue != null && searchValue.length() > 0) {
				String searchType = paramMap.get("searchType");
				searchMap.put(searchType, searchValue);
			}else {
				paramMap.put("searchType", "curationNm");
			}
			page = Integer.parseInt(paramMap.get("page"));
		} catch (Exception e) {}
		
		int mboardCount = mlrdcurationService.getMlrdcurationCount(searchMap);
		PageInfo pageInfo = new PageInfo(page, 5, mboardCount, 15);
		List<MlrdcurationList> mlist = mlrdcurationService.getMlrdcurationList(pageInfo, searchMap);
		
		model.addAttribute("areaCodeNm", Arrays.toString(areaCodeNm));	// input값 타임리프에 넘겨줘야지
		model.addAttribute("ctgCodeNm", Arrays.toString(ctgCodeNm));	// input값 타임리프에 넘겨줘야지
		model.addAttribute("mlist", mlist);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("mboardCount", mboardCount);
		
		return "agriculture/agriculturalPlusMinusList";
	}
	
	
	// view 페이지에 게시글 내용과 함께 댓글도 뿌려주기
	@GetMapping("/agriculture/agriculturalPlusMinusDetails")
	public String view(Model model, @RequestParam("dataNO") String dataNO, @RequestParam Map<String, String> paramMap) {
		// 게시글 상세
		MlrdcurationList mlrdcurationList = mlrdcurationService.findByNo(dataNO);
		
		// 댓글 상세
		int page = 1;
		Map<String, String> map = new HashMap<String, String>();
		map.put("dataNO", paramMap.get("dataNO"));
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PageInfo pageInfo = new PageInfo(1, 5, 5, 5);	// 5개만 불러오기
		List<MlrdcurationAnwrList> mlrdcurationAnwrList = mlrdcurationService.selectMlrdcurationAnwrList(pageInfo, map);
		
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("mlrdcurationList", mlrdcurationList);
		model.addAttribute("mlrdcurationAnwrList", mlrdcurationAnwrList);
		
		return "agriculture/agriculturalPlusMinusDetails";
	}

}
