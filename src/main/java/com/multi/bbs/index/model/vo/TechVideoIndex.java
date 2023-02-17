package com.multi.bbs.index.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechVideoIndex {
	
	// vidNo, videoTitle, videoOriginInstt, videoLink, videoImg
	
	private int vidNo;
	private String videoTitle;
	private String videoOriginInstt;
	private String videoLink;
	private String videoImg;

}
