package com.web.app.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.service.intf.BaseConfigSV;

@Controller
public class IndexController 
{
	private final Log logger = LogFactory.getLog(IndexController.class);

	@Resource
	BaseConfigSV baseCfgSV;
	
	@RequestMapping(value = "/config")
	public ModelAndView showConfig(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String requestUrl = request.getScheme() //当前链接使用的协议
			    +"://" + request.getServerName()//服务器地址 
			    + ":" + request.getServerPort() //端口号 
			    + request.getContextPath() //应用名称，如果应用名称为
			    + request.getServletPath(); //请求的相对url 
		logger.info("Run in Controller, Request URI:"+requestUrl);
		

		byte[] bts = new byte[1024];
		InputStream isofter = request.getInputStream();
		int num = isofter.read(bts);
		String body = new String(bts, 0, num, "ISO-8859-1");
		System.out.println(body);
		
		
		ModelAndView mv = new ModelAndView("showCfg");
		List< List<Object> > data = baseCfgSV.loadConfig();
		mv.addObject("result", data);
		return mv;
	}
}
