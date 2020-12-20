package com.atguigu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.entity.RoleExample;
import com.atguigu.crowd.entity.RoleExample.Criteria;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public PageInfo<Role> getPageInfo(Integer pageNum, Integer PageSize, String keyword) {
		// 开启分页的功能
		PageHelper.startPage(pageNum, PageSize);
		List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
		return new PageInfo<Role>(roleList);
	}

	public void saveRole(Role role) {
		roleMapper.insert(role);
		
	}

	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
		
	}

	public void removeRole(List<Integer> roleList) {
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(roleList);
		roleMapper.deleteByExample(example);
		
	}
}
