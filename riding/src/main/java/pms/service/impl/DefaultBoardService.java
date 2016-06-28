package pms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.dao.BoardDao;
import pms.service.BoardService;
import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;

@Service
public class DefaultBoardService implements BoardService {
  @Autowired BoardDao boardDao;
  
  @Override
  public void add(Board board) {
    boardDao.insert(board);
  }
  
  @Override
  public void delete(int no) {
	  boardDao.delete(no);
  }
  
  @Override
  public void deleteMap(int bno) {
	  boardDao.deleteMap(bno);
  }
  
  @Override
  public void change(Board board) {
	  boardDao.update(board);
  }
  
  @Override
  public int countPage(int pageSize) {
	  int count = boardDao.countAll();
	  int pages = count / pageSize;
	  if ((count % pageSize) > 0)
		  pages++;
	  return pages;
  }
  
  @Override
  public Board retrieve(int no) {
    return boardDao.selectOne(no);
  }
  
  @Override
  public List<Board> List(int pageNo, int pageSize, String loca, String rbtype) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("startIndex", (pageNo - 1) * pageSize);
    paramMap.put("length", pageSize);
    paramMap.put("rbtype", rbtype);
    paramMap.put("loca", loca);
    
    return boardDao.selectList(paramMap);
  }
  
  @Override
  public List<Board> ListSchedule(int pageNo, int pageSize, int no) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  paramMap.put("startIndex", (pageNo - 1) * pageSize);
	  paramMap.put("length", pageSize);
	  paramMap.put("no", no);

	  return boardDao.selectListSchedule(paramMap);
  }

  @Override
  public List<Board> ListHistory(int pageNo, int pageSize, int no) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  paramMap.put("startIndex", (pageNo - 1) * pageSize);
	  paramMap.put("length", pageSize);
	  paramMap.put("no", no);

	  return boardDao.selectListHistory(paramMap);
  }

  @Override
  public void putMap(String ab, String bb) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  
	  Board board = boardDao.getLast(1);
	  int bno = board.getBno();
	  System.out.println(bno);
	  paramMap.put("ab", ab);
	  paramMap.put("bb", bb);
	  paramMap.put("bno", bno);
	  boardDao.insertxy(paramMap);

  }

  @Override
  public List<MapDot> getMap(int no) {
	  return boardDao.selectMap(no);
  }

  @Override
  public void join(int bno, int mno) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  paramMap.put("bno", bno);
	  paramMap.put("mno", mno);
	  boardDao.join(paramMap);
  }

  @Override
  public void joinCancel(int bno, int mno) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  paramMap.put("bno", bno);
	  paramMap.put("mno", mno);
	  boardDao.joinCancel(paramMap);
  }

  @Override
  public int isJoin(int bno, int mno) {
	  HashMap<String,Object> paramMap = new HashMap<>();
	  paramMap.put("bno", bno);
	  paramMap.put("mno", mno);
	  return boardDao.isJoin(paramMap);
  }

}//

/*
# Service 객체
- 비즈니스 로직을 수행한다.
- 트랜잭션을 제어한다.
- 메서드의 이름은 업무 용어에 가깝게 정의하라!
- 업무 처리에 필요하다면 여러 개의 DAO를 사용할 수 있다.
*/