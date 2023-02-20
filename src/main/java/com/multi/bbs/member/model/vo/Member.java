package com.multi.bbs.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int mNo;
	private String id;
	private String password;
	private String role;
	private String name;
	private String gender;
	private String birth;
	private String phone;
	private String email;
	private String address;
	private String imageUrl;
	private String kakaoToken;
}
