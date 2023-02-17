package com.multi.bbs.weeklyfarminginfo.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.weeklyfarminginfo.model.vo.WeeklyFarmingInfo;

@Mapper
public interface WeeklyFarmingInfoMapper {
	
	List<WeeklyFarmingInfo> selectWeeklyFarimgInfoList(Map<String, String> map);
	int selectWeeklyFarmingInfoCount(Map<String, String> map);
//	Board selectBoardByNo(int no);

}
