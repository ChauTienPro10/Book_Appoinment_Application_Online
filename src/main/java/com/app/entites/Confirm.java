package com.app.entites;

import java.sql.Date;

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
	private int id;
	@Column(name="userid")
	private int userid;
	@Column(name="doctorid")
	private int doctorid;
	@Column(name="timeof")
	private Date timeof;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getDoctorid() {
		return doctorid;
	}
	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}
	public Date getTimeof() {
		return timeof;
	}
	public void setTimeof(Date timeof) {
		this.timeof = timeof;
	}
	public Confirm(int userid, int doctorid, Date timeof) {
		super();
		this.userid = userid;
		this.doctorid = doctorid;
		this.timeof = timeof;
	}
	public Confirm() {
		super();
	}
	
}
