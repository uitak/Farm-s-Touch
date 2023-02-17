package com.multi.bbs.loveTypePlantTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.multi.bbs.mlrdcuration.controller.MlrdcurationController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoveTypePlantTestController {
	
	
	@GetMapping("/test/loveTypePlantTest")
	public String loveTypePlantTest(Model model) {
		return "test/loveTypePlantTest";
	} 
	
	@GetMapping("/test/result-0")
	public String result0(Model model) {
		return "test/result-0";
	} 
	
	@GetMapping("/test/result-1")
	public String result1(Model model) {
		return "test/result-1";
	} 
	
	@GetMapping("/test/result-2")
	public String result2(Model model) {
		return "test/result-2";
	} 
	
	@GetMapping("/test/result-3")
	public String result3(Model model) {
		return "test/result-3";
	} 
	@GetMapping("/test/result-4")
	public String result4(Model model) {
		return "test/result-4";
	} 
	
	@GetMapping("/test/result-5")
	public String result5(Model model) {
		return "test/result-5";
	} 
	
	@GetMapping("/test/result-6")
	public String result6(Model model) {
		return "test/result-6";
	} 
	@GetMapping("/test/result-7")
	public String result7(Model model) {
		return "test/result-7";
	} 
	
	@GetMapping("/test/result-8")
	public String result8(Model model) {
		return "test/result-8";
	} 
	
	@GetMapping("/test/result-9")
	public String result9(Model model) {
		return "test/result-9";
	} 
	
	@GetMapping("/test/result-10")
	public String result10(Model model) {
		return "test/result-10";
	} 
	
	@GetMapping("/test/result-11")
	public String result11(Model model) {
		return "test/result-11";
	} 
	

}
