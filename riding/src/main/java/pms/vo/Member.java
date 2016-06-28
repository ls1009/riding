package pms.vo;

public class Member {
	protected int    no;
	protected String name;
	protected String email;
	protected String pw;
	protected String ph;
	protected String position;
	protected String gender;
	protected String img;
	
	@Override
	public String toString() {
		return "Member [no=" + no + ", name=" + name + ", email=" + email + ", pw=" + pw + ", ph=" + ph + ", position="
				+ position + ", gender=" + gender + ", img=" + img + "]";
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	
	
	
	
		
}
