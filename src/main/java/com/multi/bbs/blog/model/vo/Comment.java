package com.multi.bbs.blog.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	private int cNo;
	private int bNo;
	private int mNo;
	private String profileImg;
	private String writer;
	private String content;	
	private Date createDate;
	private Date modifyDate;
}
