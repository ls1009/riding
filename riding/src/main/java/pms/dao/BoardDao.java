package pms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;
public interface BoardDao {
  List<Board> selectList(Map<String,Object> paramMap);
  int insert(Board board);
  Board selectOne(int no);
  int update(Board board);
  int delete(int no);
  int deleteMap(int no);
  int countAll();

  List<Board> selectListSchedule(HashMap<String, Object> paramMap);
  List<Board> selectListHistory(HashMap<String, Object> paramMap);
  int insertxy(HashMap<String, Object> paramMap);
  List<MapDot> selectMap(int no);
  Board getLast(int no);
  int join(Map<String,Object> paramMap);
  int joinCancel(HashMap<String, Object> paramMap);
  int isJoin(Map<String,Object> paramMap);
  List<Member> MemberList(HashMap<String, Object> paramMap);
}










