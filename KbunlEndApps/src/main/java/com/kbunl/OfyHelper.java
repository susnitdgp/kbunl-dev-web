package com.kbunl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import com.googlecode.objectify.ObjectifyService;
//import com.kbunl.backend.entity.ItComplain;



public class OfyHelper implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// This will be invoked as part of a warm up request, 
		// or the first user request if no warm up
	    // request.
	    //ObjectifyService.register(ItComplain.class);
	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// APP Engine does not currently invoke this method.
		
	}

}
