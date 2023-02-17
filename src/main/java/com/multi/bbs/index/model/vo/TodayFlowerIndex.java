package com.multi.bbs.index.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayFlowerIndex {
	
	private String dataNo;		// 오늘의 꽃 INDEX
	private String fMonth;		// 월
	private String fDay;		// 일
	private String flowNm;		// 꽃이름(국문)
	private String fSctNm;		// 학명
	
	private String fEngNm;		// 꽃이름(영문)
	private String flowLang;	// 꽃말
	private String fContent;	// 내용
	private String fUse;		// 이용
	private String fGrow;		// 기르기
	
	private String fType;		// 자생지
	private String fileName1;	// 이미지 원본파일명1
	private String fileName2;	// 이미지 원본파일명2
	private String fileName3;	// 이미지 원본파일명3
	
	private String imgUrl1;		// 이미지 URL1
	private String imgUrl2;		// 이미지 URL2
	private String imgUrl3;		// 이미지 URL3
	private String publishOrg;	// 출처

}

