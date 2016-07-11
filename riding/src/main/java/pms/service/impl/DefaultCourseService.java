package pms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.dao.CourseDao;
import pms.service.CourseService;
import pms.vo.Course;
import pms.vo.MapDot;

@Service
public class DefaultCourseService implements CourseService {
  @Autowired CourseDao courseDao;
  
  @Override
  public void add(Course course) {
    courseDao.insert(course);
  }
  
  @Override
  public void delete(int no) {
	  courseDao.delete(no);
  }
  
  @Override
  public void deleteMap(int mcno) {
	  courseDao.deleteMap(mcno);
  }
  
  @Override
  public void change(Course course) {
	  courseDao.update(course);
  }
  
  @Override
  public int countPage(int pageSize) {
	  int count = courseDao.countAll();
	  int pages = count / pageSize;
	  if ((count % pageSize) > 0)
		  pages++;
	  return pages;
  }
  
  @Override
  public Course retrieve(int no) {
    return courseDao.selectOne(no);
  }
  
  @Override
  public List<Course> List(int pageNo, int pageSize) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("startIndex", (pageNo - 1) * pageSize);
    paramMap.put("length", pageSize);
    
    return courseDao.selectList(paramMap);
  }

  @Override
  public void putMap(String ab, String bb, int mcno) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  
	  if(mcno == -1) {
		  Course course = courseDao.getLast(1);
		  mcno = course.getMcno();
	  }
	  
	  paramMap.put("ab", ab);
	  paramMap.put("bb", bb);
	  paramMap.put("mcno", mcno);
	  courseDao.insertxy(paramMap);
  }

  @Override
  public List<MapDot> getMap(int no) {
	  return courseDao.selectMap(no);
  }
  
  @Override
  public void putImg(String dbpath, int mcno) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("mcno", mcno);
    paramMap.put("dbpath", dbpath);
    courseDao.putImg(paramMap);
  }

  @Override
  public List<String> getImg(int mcno) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("mcno", mcno);
    return courseDao.getImg(paramMap);
  }
}