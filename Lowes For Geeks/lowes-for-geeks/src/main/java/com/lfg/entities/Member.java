package com.lfg.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "members")
public class Member {
	
	@Id
	private int  memberId;
	private String firstname;
	private String lastname;
	private String mailId;

 /* No need to input the following field if the member is not intended to be joined in the organization as administrator*/
	private boolean organizationAdmin = false;
	
	
	public boolean isOrganizationAdmin() {
		return organizationAdmin;
	}

	public void setOrganizationAdmin(boolean organizationAdmin) {
		this.organizationAdmin = organizationAdmin;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getMailId() {
		return mailId;
	}
	
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
}
