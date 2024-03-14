package com.app.entites;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="userid")
	private long userid;
	@Column(name="doctorid")
	private long doctorid;
	@Column(name="timeof")
	private java.util.Date timeof;
	@Column(name="meettime")
	private String meettime;
	
	public String getMeettime() {
		return meettime;
	}
	public void setMeettime(String meettime) {
		this.meettime = meettime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public long getDoctorid() {
		return doctorid;
	}
	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}
	public java.util.Date getTimeof() {
		return timeof;
	}
	public void setTimeof(java.util.Date timeof) {
		this.timeof = timeof;
	}
	
	public Appointment(long userid, long doctorid, java.util.Date timeof, String meettime) {
		super();
		this.userid = userid;
		this.doctorid = doctorid;
		this.timeof = timeof;
		this.meettime = meettime;
	}
	public Appointment() {
		super();
	}
	
	
	
	

}
