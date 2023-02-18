package com.multi.bbs.helpcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HelpCenterController {
	
	@GetMapping("/life/helpcenter")
	public String helpcenter(Model model) {
		return "life/helpcenter";
	} 

}
