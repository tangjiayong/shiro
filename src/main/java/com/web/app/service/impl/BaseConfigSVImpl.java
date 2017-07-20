package com.web.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.web.app.dao.ListExtractor;
import com.web.app.service.intf.BaseConfigSV;

@Service
public class BaseConfigSVImpl implements BaseConfigSV 
{
	private static final Log logger = LogFactory.getLog(BaseConfigSVImpl.class);
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Resource
	ListExtractor listExtractor;
	
	@Override
	public List<List<Object>> loadConfig() 
	{
		List<List<Object>> list = jdbcTemplate.query("select * from tst_data", listExtractor);
		return list;
	}

	@Override
	public void updateData() throws Exception
	{
		logger.info(" run in update---------------------------");
		jdbcTemplate.update("update tst_data set name='t"+(int)(Math.random()*20+3)+"' where id = 3");
	}
}
