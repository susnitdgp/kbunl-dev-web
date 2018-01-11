package com.kbunl;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kbunl.mail.MailSender;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

	private static final long serialVersionUID = 1L;

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	
	 response.setContentType("text/plain");
	 response.setCharacterEncoding("UTF-8");
	    
	
	 String type = request.getParameter("type");
	 if (type != null && type.equals("simple")) {
	    response.getWriter().print("Sending Simple Mail");
	    MailSender sender=new MailSender();
		sender.sendSimpleMail("susnitdgp@gmail.com", "New Mail Test",
				"Successfully Sent Mail Body");
	 } 
    

    response.getWriter().print("Hello App Engine!\r\n");

  }
}