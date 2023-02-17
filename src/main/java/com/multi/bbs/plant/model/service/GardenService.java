package com.multi.bbs.plant.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.plant.model.mapper.GardenMapper;
import com.multi.bbs.plant.model.vo.DryGarden;
import com.multi.bbs.plant.model.vo.Garden;

@Service
public class GardenService {
	
	@Autowired
	private GardenMapper mapper;
	
	public int getGardenCount(Map<String, String> param) {
		return mapper.selectGardenCount(param);
	}
	
	public List<Garden> getGardenList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectGardenList(param);
	}
	
	public Garden findGardenByNo(String cntntsNo) {
		Garden garden = mapper.selectGardenByNo(cntntsNo);
	
		return garden;
	}
	
	public int getDryGardenCount(Map<String, String> param) {
		return mapper.selectDryGardenCount(param);
	}
	
	public List<DryGarden> getDryGardenList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectDryGardenList(param);
	}
	
	public DryGarden findDryGardenByNo(String cntnstNo) {
		DryGarden dryGarden = mapper.selectDryGardenByNo(cntnstNo);
		
		return dryGarden;
	}
}
