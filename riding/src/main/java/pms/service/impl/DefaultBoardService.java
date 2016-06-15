package pms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pms.dao.BoardDao;
import pms.service.BoardService;
import pms.vo.Board;

@Service
public class DefaultBoardService implements BoardService {
  @Autowired BoardDao boardDao;
  
  public void add(Board board) {
    boardDao.insert(board);
  }
  
  public void delete(int no) {
    boardDao.delete(no);
  }
  
  public Board retrieve(int no) {
    return boardDao.selectOne(no);
  }
  
  public List<Board> freeList(int pageNo, int pageSize) {
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("startIndex", (pageNo - 1) * pageSize);
    paramMap.put("length", pageSize);
    
    return boardDao.selectList(paramMap);
  }
  
  public void change(Board board) {
    boardDao.update(board);
  }

  public int countPage(int pageSize) {
    int count = boardDao.countAll();
    int pages = count / pageSize;
    if ((count % pageSize) > 0)
      pages++;
    return pages;
  }
}

/*
# Service 객체
- 비즈니스 로직을 수행한다.
- 트랜잭션을 제어한다.
- 메서드의 이름은 업무 용어에 가깝게 정의하라!
- 업무 처리에 필요하다면 여러 개의 DAO를 사용할 수 있다.
*/