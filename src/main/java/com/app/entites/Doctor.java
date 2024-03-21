package com.app.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doctor")
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="doctorid")
	private long doctorid;
	@Column(name="userid")
	private long userid;
	@Column(name="specialization")
	private String specialization;
	@Column(name="avatar")
	private byte[] avatar;
	@Column(name="name")
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDoctorid(long doctorid) {
		this.doctorid = doctorid;
	}
	public long getDoctorid() {
		return doctorid;
	}
	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	public Doctor(int userid, String specialization, byte[] avatar) {
		super();
		this.userid = userid;
		this.specialization = specialization;
		this.avatar = avatar;
	}
	public Doctor() {

	}
	
	
	
	
}
