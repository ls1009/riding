package pms.service;

import java.util.List;

import pms.vo.Board;
import pms.vo.MapDot;
import pms.vo.Member;

public interface BoardService {
  void add(Board board);
  void delete(int no);
  Board retrieve(int no);
  List<Board> List(int pageNo, int pageSize, String loca, String rbtype);
  void change(Board board);
  int countPage(int pageSize);

  List<Board> ListSchedule(int pageNo, int pageSize, int no);
  List<Board> ListHistory(int pageNo, int pageSize, int no);
  void putMap(String ab, String bb);
  List<MapDot> getMap(int no);
  void deleteMap(int bno);
  void join(int bno, int mno);
  void joinCancel(int bno, int mno);
  int isJoin(int bno, int mno);
}
