package com.market.admin.dto;

import java.sql.Date;

public class QnaAdminDto {
	private int qnum;
	private String pname;
	private String title;
	private String name;
	private int ref;
	private Date reg_date;
	private String content;
	private int pnum;
	
	public QnaAdminDto(int qnum, String pname, String title, String name, int ref, Date reg_date, String content,
			int pnum) {
		super();
		this.qnum = qnum;
		this.pname = pname;
		this.title = title;
		this.name = name;
		this.ref = ref;
		this.reg_date = reg_date;
		this.content = content;
		this.pnum = pnum;
	}
	public QnaAdminDto(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	public int getQnum() {
		return qnum;
	}
	public void setQnum(int qnum) {
		this.qnum = qnum;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	
}
