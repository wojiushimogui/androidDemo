package wu.rang.hao.databasedemo02;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {
	private int id;
	private String name;
	private List<News> news=new ArrayList<News>();
	public List<News> getNews() {
		return news;
	}
	public void setNews(List<News> news) {
		this.news = news;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
