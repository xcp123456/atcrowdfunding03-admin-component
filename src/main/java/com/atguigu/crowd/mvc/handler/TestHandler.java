package com.atguigu.crowd.mvc.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.ParamData;
import com.atguigu.crowd.entity.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class TestHandler {
	
	Logger log = LoggerFactory.getLogger(TestHandler.class);

	@Autowired
	private AdminService adminService;
	
	
	@ResponseBody
	@RequestMapping("/send/compose/object.json")
	public ResultEntity<Student> testReceiveComposeObject1(@RequestBody Student student,HttpServletRequest request){
		boolean requestType = CrowdUtil.judgeRequestType(request);
		log.info("requestType:" + requestType);
		return ResultEntity.successWithData(student);
		
	}
	
	@ResponseBody
	@RequestMapping("/send/compose/object.html")
	public String testReceiveComposeObject(@RequestBody Student student,HttpServletRequest request) {
		boolean requestType = CrowdUtil.judgeRequestType(request);
		log.info("requestType:" + requestType);
		log.info(student.toString());
		return "success";
		
	}
	
	@ResponseBody
	@RequestMapping("send/array/three.html")
	public String testReceiveArrayThree(@RequestBody List<Integer> array) {
		for (Integer number : array) {
			log.info("number:"+number);
		}
		return "success";
		
	}
	
	@ResponseBody
	@RequestMapping("send/array/two.html")
	public String testReceiveArrayTwo(ParamData paramData) {
		List<Integer> array = paramData.getArray();
		for (Integer number : array) {
			System.out.println("number:"+number);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("send/array/one.html")
	public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
		for (Integer number : array) {
			System.out.println("number:"+number);
		}
//		return "target";
		return "success";
		
	}
	@RequestMapping("/test/ssm.html")
	public String testSsm(ModelMap modelMap,HttpServletRequest request) {
		
		boolean requestType = CrowdUtil.judgeRequestType(request);
		log.info("requestType:" + requestType);
		List<Admin> adminList = adminService.getAll();
		modelMap.addAttribute("adminList",adminList);
		int a = 10/0;
//		String ab = null;
//		int length = ab.length();
		return "target";
	}
}
