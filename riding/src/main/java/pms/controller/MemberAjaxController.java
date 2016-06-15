package pms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import pms.service.MemberService;
import pms.vo.Member;

@Controller
@RequestMapping("/ajax/member/")
public class MemberAjaxController {
  @Autowired MemberService memberService;
  
  @RequestMapping(value="add", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String add(
		 String name,String email,String pw,String ph,String position,String gender,String img) 
	     throws ServletException, IOException {

    Member member = new Member();
    member.setName(name);
    member.setEmail(email);
    member.setPw(pw);
    member.setPh(ph);
    member.setPosition(position);
    member.setGender(gender);
    member.setImg(img);
    
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
      @RequestParam(defaultValue="3") int pageSize) 
      throws ServletException, IOException {
    
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = memberService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 3) { // 최소 3개
      pageSize = 3; 
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
  
  @RequestMapping(value="update",
      method=RequestMethod.POST,
      produces="application/json;charset=UTF-8")
  @ResponseBody
  public String update(
  int no, String name,String email,String pw,String ph,String position,String gender,String img) 
  throws ServletException, IOException {
    
    Member member = new Member();
    member.setNo(no);
    member.setName(name);
    member.setEmail(email);
    member.setPw(pw);
    member.setPh(ph);
    member.setPosition(position);
    member.setGender(gender);
    member.setImg(img);
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      memberService.change(member);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
}




