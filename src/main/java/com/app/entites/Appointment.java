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
	private String namecustomer;
	private String namedoctor;
	private String email;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNamecustomer() {
		return namecustomer;
	}
	public void setNamecustomer(String namecustomer) {
		this.namecustomer = namecustomer;
	}
	public String getNamedoctor() {
		return namedoctor;
	}
	public void setNamedoctor(String namedoctor) {
		this.namedoctor = namedoctor;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public void setDoctorid(long doctorid) {
		this.doctorid = doctorid;
	}
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
	
	
	public Appointment(long userid, long doctorid, java.util.Date timeof, String meettime, String namecustomer,
			String namedoctor,String email) {
		super();
		this.userid = userid;
		this.doctorid = doctorid;
		this.timeof = timeof;
		this.meettime = meettime;
		this.namecustomer = namecustomer;
		this.namedoctor = namedoctor;
		this.email=email;
	}
	public Appointment() {
		super();
	}
	
	
	
	

}
