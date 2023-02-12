package com.multi.bbs.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.multi.bbs.member.model.service.MemberService;
import com.multi.bbs.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SessionAttributes("loginMember") // loginMember를 Model로 취급할때 세션으로 처리.
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	final static private String savePath = "c:/multicampus/";
	
	@GetMapping("/login")
	String login() {
		return "/member/login";
	}
	
	@PostMapping("/login")
	String login(Model model, String userId, String userPwd) {
		log.info("id : " + userId + ", pwd : " + userPwd);
		Member loginMember = service.login(userId, userPwd);
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			return "redirect:/";
		}else {
			model.addAttribute("msg", "아이디 패스워드가 잘못되었습니다.");
			model.addAttribute("location", "/");
			return "common/msg";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(SessionStatus status) {
		log.info("status : " + status.isComplete());
		status.setComplete();
		log.info("status : " + status.isComplete());
		return "redirect:/";
	}
	
	@GetMapping("/member/enroll")
	public String enrollPage(Model model, Member member) {
		log.info("회원가입 페이지 요청");
		model.addAttribute("member", member);
		return "member/enroll";
	}
	
	@PostMapping("/member/enroll")
	public String enroll(Model model, Member member) {
		System.out.println("회원가입, member : " + member.toString());

		int result = service.save(member);
		
		if(result > 0) {
			model.addAttribute("msg", "회원가입에 성공하였습니다.");
			model.addAttribute("location", "/");
		}else {
			model.addAttribute("msg", "회원가입에 실패하였습니다. 입력정보를 확인하세요.");
			model.addAttribute ("location", "/");
		}
		return "common/msg";
	}
	
	@GetMapping("/member/idCheck")
	public ResponseEntity<Map<String, Object>> idCheck(String id){
		log.info("아이디 중복 확인 : " + id);
		
		boolean result = service.validate(id);
		Map<String,	Object> map = new HashMap<String, Object>();
		map.put("validate", result);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/member/update")
	public String update(Model model, 
			@ModelAttribute Member updateMember,
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@RequestParam("upimage") MultipartFile upImage
			) {
		log.info("update 요청, updateMember : " + updateMember);
		if(loginMember == null || loginMember.getId().equals(updateMember.getId()) == false) {
			model.addAttribute("msg","잘못된 접근입니다.");
			model.addAttribute("location","/");
			return "/common/msg";
		}
		
		updateMember.setMNo(loginMember.getMNo());
		
		if(upImage != null && upImage.isEmpty() == false) {
			String profileImage = service.saveFile(upImage, savePath + loginMember.getId() + "/");
			updateMember.setImageUrl(profileImage);
		} else {
			updateMember.setImageUrl(loginMember.getImageUrl());
		}
				
		if (updateMember.getName() == null || updateMember.getName().isEmpty()) {
			updateMember.setName(loginMember.getName());
		}
		if (updateMember.getGender() == null || updateMember.getGender().isEmpty()) {
			updateMember.setGender(loginMember.getGender());
		}
		if (updateMember.getBirth() == null || updateMember.getBirth().isEmpty()) {
			updateMember.setBirth(loginMember.getBirth());
		}
		if (updateMember.getPhone() == null || updateMember.getPhone().isEmpty()) {
			updateMember.setPhone(loginMember.getPhone());
		}
		if (updateMember.getEmail() == null || updateMember.getEmail().isEmpty()) {
			updateMember.setEmail(loginMember.getEmail());
		}
		if (updateMember.getAddress() == null || updateMember.getAddress().isEmpty()) {
			updateMember.setAddress(loginMember.getAddress());
		}

		int result = service.save(updateMember);		
		if(result > 0) {
			// 수정된 db정보로 세션을 새롭게 세팅.
			model.addAttribute("loginMember", service.findById(loginMember.getId()));
			model.addAttribute("msg", "회원정보를 수정하였습니다.");
			model.addAttribute("location", "/member/view");
		}else {
			model.addAttribute("msg", "회원정보 수정에 실패하였습니다.");
			model.addAttribute("location", "/member/view");
		}
		return "/common/msg";
	}
	
	@GetMapping("/member/view")
	public String memberView(@SessionAttribute("loginMember") Member loginMember,
							 @ModelAttribute Member updateMember) {
		log.info("회원 정보 페이지 요청");
		return "member/myInfo";
	}
	
	@GetMapping("/member/updatePwd")
	public String updatePwdPage(@SessionAttribute(name = "loginMember", required = true) Member loginMember) {
		return "member/myPwd";
	}
	
	@PostMapping("/member/updatePwd")
	public String updatePwd(Model model,
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			String currentPwd, String newPwd
			) {
		
		int result = service.updatePwd(loginMember, currentPwd, newPwd);		
		if(result > 0) {
			model.addAttribute("msg", "비밀번호 수정에 성공하였습니다.");
		} 
		else if (result == -4) {
			model.addAttribute("msg", "현재 비밀번호를 확인해주세요.");
		}
		else {
			model.addAttribute("msg", "비밀번호 변경에 실패했습니다.");
		}
		model.addAttribute("location", "/logout");
		return "common/msg";
	}
	
	@RequestMapping("/member/delete")
	public String delete(Model model,
			@SessionAttribute(name = "loginMember", required = true) Member loginMember) {
		int result = service.delete(loginMember.getMNo());
		if(result > 0) {
			model.addAttribute("msg", "회원탈퇴에 성공하였습니다.");
			model.addAttribute("location","/logout");
		}else {
			model.addAttribute("msg", "회원탈퇴에 실패하였습니다.");
			model.addAttribute("location","/member/view");
		}
		return  "/common/msg";
	}
	
	@GetMapping("/member/file/{fileName}")
	@ResponseBody
	public Resource downloadImage(@PathVariable("fileName") String fileName, 
			@SessionAttribute(name = "loginMember", required = true) Member loginMember, 
			Model model) throws IOException {
		
		return new UrlResource("file:" + savePath + loginMember.getId() + "/" + fileName);
	}
}

