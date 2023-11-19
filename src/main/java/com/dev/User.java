package com.dev;

public class User {
	private String username;
	private String password ;
	private String Email;
	private int balance;
	public User(String u,String p,String e) {
		
		this.username=u;
		this.password=p;
		this.Email=e;
		this.balance=500;
		
	}
	public int get_balance() {
		return this.balance;
	}
	public String get_u() {
		
		
		return this.username;
	}
public String get_e() {
		
		
		return this.Email;
	}
public String get_p() {
	
	
	return this.password;
}

	
	

}
