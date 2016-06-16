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

import pms.service.BoardService;
import pms.vo.Board;

@Controller
@RequestMapping("/ajax/board/")
public class BoardAjaxController {
  @Autowired BoardService boardService;
  
  @RequestMapping(value="Add", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String add
  (String title, String mloca, String mtime, String distance, String time,
		  String pnum, String ph, String loca, String rbtype)
  throws ServletException, IOException {
    Board board = new Board();
    board.setTitle(title);
    board.setMloca(mloca);
    board.setMtime(mtime);
    board.setDistance(distance);
    board.setTime(time);
    board.setPnum(pnum);
    board.setPh(ph);
    
    board.setRbtype(rbtype);
    board.setMno(1);
    board.setLoca(loca);
    System.out.println(loca);
    HashMap<String,Object> result = new HashMap<>();
    try {
      boardService.add(board);
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
      boardService.delete(no);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(value="detail", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String detail(int no) throws ServletException, IOException {
    Board board = boardService.retrieve(no);
    return new Gson().toJson(board);
  }
  
  @RequestMapping(value="freeList", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String list(
      @RequestParam(defaultValue="1") int pageNo, 
      @RequestParam(defaultValue="5") int pageSize) 
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
    
    List<Board> list = boardService.freeList(pageNo, pageSize);
    
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
  public String update(int bno, String title, String mloca, String mtime, String distance, String time,
		  String pnum, String ph, String rbtype) throws ServletException, IOException {
    
    Board board = new Board();
    board.setBno(bno);
    board.setTitle(title);
    board.setMloca(mloca);
    board.setMtime(mtime);
    board.setDistance(distance);
    board.setTime(time);
    board.setPnum(pnum);
    board.setPh(ph);
    
    board.setRbtype(rbtype);
    board.setMno(1);
    board.setMloca("서울");
    
    HashMap<String,Object> result = new HashMap<>();
    try {
      boardService.change(board);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
}




