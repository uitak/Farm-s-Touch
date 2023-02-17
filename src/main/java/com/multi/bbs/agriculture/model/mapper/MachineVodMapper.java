package com.multi.bbs.agriculture.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.agriculture.model.vo.MachineVodAni;
import com.multi.bbs.agriculture.model.vo.MachineVodImg;
import com.multi.bbs.agriculture.model.vo.MachineVodMov;

@Mapper
public interface MachineVodMapper {
	List<MachineVodAni> selectMachineVodAniList(Map<String, String> map);
	List<MachineVodMov> selectMachineVodMovList(Map<String, String> map);
	List<MachineVodImg> selectMachineVodImgList(Map<String, String> map);
	int selectMachineVodAniCount(Map<String, String> map);
	int selectMachineVodMovCount(Map<String, String> map);
	int selectMachineVodImgCount(Map<String, String> map);
	MachineVodAni selectAniByNo(String cntntsNo);
	MachineVodMov selectMovByNo(String cntntsNo);
	MachineVodImg selectImgByNo(String cntntsNo);
}
