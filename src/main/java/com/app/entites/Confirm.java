package com.app.entites;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="confirm")
public class Confirm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="userid")
	private long userid;
	@Column(name="doctorid")
	private long doctorid;
	@Column(name="timeof")
	private Date timeof;
	@Column(name="meettime")
	private String meettime;
	
	private String nameother;
	
	private String mailcustomer;
	
	public String getMailcustomer() {
		return mailcustomer;
	}
	public void setMailcustomer(String mailcustomer) {
		this.mailcustomer = mailcustomer;
	}
	public String getNameother() {
		return nameother;
	}
	public void setNameother(String nameother) {
		this.nameother = nameother;
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
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getDoctorid() {
		return doctorid;
	}
	public void setDoctorid(long doctorid) {
		this.doctorid = doctorid;
	}
	public Date getTimeof() {
		return timeof;
	}
	public void setTimeof(Date timeof) {
		this.timeof = timeof;
	}
	
	

	public Confirm(long userid, long doctorid, Date timeof, String meettime, String nameother, String mailcustomer) {
		super();
		this.userid = userid;
		this.doctorid = doctorid;
		this.timeof = timeof;
		this.meettime = meettime;
		this.nameother = nameother;
		this.mailcustomer = mailcustomer;
	}
	public Confirm() {
		super();
	}
	
}
