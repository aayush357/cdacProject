package com.entity.model.keys;

import java.io.Serializable;
import java.util.Objects;

public class AppUserID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String aadhar;
	
	public AppUserID() {}
	public AppUserID(String email, String aadhar) {
		super();
		this.email = email;
		this.aadhar = aadhar;
	}
	@Override
	public int hashCode() {
		return Objects.hash(aadhar, email);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUserID other = (AppUserID) obj;
		return Objects.equals(aadhar, other.aadhar) && Objects.equals(email, other.email);
	}
	

}
