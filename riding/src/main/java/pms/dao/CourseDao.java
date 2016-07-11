package pms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pms.vo.Course;
import pms.vo.MapDot;
import pms.vo.Member;
public interface CourseDao {
  List<Course> selectList(Map<String,Object> paramMap);
  int insert(Course course);
  Course selectOne(int no);
  int update(Course course);
  int delete(int no);
  int deleteMap(int no);
  int countAll();

  List<Course> selectListSchedule(HashMap<String, Object> paramMap);
  List<Course> selectListHistory(HashMap<String, Object> paramMap);
  int insertxy(HashMap<String, Object> paramMap);
  List<MapDot> selectMap(int no);
  Course getLast(int no);
  int join(Map<String,Object> paramMap);
  int joinCancel(HashMap<String, Object> paramMap);
  int isJoin(Map<String,Object> paramMap);
  List<Member> MemberList(HashMap<String, Object> paramMap);
  
  void putImg(HashMap<String, Object> paramMap);
  List<String> getImg(HashMap<String, Object> paramMap);
}










