package com.nzarudna.logparser.model;

import java.util.Date;

public abstract class Request {

	private Date datetime;
	private String threadName;
	private String userContext;
	private int duration;

	public Request(Date datetime, String threadName, String userContext, int duration) {
		this.datetime = datetime;
		this.threadName = threadName;
		this.userContext = userContext;
		this.duration = duration;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getUserContext() {
		return userContext;
	}

	public void setUserContext(String userContext) {
		this.userContext = userContext;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public abstract String getResourceIdentifier();
}
