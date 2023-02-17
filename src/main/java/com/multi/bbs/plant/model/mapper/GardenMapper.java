package com.multi.bbs.plant.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.plant.model.vo.DryGarden;
import com.multi.bbs.plant.model.vo.Garden;

@Mapper
public interface GardenMapper {
	List<Garden> selectGardenList(Map<String, String> map);
	int selectGardenCount(Map<String, String> map);
	Garden selectGardenByNo(String cntntsNo);
	List<DryGarden> selectDryGardenList(Map<String, String> map);
	int selectDryGardenCount(Map<String, String> map);
	DryGarden selectDryGardenByNo(String cntntsNo);
}
