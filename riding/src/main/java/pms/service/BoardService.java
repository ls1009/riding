package pms.service;

import java.util.List;

import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;

public interface BoardService {
  void add(Board board);
  void putMap(String ab, String bb, int bno);
  void putImg(String dbpath, int bno);

  void delete(int no);
  void deleteMemberList(int bno);
  void deleteMap(int bno);
  void deleteImg(int bno);
  
  Board retrieve(int no);
  List<Board> List(int pageNo, int pageSize, String loca, String rbtype);
  List<Board> ListSchedule(int pageNo, int pageSize, int no, String rbtype);
  List<Board> ListHistory(int pageNo, int pageSize, int no,  String rbtype);
  List<MapDot> getMap(int no);
  List<String> getImg(int bno);
  
  void change(Board board);
  int countPage(int pageSize);

  void join(int bno, int mno);
  void joinCancel(int bno, int mno);
  int isJoin(int bno, int mno);
  Board getLast(int bno);
}
