package wu.rang.hao.databasedemo02;

import org.litepal.crud.DataSupport;

public class Introduce extends DataSupport {
	private int id;
	private String guide;
	private String digest;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
}
