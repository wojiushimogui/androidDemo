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
	private Introduce introduce;//����������һ����䣬�����͹����˱�news��introduce��һ��һ�Ĺ���
	private List<Comment> comment=new ArrayList<Comment>();//��Comment���е�һ�������ϣ��������������commentһ�Զ�Ĺ�ϵ
	private List<Category> category=new ArrayList<Category>();
	
	//����Ϊ����get��set����
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
