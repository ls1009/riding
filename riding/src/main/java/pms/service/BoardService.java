package pms.service;

import java.util.List;

import pms.vo.Board;

public interface BoardService {
  void add(Board board);
  void delete(int no);
  Board retrieve(int no);
  List<Board> list(int pageNo, int pageSize);
  void change(Board board);
  int countPage(int pageSize);
}
