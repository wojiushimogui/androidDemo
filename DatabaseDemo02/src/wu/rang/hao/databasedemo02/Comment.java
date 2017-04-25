package wu.rang.hao.databasedemo02;

import org.litepal.crud.DataSupport;

public class Comment extends DataSupport {
	private int id;
	private String content;
	private int publishData;
	private News news;
	
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPublishData() {
		return publishData;
	}
	public void setPublishData(int publishData) {
		this.publishData = publishData;
	}
	

}
