package com.atguigu.crowd.service.api;

import java.util.List;

import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
	
	PageInfo<Role> getPageInfo(Integer pageNum, Integer PageSize,String keyword);

	void saveRole(Role role);

	void updateRole(Role role);
	
	void removeRole(List<Integer> roleList);

}
