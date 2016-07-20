package pms.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import pms.service.CourseService;
import pms.vo.Course;
import pms.vo.MapDot;
import pms.vo.Member;
import pms.vo.PicturePath;

@Controller
@RequestMapping("/ajax/course/")
public class CourseAjaxController {
  @Autowired CourseService courseService;
  
  int currentMcno = 0;

  @RequestMapping(value = "upload", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
  public String insert(MultipartHttpServletRequest request,HttpSession session, String title, String des, String distance, String time, String loca, String area, Member sessionMember){
    Member member = null;
    if((Member)session.getAttribute("loginUser") == null) {
      member = sessionMember;
    } else {
      member = (Member)session.getAttribute("loginUser");
    }
    
    try {
      Course course = new Course();
      course.setTitle(title);
      course.setDes(des);
      course.setDistance(distance);
      course.setTime(time);
      course.setLoca(loca);
      course.setArea(area);
      course.setMno(member.getNo());
    
      courseService.add(course);
      
      /*Map<String, MultipartFile> files = request.getFileMap();
      CommonsMultipartFile cmf = (CommonsMultipartFile) files.get("uploadFile");*/
      List<MultipartFile> cmf = request.getFiles("uploadFile");
      if (cmf.size() == 1 && cmf.get(0).getOriginalFilename().equals("")) {
      } else {
        for (int i = 0; i < cmf.size(); i++) {
          int extPoint = cmf.get(i).getOriginalFilename().lastIndexOf(".");
          System.out.println(extPoint);
          
          String filename="";
          filename =  System.currentTimeMillis() + cmf.get(i).getOriginalFilename().substring(extPoint);
          PicturePath pp = new PicturePath();
          String path = pp.getCoursePicPath() + filename;
          String dbpath = "img/courseImg/" + filename;
          courseService.putImg(dbpath, -1);
          cmf.get(i).transferTo(new File(path));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:../../courseRead.html";
  }
  
  @RequestMapping(value="delete", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String delete(int no, HttpSession session) 
      throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
      courseService.deleteMap(no);
      courseService.deleteImg(no);
      courseService.delete(no);
      result.put("status", "success");
      return new Gson().toJson(result);
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="deleteMap", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String deleteMap(int mcno) 
      throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
      courseService.deleteMap(mcno);
      result.put("status", "success");
      return new Gson().toJson(result);
    } catch (Exception e) {
      result.put("status", "failure");
      return new Gson().toJson(result);
    }
  }
  
  @RequestMapping(value="detail", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String detail(int no, HttpSession session, Member sessionMember) throws ServletException, IOException {
	  Member member = null;
		if((Member)session.getAttribute("loginUser") == null) {
			member = sessionMember;
		} else {
			member = (Member)session.getAttribute("loginUser");
		}
	
		System.out.println(member);
		Course course = courseService.retrieve(no);
		System.out.println(course);
		HashMap<String,Object> result = new HashMap<>();

		if(course.getMno() == member.getNo()) {
		  result.put("status", "success");
		} else {
		  result.put("status", "fail");
		}
		result.put("course", course);
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="update",
		  method=RequestMethod.POST
		  )
  @ResponseBody
  public String update(MultipartHttpServletRequest request, int mcno, String title, String des, String distance, String time, String loca, String area, HttpSession session, Member sessionMember) throws ServletException, IOException {
    Member member = null;
    if((Member)session.getAttribute("loginUser") == null) {
      member = sessionMember;
    } else {
      member = (Member)session.getAttribute("loginUser");
    }

    System.out.println(mcno);
    Course course = courseService.retrieve(mcno);
    
    try {
      course.setTitle(title);
      course.setDes(des);
      course.setDistance(distance);
      course.setTime(time);
      course.setLoca(loca);
      course.setArea(area);
      
      courseService.change(course);
      
      List<MultipartFile> cmf = request.getFiles("uploadFile");
      if (cmf.size() == 1 && cmf.get(0).getOriginalFilename().equals("")) {
      } else {
        for (int i = 0; i < cmf.size(); i++) {
          int extPoint = cmf.get(i).getOriginalFilename().lastIndexOf(".");
          
          String filename = "";
          filename = System.currentTimeMillis() + cmf.get(i).getOriginalFilename().substring(extPoint);
          PicturePath pp = new PicturePath();
          String path = pp.getCoursePicPath() + filename;
          String dbpath = "img/courseImg/" + filename;
          courseService.putImg(dbpath, -1);
          cmf.get(i).transferTo(new File(path));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
	  return "redirect:../../courseRead.html?no" + mcno;
  }

  @RequestMapping(value="list", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String list(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize,
      HttpServletRequest request) 
      throws ServletException, IOException {
	  
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = courseService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    
    List<Course> list = courseService.List(pageNo, pageSize);
    
    HashMap<String,Object> result = new HashMap<>();
    result.put("pageNo", pageNo);
    result.put("pageSize", pageSize);
    result.put("totalPage", totalPage);
    result.put("list", list);
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="putMap", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String putMap(String ab, String bb, HttpSession session) 
      throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	  try {
	    courseService.putMap(ab, bb, -1);
	    result.put("status", "success");
	  } catch(Exception e) {
	    result.put("status", "failure");
	  }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="getMap", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
  @ResponseBody
  public String putMap(int no) 
      throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	  List<MapDot> list = courseService.getMap(no);
	  result.put("list", list);
	  try {
	    result.put("status", "success");
	  } catch(Exception e) {
	    result.put("status", "failure");
	  }
	  return new Gson().toJson(result);
  }
  
  @RequestMapping(value="changeMap", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String changeMap(int mcno, String ab, String bb, HttpSession session) 
      throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	  try {
	    courseService.putMap(ab, bb, mcno);
	    result.put("status", "success");
	  } catch(Exception e) {
	    result.put("status", "failure");
	  }
	  return new Gson().toJson(result);
  }
  
  @RequestMapping(value="getImg", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
  @ResponseBody
  public String getImg(int no, HttpSession session) throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
      List<String> imgPath = courseService.getImg(no);
    result.put("list", imgPath);
      result.put("status", "success");
    } catch(Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="courseNo", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String courseNo(HttpSession session, int no) 
      throws ServletException, IOException {
    currentMcno = no;
    HashMap<String,Object> result = new HashMap<>();
    try {
      result.put("no", no);
      result.put("status", "success");
    } catch(Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
}
