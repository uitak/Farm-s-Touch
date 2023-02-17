package com.multi.bbs.blog.model.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
	
	private int bNo;
	private int mNo;
	private String writer;
	private String title;
	private String content;
	private String thumbnailImgUrl;
	private int readCount;
	private List<Comment> commentList;
	private Date createDate;
	private Date modifyDate;
	private int likeCount;
	private int isLike;
}
