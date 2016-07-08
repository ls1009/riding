package pms.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import pms.service.MemberService;
import pms.vo.Member;
import pms.vo.PicturePath;

@Controller
@RequestMapping("/ajax/member/")
public class MemberAjaxController {
  @Autowired MemberService memberService;
  
  @RequestMapping(value="add", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String add(
		 String name,String email,String pw,String ph,String gender) 
	     throws ServletException, IOException {
    Member member = new Member();
    member.setName(name);
    member.setEmail(email);
    member.setPw(pw);
    member.setPh(ph);
    member.setGender(gender);
    HashMap<String,Object> result = new HashMap<>();
    try {
      memberService.add(member);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="delete", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String delete(int no) 
      throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
      memberService.delete(no);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="detail", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String detail(int no) throws ServletException, IOException {
    Member member = memberService.retrieveByNo(no);
    return new Gson().toJson(member);
  }
  
  @RequestMapping(value="list", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String list(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize) 
      throws ServletException, IOException {
    
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = memberService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    
    List<Member> list = memberService.list(pageNo, pageSize);
    
    HashMap<String,Object> result = new HashMap<>();
    result.put("pageNo", pageNo);
    result.put("pageSize", pageSize);
    result.put("totalPage", totalPage);
    result.put("list", list);
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="memberList", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String memberList(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize, int bno) 
      throws ServletException, IOException {
    
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = memberService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    
    List<Member> list = memberService.memberList(pageNo, pageSize, bno);
    
    HashMap<String,Object> result = new HashMap<>();
    result.put("pageNo", pageNo);
    result.put("pageSize", pageSize);
    result.put("totalPage", totalPage);
    result.put("list", list);
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="update",
      method=RequestMethod.POST,
      produces="application/json;charset=UTF-8")
  @ResponseBody
  public String update(
  int no, String name,String pw,String ph,String gender) 
  throws ServletException, IOException {
    System.out.println(no);
    Member member = new Member();
    member.setNo(no);
    member.setName(name);
    member.setPw(pw);
    member.setPh(ph);
    member.setGender(gender);
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      memberService.change(member);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value = "memberImg", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
  public String insert(
        MultipartHttpServletRequest request, ModelMap model, HttpSession session, MultipartFile myPhoto, Member sessionMember) 
        throws IllegalStateException, IOException{
	  	Member member = null;
		if((Member)session.getAttribute("loginUser") == null) {
			member = sessionMember;
		} else {
			member = (Member)session.getAttribute("loginUser");
		}
    //int extPoint = myPhoto.getOriginalFilename().lastIndexOf(".");
    //String filename = System.currentTimeMillis() + myPhoto.getOriginalFilename().substring(extPoint);
		
	String filename = System.currentTimeMillis() + ".jpg";
	
    PicturePath pp = new PicturePath();
    String path =pp.getProfilePath()+filename;
    String thumPath =pp.getProfilePath()+"thum/"+filename;
    
    /*String path = servletContext.getRealPath("img/memberImg/" + filename);*/
    //이미지 경로 DB 저장
    
    String dbpath ="img/memberImg/"+filename;
    String dbThumPath ="img/memberImg/thum/"+filename;
    member.setImg(dbpath);
  	member.setThumimg(dbThumPath);
    
    ///////////썸네일//////////////
    try {
      memberService.change(member);
      myPhoto.transferTo(new File(path));
      
    File originalFileNm = new File(path);
  	File thumbnailFileNm = new File(thumPath);
  	int width = 50;
  	int height = 50;
  	// 썸네일 이미지 생성
  	BufferedImage originalImg = ImageIO.read(originalFileNm);
  	BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
  	// 썸네일 그리기 
  	Graphics2D g = thumbnailImg.createGraphics();
  	g.drawImage(originalImg, 0, 0, width, height, null);
  	// 파일생성
  	ImageIO.write(thumbnailImg, "jpg", thumbnailFileNm);	
  	
  	
	  	
    } catch (Exception e) {
      e.printStackTrace();
    }
    
     return "redirect:../../myInfo.html";
  }

  @RequestMapping(value="getImg",
        method=RequestMethod.POST,
        produces="application/json;charset=UTF-8")
  @ResponseBody
  public String getImg(HttpSession session, Member sessionMember) throws ServletException, IOException {
	Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
	
     String url = member.getImg();
     HashMap<String,Object> result = new HashMap<>();
     try {
        result.put("status", url);
     } catch (Exception e) {
        result.put("status", "failure");
     }
     return new Gson().toJson(result);
  }
  
  @RequestMapping(value="loginMember",
      method=RequestMethod.POST,
      produces="application/json;charset=UTF-8")
  @ResponseBody
  public String loginMember(HttpSession session, Member sessionMember) throws ServletException, IOException {
	Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
		System.out.println("clientSession "+member);
	} else {
		member = (Member)session.getAttribute("loginUser");
		System.out.println("getAttribute "+member);
	}
	System.out.println(member);
	System.out.println(sessionMember);
    return new Gson().toJson(member);
  }
  
}//




