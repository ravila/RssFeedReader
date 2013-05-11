package br.usp.rssfeedreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventoIme {

	private String title;
	private String place;
	private String category;
	private String date;
	private String hour;
	private String description;
	private String speaker;
	private String summary;
	
	
	public EventoIme(String title, String place, String category, String date,
			String hour, String description, String speaker, String summary) {
		this.title = title;
		this.place = place;
		this.category = category;
		this.date = date;
		this.hour = hour;
		this.description = description;
		this.speaker = speaker;
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSpeaker() {
		return speaker;
	}


	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	
	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public long getMiliSeconds() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			Date d1 = df.parse(getDate() + " " + getHour());
			
			return d1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
