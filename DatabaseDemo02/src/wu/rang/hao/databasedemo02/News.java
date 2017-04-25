package wu.rang.hao.databasedemo02;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class News extends DataSupport {
	private int id;
	private String title;
	private String content;
	private long publicData;
	private int commentCount;
	private Introduce introduce;//仅仅就这样一条语句，这样就构成了表news与introduce的一对一的关联
	private List<Comment> comment=new ArrayList<Comment>();//与Comment类中的一条语句配合，这样构成了与表comment一对多的关系
	private List<Category> category=new ArrayList<Category>();
	
	//下面为生成get和set方法
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getPublicData() {
		return publicData;
	}
	public void setPublicData(long publicData) {
		this.publicData = publicData;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Introduce getIntroduce() {
		return introduce;
	}
	public void setIntroduce(Introduce introduce) {
		this.introduce = introduce;
	}
	
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	
	
	

}
