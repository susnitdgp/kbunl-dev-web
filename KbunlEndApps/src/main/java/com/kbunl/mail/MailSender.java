package com.kbunl.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailSender {
	
	
	public void sendSimpleMail(String toAddress,String mailSubject,String mailText) {
	    
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    try {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("admin@kbunl-dev.appspotmail.com", 
	    		  "KBUNL Cloud Admin"));
	      msg.addRecipient(Message.RecipientType.TO,
	                       new InternetAddress(toAddress, "Mr. User"));
	      
	      msg.setSubject(mailSubject);
	      msg.setText(mailText);
	      Transport.send(msg);
	      
	    } catch (AddressException e) {
	      
	    } catch (MessagingException e) {
	      
	    } catch (UnsupportedEncodingException e) {
	      
	    }
	    
	  }

}
