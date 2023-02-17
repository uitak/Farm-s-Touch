package com.multi.bbs.mlrdcuration.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MlrdcurationList {
	
	private int mlNo;					// 게시판 번호
	private String dataNO;				// 콘텐츠 번호
	private String curationNm;			// 제목
	private String areaCodeNm;			// 지역
	private String ctgCodeNm;			// 분야
	
	private String kidofcomdtyCodeNm;	// 작목
	private String registDt;			// 등록일
	private String curationCn;			// 내용
	private String registerNm;			// 등록자
	private String rdcnt;				// 조회수
	
	
	
	// mlNo, dataNO, curationNm, areaCodeNm, ctgCodeNm, 
	// kidofcomdtyCodeNm, registDt, curationCn, registerNm, rdcnt
	
}
