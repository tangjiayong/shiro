package com.web.app.service.intf;

import java.util.List;

public interface BaseConfigSV 
{
	
	public List< List<Object> > loadConfig();
	
	public void updateData() throws Exception;
	
}
