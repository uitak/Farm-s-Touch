package com.multi.bbs.plant.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Garden {
	private String cntntsNo;
	private String cntntsSj;
	private String plntzrNm;
	private String distbNm;
	private String fmlCodeNm;
	private String orgplceInfo;
	private String adviseInfo;
	private String growthHgInfo;
	private String growthAraInfo;
	private String lefStleInfo;
	private String smellCodeNm;
	private String toxctyInfo;
	private String prpgtEraInfo;
	private String etcEraInfo;
	private String managelevelCodeNm;
	private String grwtveCodeNm;
	private String grwhTpCodeNm;
	private String winterLwetTpCodeNm;
	private String hdCodeNm;
	private String frtlzrInfo;
	private String soilInfo;
	private String watercycleSprngCodeNm;
	private String watercycleSummerCodeNm;
	private String watercycleAutumnCodeNm;
	private String watercycleWinterCodeNm;
	private String dlthtsManageInfo;
	private String speclmanageInfo;
	private String managedemanddoCodeNm;
	private String clCodeNm;
	private String grwhstleCodeNm;
	private String indoorpsncpacompositionCodeNm;
	private String eclgyCodeNm;
	private String lefmrkCodeNm;
	private String lefcolrCodeNm;
	private String ignSeasonCodeNm;
	private String flclrCodeNm;
	private String fmldeSeasonCodeNm;
	private String fmldecolrCodeNm;
	private String prpgtmthCodeNm;
	private String lighttdemanddoCodeNm;
	private String postngplaceCodeNm;
	private String dlthtsCodeNm;
	private List<GardenImg> imgList;
}
