package com.multi.bbs.agriculture.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.bbs.agriculture.model.mapper.MachineVodMapper;
import com.multi.bbs.agriculture.model.vo.MachineVodAni;
import com.multi.bbs.agriculture.model.vo.MachineVodImg;
import com.multi.bbs.agriculture.model.vo.MachineVodMov;
import com.multi.bbs.common.util.PageInfo;

@Service
public class MachineVodService {
	
	@Autowired
	private MachineVodMapper mapper;
	
	public int getAniCount(Map<String, String> param) {
		return mapper.selectMachineVodAniCount(param);
	}
	
	public List<MachineVodAni> getAniList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectMachineVodAniList(param);
	}
	
	public int getMovCount(Map<String, String> param) {
		return mapper.selectMachineVodMovCount(param);
	}
	
	public List<MachineVodMov> getMovList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectMachineVodMovList(param);
	}
	
	public int getImgCount(Map<String, String> param) {
		return mapper.selectMachineVodImgCount(param);
	}
	
	public List<MachineVodImg> getImgList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectMachineVodImgList(param);
	}
	
	public MachineVodAni findAniByNo(String cntntsNo) {
		MachineVodAni mva = mapper.selectAniByNo(cntntsNo);
		return mva;
	}
	
	public MachineVodMov findMovByNo(String cntntsNo) {
		MachineVodMov mvm = mapper.selectMovByNo(cntntsNo);
		return mvm;
	}
	
	public MachineVodImg findImgByNo(String cntntsNo) {
		MachineVodImg mvi = mapper.selectImgByNo(cntntsNo);
		return mvi;
	}
}
