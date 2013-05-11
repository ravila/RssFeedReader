package br.usp.rssfeedreader;


public class RSSItem {

	private Integer id;
	private Integer noticiaId;
	private String title;
	private String link;
    private String description;
    private Integer categoryId;
    private String pubdate;
	
    public RSSItem() {
    	
	}
    
    public RSSItem(Integer noticiaId, String title, String link, String description,
			Integer categoryId, String pubdate) {
    	this.noticiaId = noticiaId;
		this.title = title;
		this.link = link;
		this.description = description;
		this.categoryId = categoryId;
		this.pubdate = pubdate;
	}

    public Integer getNoticiaId() {
		return noticiaId;
	}

	public void setNoticiaId(Integer noticiaId) {
		this.noticiaId = noticiaId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
