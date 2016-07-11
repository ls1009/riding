package pms.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
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

import com.google.gson.Gson;

import pms.service.BoardService;
import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;
import pms.vo.PicturePath;

@Controller
@RequestMapping("/ajax/board/")
public class BoardAjaxController {
  @Autowired BoardService boardService;
  @Autowired ServletContext servletContext;
  
  int currentBno = 0;
  
  @RequestMapping(value="Add", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
  @ResponseBody
  public String add
  (String title, String mloca, String mtime,String mday, String distance, String time,
		  String pnum, String ph, String loca, String rbtype, HttpSession session, Member sessionMember)
  throws ServletException, IOException {
    Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
	
    Board board = new Board();
    board.setTitle(title);
    board.setMloca(mloca);
    board.setMtime(mtime);
    board.setMday(Date.valueOf(mday));
    board.setDistance(distance);
    board.setTime(time);
    board.setPnum(pnum);
    
    board.setRbtype(rbtype);
    board.setMno(member.getNo());
    board.setLoca(loca);
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      boardService.add(board);
      boardService.join(boardService.getLast(1).getBno(), member.getNo());
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="delete", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String delete(int bno, HttpSession session) 
      throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
	  boardService.deleteMemberList(bno);
	  boardService.deleteMap(bno);
	  boardService.deleteImg(bno);
      boardService.delete(bno);
      result.put("status", "success");
      return new Gson().toJson(result);
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
 @RequestMapping(value="deleteMap", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String deleteMap(int bno) 
      throws ServletException, IOException {
    HashMap<String,Object> result = new HashMap<>();
    try {
	      boardService.deleteMap(bno);
	      result.put("status", "success");
	      return new Gson().toJson(result);
    } catch (Exception e) {
      result.put("status", "failure");
      return new Gson().toJson(result);
    }
  }
  
  @RequestMapping(value="detail", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String detail(int bno, HttpSession session, Member sessionMember) throws ServletException, IOException {
	  Member member = null;
		if((Member)session.getAttribute("loginUser") == null) {
			member = sessionMember;
		} else {
			member = (Member)session.getAttribute("loginUser");
		}
	
	Board board = boardService.retrieve(bno);
	HashMap<String,Object> result = new HashMap<>();

	if(board.getMno() == member.getNo()) {
		result.put("status", "success");
	}else {
		result.put("status", "fail");
	}
	result.put("board", board);
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="update",
		  method=RequestMethod.POST,
		  produces="application/json;charset=UTF-8")
  @ResponseBody
  public String update(int bno, String title, String mloca,String mday, String mtime, String distance, String time,
		  String pnum, String ph, HttpSession session) throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	  Board board = boardService.retrieve(bno);

	  board.setTitle(title);
	  board.setMloca(mloca);
	  board.setMtime(mtime);
	  board.setMday(Date.valueOf(mday));
	  board.setDistance(distance);
	  board.setTime(time);
	  board.setPnum(pnum);

	  try {
		  boardService.change(board);
		  result.put("status", "success");
	  } catch (Exception e) {
		  result.put("status", "failure");
	  }
	  return new Gson().toJson(result);
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
    
    int totalPage = boardService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    String loca = request.getParameter("loca");
    String rbtype = request.getParameter("rbtype");
    List<Board> list = boardService.List(pageNo, pageSize, loca, rbtype);
    for(Board b : list) {
      System.out.println(b.getMnm());
    }
    HashMap<String,Object> result = new HashMap<>();
    result.put("pageNo", pageNo);
    result.put("pageSize", pageSize);
    result.put("totalPage", totalPage);
    result.put("list", list);
    
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="listSchedule", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String listSchedule(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize,
      HttpSession session, String rbtype, Member sessionMember) 
      throws ServletException, IOException {
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = boardService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    
    Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
		System.out.println(member);
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
    
    List<Board> list = boardService.ListSchedule(pageNo, pageSize, member.getNo(), rbtype);
    
    HashMap<String,Object> result = new HashMap<>();
    result.put("pageNo", pageNo);
    result.put("pageSize", pageSize);
    result.put("totalPage", totalPage);
    result.put("list", list);
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="listHistory", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String listHistory(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize,
      HttpSession session, String rbtype, Member sessionMember) 
      throws ServletException, IOException {
    // 페이지 번호와 페이지 당 출력 개수의 유효성 검사
    if (pageNo < 0) { // 1페이지 부터 시작
      pageNo = 1;
    }
    
    int totalPage = boardService.countPage(pageSize);
    if (pageNo > totalPage) { // 가장 큰 페이지 번호를 넘지 않게 한다.
      pageNo = totalPage;
    }
    
    if (pageSize < 5) { // 최소 3개
      pageSize = 5; 
    } else if (pageSize > 50) { // 최대 50개 
      pageSize = 50;
    }
    
    Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
    
    List<Board> list = boardService.ListHistory(pageNo, pageSize, member.getNo(), rbtype);
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
	    boardService.putMap(ab, bb, -1);
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="getMap", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
  @ResponseBody
  public String putMap(int bno) 
      throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	  List<MapDot> list = boardService.getMap(bno);
	  result.put("list", list);
	try {
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="changeMap", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String changeMap(int bno, String ab, String bb, HttpSession session) 
      throws ServletException, IOException {

	  HashMap<String,Object> result = new HashMap<>();
	try {
	    boardService.putMap(ab, bb, bno);
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="join", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String join(int bno, HttpSession session, Member sessionMember) 
      throws ServletException, IOException {

    Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
	
	HashMap<String,Object> result = new HashMap<>();
	
	try {
		if(boardService.isJoin(bno, member.getNo()) > 0) {
			boardService.joinCancel(bno, member.getNo());
			result.put("status", "cancel");
		}else {
			boardService.join(bno, member.getNo());
			result.put("status", "success");
		}
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="isJoin", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
  @ResponseBody
  public String isJoin(int bno, HttpSession session, Member sessionMember) 
      throws ServletException, IOException {

	Member member = null;
	if((Member)session.getAttribute("loginUser") == null) {
		member = sessionMember;
	} else {
		member = (Member)session.getAttribute("loginUser");
	}
	HashMap<String,Object> result = new HashMap<>();
	
	try {
		if(boardService.isJoin(bno, member.getNo()) > 0) {
			result.put("status", "yes");
		}else {
			result.put("status", "no");
		}
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="putImg", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  public String putImg(MultipartFile img, HttpSession session) 
      throws ServletException, IOException {
	  int bno = currentBno;
	  int extPoint = img.getOriginalFilename().lastIndexOf(".");
      String filename = System.currentTimeMillis() + img.getOriginalFilename().substring(extPoint);	 
     
      PicturePath pp = new PicturePath();
	  
      String path =pp.getBoardPicPath()+filename;
    /*String path = servletContext.getRealPath("img/boardImg/" + filename);*/
	  String dbpath ="img/boardImg/"+filename;	  
	  
	  HashMap<String,Object> result = new HashMap<>();
	  try {
	    boardService.putImg(dbpath, bno);
	    List<String> imgPath = boardService.getImg(bno);
		img.transferTo(new File(path));	
		result.put("list", imgPath);
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return "redirect:../../freeRead.html?bno="+bno;
  }
  
  @RequestMapping(value="getImg", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
  @ResponseBody
  public String getImg(int bno, HttpSession session) 
      throws ServletException, IOException {
	  HashMap<String,Object> result = new HashMap<>();
	  try {
	    List<String> imgPath = boardService.getImg(bno);
		result.put("list", imgPath);
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
	return new Gson().toJson(result);
  }
  
  @RequestMapping(value="boardNo", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String boardNo(HttpSession session, int bno) 
      throws ServletException, IOException {
	  currentBno = bno;
	  HashMap<String,Object> result = new HashMap<>();
	  try {
		result.put("bno", bno);
	    result.put("status", "success");
	}catch(Exception e) {
		result.put("status", "failure");
	}
    return new Gson().toJson(result);
  }
  
}//




