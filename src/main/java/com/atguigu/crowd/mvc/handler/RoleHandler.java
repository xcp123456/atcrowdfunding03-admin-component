package com.atguigu.crowd.mvc.handler;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.service.impl.AdminServiceImpl;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

@Controller
public class RoleHandler {
	private Logger logger = LoggerFactory.getLogger(RoleHandler.class);
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping("/role/remove/by/role/id/array.json")
	public ResultEntity<String> removeByRoleIdAarry(@RequestBody List<Integer> roleIdList) {
		
		roleService.removeRole(roleIdList);
		
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("role/update.json")
	public ResultEntity<String> updateRole(Role role){
		roleService.updateRole(role);
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("role/save.json")
	public ResultEntity<String> saveRole(Role role){
		roleService.saveRole(role);
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("/role/get/page/info.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize",defaultValue = "5") Integer pagaSize,
			@RequestParam(value = "keyword",defaultValue = "") String keyword){
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pagaSize, keyword);
//		try {
//			PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pagaSize, keyword);
//			return ResultEntity.successWithData(pageInfo);
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//			return ResultEntity.failed(e.getMessage());
//		}
		// 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
		return ResultEntity.successWithData(pageInfo);
	}
}
