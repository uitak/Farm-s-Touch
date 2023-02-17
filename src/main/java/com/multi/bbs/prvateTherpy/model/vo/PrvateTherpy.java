package com.multi.bbs.prvateTherpy.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrvateTherpy {
	
	private int prvNo;			// 넘버링
	private String cntntsNo;	// 콘텐츠 번호
	private String cntntsSj;	// 명칭
	private String bneNm;		// 학명
	private String hbdcNm;		// 생약명
	
	private String thmbImgUrl;		// 썸네일 이미지
	private String imgUrl;		// 이미지
	private String useeRegn;	// 이용부위
	private String stle;		// 형태
	private String prvateTherpy;// 민간요법
	
	private String imgUrl1;		// 이미지1
	private String imgUrl2;		// 이미지2
	private String imgUrl3;		// 이미지3
	private String imgUrl4;		// 이미지4
	private String imgUrl5;		// 이미지5
	private String imgUrl6;		// 이미지6
	
}


/* prvNo, cntntsNo, cntntsSj, bneNm, hbdcNm, 
 * thmbImgUrl, imgUrl, useeRegn, stle, prvateTherpy, 
 * imgUrl1, imgUrl2, imgUrl3, imgUrl4, imgUrl5, 
 * imgUrl6
 */