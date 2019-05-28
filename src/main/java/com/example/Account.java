package com.example;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "account", schema = "salesforce")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String sfid;

	@Column(name = "name")
	@NotBlank(message = "Name is mandatory")
	private String name;
	 
	@Column(name = "phone")
	@NotBlank(message = "Phone is mandatory")
	private String phone;
	 
	@Column(name = "ownership")
	@NotBlank(message = "Ownership is mandatory")
	private String ownership;
	
	@Column(name="createddate", insertable=true)
    @Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public Account() {
	}

	public Account(long id, String sfid, @NotBlank(message = "Name is mandatory") String name,
			@NotBlank(message = "Phone is mandatory") String phone,
			@NotBlank(message = "Ownership is mandatory") String ownership) {
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
