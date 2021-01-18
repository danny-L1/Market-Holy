package com.market.admin.dto;

public class StatGenderDto {
	private int cnt;
	private String gender;
	public StatGenderDto(int cnt, String gender) {
		super();
		this.cnt = cnt;
		this.gender = gender;
		
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
}
