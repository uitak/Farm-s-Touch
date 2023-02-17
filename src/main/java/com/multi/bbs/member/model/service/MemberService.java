package com.multi.bbs.member.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.multi.bbs.member.model.mapper.MemberMapper;
import com.multi.bbs.member.model.vo.Member;

@Service
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	
	private BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	public Member login(String id, String pw) {
		Member member = mapper.selectMember(id);
		if(member == null) {
			return null;
		}		
		/*
		if(id.equals("admin")) { // admin 테스트를 위한 코드
			return member;
		}
		*/
		if(member != null && pwEncoder.matches(pw, member.getPassword()) == true) {
			return member;
		}else {
			return null;
		}
	}
	
	// @Transactional : DB 트랜잭션 관리를 위한 AOP 어노테이션. 만일 오류가 발생하면 롤백. 아니면 커밋
	// (rollbackFor = Exception.class) : 사용하지 않은 경우 트랜잭션 코드가 정상적으로 작동하지 않을수 있다.
	@Transactional(rollbackFor = Exception.class)
	public int save(Member member) {
		int result = 0;
		if(member.getMNo() == 0) {
			String encodePW = pwEncoder.encode(member.getPassword());
			member.setPassword(encodePW);
			result = mapper.insertMember(member);
		}else {
			result = mapper.updateMember(member);
		}
		return result;
	}
	
	public boolean validate(String userId) {
		return this.findById(userId) != null;
	}
	
	public Member findById(String id) {
		return mapper.selectMember(id);
	}

	
	@Transactional(rollbackFor = Exception.class)
	public int delete(int no) {
		return mapper.deleteMember(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updatePwd(Member loginMember, String currentPwd, String newPwd) {
		if (!pwEncoder.matches(currentPwd, loginMember.getPassword())) {
			return -4;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mNo", "" + loginMember.getMNo());
		map.put("password", pwEncoder.encode(newPwd));
		return mapper.updatePwd(map);
	}
	
	public String saveFile(MultipartFile upFile, String savePath) {
		
		File folder = new File(savePath);
		if(folder.exists() == false) {
			folder.mkdirs();
		}
		
		String originalFileName = upFile.getOriginalFilename();
		String reNameFileName = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = savePath + "/" + reNameFileName;
		
		try {
			upFile.transferTo(new File(reNamePath));
		} catch (Exception e) {
			return null;
		}
		return reNameFileName;
	}
}
