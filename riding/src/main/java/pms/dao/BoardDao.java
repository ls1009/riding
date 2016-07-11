package pms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;
public interface BoardDao {
  List<Board> selectList(Map<String,Object> paramMap);
  List<Board> selectListSchedule(HashMap<String, Object> paramMap);
  List<Board> selectListHistory(HashMap<String, Object> paramMap);
  List<MapDot> selectMap(int no);
  List<String> getImg(HashMap<String, Object> paramMap);
  List<Member> MemberList(HashMap<String, Object> paramMap);
  Board getLast(int no);
  
  int insert(Board board);
  int insertxy(HashMap<String, Object> paramMap);
  Board selectOne(int no);
  void putImg(HashMap<String, Object> paramMap);

  int update(Board board);
  
  int delete(int no);
  int deleteMap(int no);
  void deleteImg(int bno);
  void deleteMemberList(int bno);
  int countAll();

  int join(Map<String,Object> paramMap);
  int joinCancel(HashMap<String, Object> paramMap);
  int isJoin(Map<String,Object> paramMap);
}










