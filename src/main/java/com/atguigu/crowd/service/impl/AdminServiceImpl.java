package com.atguigu.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.entity.AdminExample.Criteria;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	public void save(Admin admin) {
		String password = admin.getUserPswd();
		admin.setUserPswd(CrowdUtil.md5(password));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		admin.setCreateTime(format.format(new Date()));
		// 3.执行保存
		try {
			adminMapper.insert(admin);
		} catch (Exception e) {
			e.printStackTrace();
			
			logger.info("异常全类名="+e.getClass().getName());
			
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}
	public List<Admin> getAll() {
		
		return adminMapper.selectByExample(new AdminExample());
	}
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

		// 1.根据登录账号查询Admin对象
		// ①创建AdminExample对象
		AdminExample adminExample = new AdminExample();
		
		// ②创建Criteria对象
		Criteria criteria = adminExample.createCriteria();
		
		// ③在Criteria对象中封装查询条件
		criteria.andLoginAcctEqualTo(loginAcct);
		
		// ④调用AdminMapper的方法执行查询
		List<Admin> list = adminMapper.selectByExample(adminExample);
		
		// 2.判断Admin对象是否为null
		if(list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		if(list.size() > 1) {
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		
		Admin admin = list.get(0);
		
		// 3.如果Admin对象为null则抛出异常
		if(admin == null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4.如果Admin对象不为null则将数据库密码从Admin对象中取出
		String userPswdDB = admin.getUserPswd();
		
		// 5.将表单提交的明文密码进行加密
		String userPswdForm = CrowdUtil.md5(userPswd);
		
		// 6.对密码进行比较
		if(!Objects.equals(userPswdDB, userPswdForm)) {
			// 7.如果比较结果是不一致则抛出异常
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 8.如果一致则返回Admin对象
		return admin;
	}
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		List<Admin> list = adminMapper.selectAdminByKeyword(keyword);
		
		return new PageInfo<Admin>(list);
		
	}
	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
		
	}
	public Admin getAdminById(Integer adminId) {
		
		return adminMapper.selectByPrimaryKey(adminId);
	}
	public void update(Admin admin) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			admin.setCreateTime(format.format(new Date()));
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		
	}

}
