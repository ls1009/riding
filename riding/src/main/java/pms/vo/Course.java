package pms.vo;

import java.sql.Date;

public class Course {
  private int mcno;
  private String mctype;
  private int mno;
  private String title;
  private String des;
  private Date cdt;
  private String distance;
  private String time;
  private String loca;
  private String area;
  
  public int getMcno() {
    return mcno;
  }
  public void setMcno(int mcno) {
    this.mcno = mcno;
  }
  public String getMctype() {
    return mctype;
  }
  public void setMctype(String mctype) {
    this.mctype = mctype;
  }
  public int getMno() {
    return mno;
  }
  public void setMno(int mno) {
    this.mno = mno;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDes() {
    return des;
  }
  public void setDes(String des) {
    this.des = des;
  }
  public Date getCdt() {
    return cdt;
  }
  public void setCdt(Date cdt) {
    this.cdt = cdt;
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
  public String getLoca() {
    return loca;
  }
  public void setLoca(String loca) {
    this.loca = loca;
  }
  public String getArea() {
    return area;
  }
  public void setArea(String area) {
    this.area = area;
  }
}