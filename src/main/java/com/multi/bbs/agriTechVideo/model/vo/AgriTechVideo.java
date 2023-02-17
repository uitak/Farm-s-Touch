package com.multi.bbs.agriTechVideo.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgriTechVideo {
	private int vidNo;					//넘버링
	private String videoTitle;			// 제목
	private String videoOriginInstt;	// 출저-저작권
	private String videoLink;			// 비디오 링크
	private String videoImg;			// 비디오 이미지

}
