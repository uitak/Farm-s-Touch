package com.multi.bbs.notice.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequestMapping("/life") // 요청 url의 상위 url을 모두 처리할때 사용
@Controller
public class NoticeController {
	
	@GetMapping("/board/notice")
	public String list(Model model, @RequestParam Map<String, String> paramMap) {

		return "/board/notice";
	}
	

}

