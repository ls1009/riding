package pms.service;

import java.util.List;

import pms.vo.Course;
import pms.vo.MapDot;

public interface CourseService {
  void add(Course course);
  void delete(int no);
  Course retrieve(int no);
  List<Course> List(int pageNo, int pageSize);
  void change(Course course);
  int countPage(int pageSize);
  
  void putMap(String ab, String bb, int mcno);
  List<MapDot> getMap(int no);
  void deleteMap(int mcno);
  
  void putImg(String dbpath, int mcno);
  List<String> getImg(int mcno);
}
