package com.kbunl.backend.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.kbunl.backend.entity.ItComplain;
//import com.kbunl.mail.MailSender;


@Api(name = "iTComplainService",version = "v1",description="IT Complain Service API")
public class ItComplainService {
	
	
	private static DatastoreService datastore=DatastoreServiceFactory.getDatastoreService();
	
	@ApiMethod(name = "addComplain",description="Add IT Complain")
	public Message addComplain(
			@Named("user")String user,
			@Named("type") String type,
			@Named("location")String location,
			@Named("description")String description){
		
		
		
		//Check for valid Email of user field else return Message
		if(!EmailValidator.getInstance().isValid(user)){
	
			//Error Message
			Message msg=new Message();
			msg.setStatus("ERROR");
			msg.setMessage("Not a valid Email Address");
			
			return msg;
	
		}
		
		//Check for Valid Complain Type else return message
		Filter comptypeFilter =
			    new FilterPredicate("comptype", FilterOperator.EQUAL, type.trim());
		Query query=new Query("ItComplainType").setFilter(comptypeFilter);
		List<Entity> results =
		        datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		
		if(results.size() < 1){
				//Error Message
			Message msg=new Message();
			msg.setStatus("ERROR");
			msg.setMessage("Not a Valid Complain Type");
				
			return msg;
		}
		
			
			//Otherwise enter details			
			Entity complain=new Entity("ItComplain");
			
			complain.setProperty("user", user.toLowerCase().trim());
			complain.setProperty("time", new Date());
			complain.setProperty("status", "NEW");
			
			complain.setProperty("type", type);
			complain.setProperty("location", location.trim());
			//complain.setUnindexedProperty("location", location.trim());
			complain.setProperty("description", description.trim());
			//complain.setUnindexedProperty("description", description.trim());
			
			datastore.put(complain);
			
			//Success Message
			Message msg=new Message();
			msg.setStatus("OK");
			msg.setMessage("Successfully Created with Id: " + Long.toString(complain.getKey().getId()));
					
			//MailSender sender=new MailSender();
			//sender.sendSimpleMail("susantagoswami@ntpc.co.in", "New Comaplin Registered",
					//"Successfully Created with Id: " + Long.toString(complain.getKey().getId()));
			
			return msg;
			
	
	}

	@ApiMethod(name = "detailComplain",description="Details of Single Complain by Number")
	public ItComplain detailComplain(
			@Named("number")String number){
		
		ItComplain com=new ItComplain();
		
		Key findKey = KeyFactory.createKey("ItComplain", Long.parseLong(number.trim()));
		
		try {
			Entity e=datastore.get(findKey);
			
			com.setUser(this.santizeString(e.getProperty("user")));
			
			com.setNumber(e.getKey().getId());
			com.setTime(this.getLocalTime(e.getProperty("time").toString()));
			
			com.setStatus(e.getProperty("status").toString());			
			com.setType(e.getProperty("type").toString());
			com.setLocation(e.getProperty("location").toString());
			com.setDescription(e.getProperty("description").toString());
			
			com.setAssignby(this.santizeString(e.getProperty("assignby")));
			com.setAssignto(this.santizeString(e.getProperty("assignto")));
			com.setAssigntime(this.getLocalTime(this.santizeString(e.getProperty("assigntime"))));
			
			com.setPartialtime(this.santizeString(e.getProperty("partialtime")));
			com.setPartialcomment(this.santizeString(e.getProperty("partialcomment")));
			com.setCompletecomment(this.santizeString(e.getProperty("completecomment")));
			com.setCompletetime(this.santizeString(e.getProperty("completetime")));
			
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
				
		return com;
		
	}
	
	@ApiMethod(name = "assignComplain",description="Assign IT Complain to Service Engineer")
	public Message assignItComplain(
			@Named("number")String number,
			@Named("assignby")String assignby,
			@Named("assignto")String assignto){
		
		Key findKey = KeyFactory.createKey("ItComplain", Long.parseLong(number.trim()));
		Message msg=new Message();
		try {
			
			Entity e=datastore.get(findKey);
			e.setProperty("assignby", assignby.toLowerCase().trim());
			e.setProperty("assignto", assignto.toLowerCase().trim());
			e.setProperty("assigntime", new Date());
			e.setProperty("status","ASSIGNED");
			
			datastore.put(e);
			
			msg.setStatus("OK");
			msg.setMessage("Successfully Assigned");
			
			return msg;
			
		}catch (EntityNotFoundException e) {
			
			msg.setStatus("ERROR");
			msg.setMessage(e.getMessage());
			
			return msg;
		
		}
	}
	
	@ApiMethod(name = "attendComplain",description="Attend IT Complain by Service Engineer")
	public Message attendComplain(
			@Named("number")String number,
			@Named("comment")String comment,
			@Named("status") Boolean status){
		
		Key findKey = KeyFactory.createKey("ItComplain", Long.parseLong(number.trim()));
		Message msg=new Message();
		try {
			
			Entity e=datastore.get(findKey);
			
			if(status){
				e.setProperty("completetime", new Date());
				e.setProperty("completecomment", comment.trim());
				e.setProperty("status", "COMPLETE");
				
			}else{
				e.setProperty("partialtime", new Date());
				e.setProperty("partialcomment", comment.trim());
				e.setProperty("status", "PARTIAL");
				
			}
		
			datastore.put(e);
			
			msg.setStatus("OK");
			msg.setMessage("Successfully Completed");
			
			return msg;
			
		}catch (EntityNotFoundException e) {
			
			msg.setStatus("ERROR");
			msg.setMessage(e.getMessage());
			
			return msg;
		
		}
	
	}
	
	@ApiMethod(name = "listComUser",description="List of IT Complain of User by Status")
	public List<ItComplain> listComUser(
			@Named("user")String user,
			@Named("status")String status,
			@Named("role")String role){
		
		Query query=null;
		List<ItComplain> lsc=new ArrayList<ItComplain>();
		
		
		
		if(role.equals("USER")){
			
			Filter userFilter =
					   new FilterPredicate("user", FilterOperator.EQUAL, user.toLowerCase().trim());
			
			
			Filter statusFilter =
				    new FilterPredicate("status", FilterOperator.EQUAL, status.trim());
		
			// Use CompositeFilter to combine multiple filters
			CompositeFilter combinedFilter =
			    CompositeFilterOperator.and(userFilter, statusFilter);
			
			query=new Query("ItComplain")
					.setFilter(combinedFilter)
					.addSort("time", SortDirection.DESCENDING);
			
		}
		if(role.equals("SUPERVISOR")){
			

			Filter statusFilter =
				    new FilterPredicate("status", FilterOperator.EQUAL, status.trim());
				query=new Query("ItComplain")
					.setFilter(statusFilter)
					.addSort("time", SortDirection.DESCENDING);
			
		}
		
		//Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(query);
		
		for(Entity e : pq.asIterable()){
		
			ItComplain com=new ItComplain();
			
			com.setNumber(e.getKey().getId());
			com.setTime(this.getLocalTime(e.getProperty("time").toString()));
						
			com.setType(e.getProperty("type").toString());
			com.setLocation(e.getProperty("location").toString());
			com.setDescription(e.getProperty("description").toString());
						
			lsc.add(com);
		}
		
		return lsc;
		
	}
	//String Sanitize Method
	private String santizeString(Object object){
		String result="N/A";
		if(object !=null){
			
			if(!object.toString().isEmpty()){
				
				result=object.toString();
			}
			
		}
		return result;
	}
	
	//Get localtime
	private String getLocalTime(String inputString){
		
		//String dateInput="Thu Dec 21 11:57:16 UTC 2017";
		//String dateInput="N/A";
		inputString=inputString.replaceAll("UTC", "");
		String result;
		SimpleDateFormat sdf = 
						new SimpleDateFormat("E MMM dd HH:mm:ss yyyy");
				
		SimpleDateFormat sdf2 = 
						new SimpleDateFormat("E MMM dd yyyy HH:mm:ss ");
				
				
		TimeZone tz = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(tz);
		
				
		try {
					
			Date date=sdf.parse(inputString);
					
			TimeZone t2 = TimeZone.getTimeZone("Asia/Calcutta");
			sdf2.setTimeZone(t2);
			
			result=sdf2.format(date);
					
			} catch (ParseException e) {
					
				result="N/A";
					
			}catch(Exception ex){
					
					result="N/A";
			}
				
				
			return result;

	}
	
	
}
