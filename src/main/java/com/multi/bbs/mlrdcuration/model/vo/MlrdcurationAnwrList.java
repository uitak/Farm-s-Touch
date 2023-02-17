package com.multi.bbs.mlrdcuration.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MlrdcurationAnwrList {
	
	private int anNo;			// 게시판 번호
	private String dataNo;		// 상세 콘텐츠 번호 빨아온거
	private String anwrNo;		// 콘텐츠 내의 댓글 순서 번호
	private String anwrCn;		// 내용
	private String wrterNm;		// 작성자
	
	private String writngDt;	// 작성일
	private String chdYn;		// 답글 구분 여부
	private String lvl;			// 댓글 레벨
	
	// anNo, dataNo, anwrNo, anwrCn, wrterNm, writngDt, chdYn, lvl

}
