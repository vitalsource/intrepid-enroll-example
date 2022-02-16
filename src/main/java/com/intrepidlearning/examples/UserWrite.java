package com.intrepidlearning.examples;

import java.util.Map;

public class UserWrite {
	public String username; // optional. if not provided a unique username will be generated.  if provided it must be unique
	public String externalId; // optional. if provided it must be unique
	public String firstName; // optional
	public String lastName; // optional
	public String email; // optional
	public String manager; // optional
	public Boolean enabled; // optional, defaults to true
	public Boolean forcePasswordReset; // optional
	public Boolean leaderboards; // optional
	public Boolean admin; // optional, defaults to false
	public String password; // optional, a random password will be generated if not provided.  if you are using sso just leave this blank
	public Map<String, String> customFields; // optional
	public Long activeUntil; // optional. seconds since epoch that user will automatically be disabled
}

