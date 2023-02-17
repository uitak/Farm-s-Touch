package com.multi.bbs.frmhsTasteHosDtl.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrmhsTasteHosReview {
	
	private int reviewNo;				// 리뷰 번호
	private String cntntsNo;			// 농가맛집 번호
	private int mNo;					// 멤버 번호
	private String content;				// 리뷰 내용
	private double star;				// 리뷰 별점
	
	private String originalFilename;	// 파일 저장 이름
	private String renamedFilename;		// 파일 수정 이름
	private Date createDate;			// 리뷰 생성 날짜
	
	// reviewNo, cntntsNo, mNo, content, star, 
	// originalFilename, renamedFilename, createDate

	// 나중에 Member.name 도 가져오자
}
