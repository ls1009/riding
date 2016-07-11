package pms.vo;

import java.sql.Date;

public class Board {
	private int     bno;
	private int	  mno;		//글쓴이
	private String  rbtype; //free or guide
	private String  loca;	// 지역

	private String  title;
	private String  mloca; //모임 장소
	private String  mtime; //모임 시간
	private Date  	mday; //모임 날짜
	private String  distance; //예상거리
	private String  time;	//예상시간
	private String  pnum;	//참여인원
	private String  ph;
	private String  imglo;
	private Date    createdDate;
	
	private String mnm;
	
	
	
	public String getMnm() {
		return mnm;
	}
	public void setMnm(String mnm) {
		this.mnm = mnm;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getRbtype() {
		return rbtype;
	}
	public void setRbtype(String rbtype) {
		this.rbtype = rbtype;
	}
	public String getLoca() {
		return loca;
	}
	public void setLoca(String loca) {
		this.loca = loca;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMloca() {
		return mloca;
	}
	public void setMloca(String mloca) {
		this.mloca = mloca;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public Date getMday() {
		return mday;
	}
	public void setMday(Date mday) {
		this.mday = mday;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getImglo() {
		return imglo;
	}
	public void setImglo(String imglo) {
		this.imglo = imglo;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Board [bno=" + bno + ", mno=" + mno + ", rbtype=" + rbtype + ", loca=" + loca + ", title=" + title
				+ ", mloca=" + mloca + ", mtime=" + mtime + ", mday=" + mday + ", distance=" + distance + ", time="
				+ time + ", pnum=" + pnum + ", ph=" + ph + ", imglo=" + imglo + ", createdDate=" + createdDate + "]";
	}







}
