package com.multi.bbs;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.frmhsTasteHosDtl.controller.FrmhsTasteHosController;
import com.multi.bbs.index.model.service.IndexService;
import com.multi.bbs.index.model.vo.FrmhsTasteHosIndex;
import com.multi.bbs.index.model.vo.PrvateTherpyIndex;
import com.multi.bbs.index.model.vo.TechVideoIndex;
import com.multi.bbs.index.model.vo.TodayFlowerIndex;
import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
//	@Autowired
//	private BoardService service;
	
//	@Autowired
//	private MemberService memberService;
	
	@Autowired
	private IndexService indexService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
//		Member loginMember = memberService.login("admin", "1212");
//		session.setAttribute("loginMember", loginMember);
//		test();
		
		// 오늘의 꽃
		List<TodayFlowerIndex> fList = indexService.getTodayFlowerIndexList();
		
		// 농가맛집
		List<FrmhsTasteHosIndex> tList = indexService.getFrmhsTasteHosIndexList();

		// 농업기술 동영상
		List<TechVideoIndex> vList = indexService.getTechVideoIndexList();
		
		//확인용 추후에 필히 삭제
//		for(TodayFlowerIndex havetodelete : fList) {
//			System.out.println(havetodelete);
//		}
		
		String reContent = "";
		String reGrow = "";
		if(fList.get(0).getFContent().length() >= 90) {
			reContent = fList.get(0).getFContent().substring(0,90) + "...";	// 문자열 너무 길어서 컷팅
		} else {
			reContent = fList.get(0).getFContent();
		}
		if(fList.get(0).getFGrow().length() >= 90) {
			reGrow = fList.get(0).getFGrow().substring(0,90) + "...";	// 문자열 너무 길어서 컷팅
		} else {
			reGrow = fList.get(0).getFGrow();
		}
	
		model.addAttribute("fList", fList);	// 오늘의 꽃 전달
		model.addAttribute("reContent", reContent);	// 문자열 컷팅 한 내용
		model.addAttribute("reGrow", reGrow);		// 문자열 컷팅 한 내용
		
		model.addAttribute("tList", tList);	// 농가맛집 전달
		model.addAttribute("vList", vList);	// 농업기술동영상 전달
		return "/index";
	}

	
//	public void test() {
//		Map<String, String> map = new HashMap<>();
//		PageInfo info = new PageInfo(1, 10, service.getBoardCount(map), 10);
//		logger.info("board List : " + service.getBoardList(info, map));
//	}
	
}
