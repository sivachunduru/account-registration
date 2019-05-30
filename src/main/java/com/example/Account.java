package com.example;

import java.util.Date;

public class Account {

	private long id;

	private String sfid;

	private String name;
	 
	private String phone;
	 
	private String ownership;
	
	private Date createdDate;

	public Account() {
	}

	public Account(long id, String sfid, String name,
			String phone,
			String ownership) {
		super();
		this.id = id;
		this.sfid = sfid;
		this.name = name;
		this.phone = phone;
		this.ownership = ownership;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", sfid=" + sfid + ", name=" + name + ", phone=" + phone + ", ownership="
				+ ownership + ", createdDate=" + createdDate + "]";
	}
}
