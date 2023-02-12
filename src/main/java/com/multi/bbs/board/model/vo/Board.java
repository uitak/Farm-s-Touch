package com.multi.bbs.board.model.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int bNo;
	private int mNo;
	private String writer;
	private String title;
	private String content;
	private String whetherSales;
	private String originalFileName;
	private String renamedFileName;
	private int readCount;
	private String dtype;
	private List<Reply> replyList;
	private Date createDate;
	private Date modifyDate;
}