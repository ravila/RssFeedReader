package br.usp.rssfeedreader;

import java.util.List;

public class RSSFeed {

	private String title;
	private String description;
	private String link;
	private String rss_link;
	private String language;
	private List<RSSItem> items;
	
	public RSSFeed(String title, String description, String link,
			String rss_link, String language, List<RSSItem> items) {
		this.title = title;
		this.description = description;
		this.link = link;
		this.rss_link = rss_link;
		this.language = language;
		this.items = items;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRss_link() {
		return rss_link;
	}

	public void setRss_link(String rss_link) {
		this.rss_link = rss_link;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<RSSItem> getItems() {
		return items;
	}

	public void setItems(List<RSSItem> items) {
		this.items = items;
	}
	
}
