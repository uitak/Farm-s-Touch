package com.multi.bbs.plant.model.vo;

import java.util.Date;

import com.multi.bbs.board.model.vo.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GardenImg {
	private int imgNo;
	private String cntntsNo;
	private String imgUrl;
}
