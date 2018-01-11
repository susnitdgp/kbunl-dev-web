package com.kbunl.backend.entity;


public class ItComplain {
		
	private Long number; //Complain Number
	
	
	private String user; //kisne diya
	private String time; //Kab Diya
	private String status; //NEW -ASSIGNED- PARTIAL--COMPLETE  --- CLOSE
	
	
	private String type; //PC-PRINTER
    private String location; //AREA LOCATION
    private String description; //DESCRIPTION
    
  
    private String assignby;
    private String assignto;
    private String assigntime;
    
    
    private String partialtime;
    private String partialcomment;
    
   
    private String completetime;
    private String completecomment;


	//Getter And Setter

	public Long getNumber() {
		return number;
	}


	public void setNumber(Long number) {
		this.number = number;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getAssignby() {
		return assignby;
	}


	public void setAssignby(String assignby) {
		this.assignby = assignby;
	}


	public String getAssignto() {
		return assignto;
	}


	public void setAssignto(String assignto) {
		this.assignto = assignto;
	}


	public String getAssigntime() {
		return assigntime;
	}


	public void setAssigntime(String assigntime) {
		this.assigntime = assigntime;
	}


	public String getPartialtime() {
		return partialtime;
	}


	public void setPartialtime(String partialtime) {
		this.partialtime = partialtime;
	}
	
	

	public String getPartialcomment() {
		return partialcomment;
	}


	public void setPartialcomment(String partialcomment) {
		this.partialcomment = partialcomment;
	}


	public String getCompletetime() {
		return completetime;
	}


	public void setCompletetime(String completetime) {
		this.completetime = completetime;
	}


	public String getCompletecomment() {
		return completecomment;
	}


	public void setCompletecomment(String completecomment) {
		this.completecomment = completecomment;
	}
  
	

	

}
