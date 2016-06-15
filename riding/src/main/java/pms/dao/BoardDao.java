package pms.dao;

import java.util.List;
import java.util.Map;

import pms.vo.Board;

public interface BoardDao {
  List<Board> selectList(Map<String,Object> paramMap);
  int insert(Board board);
  Board selectOne(int no);
  int update(Board board);
  int delete(int no);
  int countAll();
}










