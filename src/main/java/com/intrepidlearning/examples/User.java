package com.intrepidlearning.examples;

import java.util.Map;

public class User {
	public String id;
	public String externalId;
	public String username;
	public String firstName;
	public String lastName;
	public String email;
	public String photo;
	public Boolean enabled;
	public Boolean forcePasswordReset;
	public Long systemCreationDate;
	public Long siteLastAccessDate; // last time user accessed the system
	public Long activeUntil;
	public Map<String, String> customFields;
	public String manager;

}
