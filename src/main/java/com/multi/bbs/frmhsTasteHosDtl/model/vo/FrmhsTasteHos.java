package com.multi.bbs.frmhsTasteHosDtl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrmhsTasteHos {
	
	private String cntntsNo;	// 컨텐츠 번호
	private String cntntsSj;	// 명창
	private String slogan;		// 슬로건
	private String adstrdNm;	// 지역
	private String locplc;		// 주소
	
	private String telno;		// 전화번호
	private String operMth;		// 운영방법
	private String restde;		// 쉬는 날
	private String bsnTime;		// 영업시간
	private String seatStle;	// 좌석형태
	
	private String url;			// 홈페이지 주소
	private String smm;			// 개요
	private String trtmntMenu;	// 취급 메뉴
	private String exprnProgrm;	// 체험프로그램
	private String trrsrt;		// 주변 관광/볼거리
	
	private String imgUrl1;		// 이미지1
	private String imgUrl2;		// 이미지2
	private String imgUrl3;		// 이미지3
	private String imgUrl4;		// 이미지4
	private String imgUrl5;		// 이미지5
	
	private String subArea;		// 지역 카테고리 - 추가
	private int mNo;			// 멤버 외래키
	
	
	// cntntsNo, cntntsSj, slogan, adstrdNm, locplc, 
	// telno, operMth, restde, bsnTime, seatStle, 
	// url, smm, trtmntMenu, exprnProgrm, trrsrt, 
	// imgUrl1, imgUrl2, imgUrl3, imgUrl4, imgUrl5
	
	// subArea
	

}
