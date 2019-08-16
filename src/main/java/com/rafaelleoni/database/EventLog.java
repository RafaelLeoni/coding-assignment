package com.rafaelleoni.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uuid;
	
	private String id;
    private int duration;
    private String type;
    private String host;
    private Boolean alert;
	
    public String getId() {
		return id;
	}
	
    public void setId(String id) {
		this.id = id;
	}
	
    public int getDuration() {
		return duration;
	}
	
    public void setDuration(int duration) {
		this.duration = duration;
	}
	
    public String getType() {
		return type;
	}
	
    public void setType(String type) {
		this.type = type;
	}
	
    public String getHost() {
		return host;
	}
	
    public void setHost(String host) {
		this.host = host;
	}
	
    public Boolean getAlert() {
		return alert;
	}
	
    public void setAlert(Boolean alert) {
		this.alert = alert;
	}

}
