package com.multi.bbs.weeklyfarminginfo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyFarmingInfo {
	private int weekNo;			// index번호
	private String cntntsNo;	// 콘텐츠 번호
	private String fileSeCode;	// 파일 암호화 코드
	private String subject;		// 제목
	private String hitCt;		// 조회수
	private String writerNm;	// 작성자
	private String regDt;		// 등록일
	private String downUrl;		// 첨부파일 다운로드 링크
	private String fileName;	// 첨부파일 이름
	private String subUrl;		// 뷰어 링크
	
}

// weekNo, cntntsNo, fileSeCode, subject, writerNm, hitCt, regDt, downUrl, fileName, subUrl
