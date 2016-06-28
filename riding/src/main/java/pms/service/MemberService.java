package pms.service;

import java.util.List;

import pms.vo.Member;

public interface MemberService {
  void add(Member member);
  void delete(int no);
  Member retrieveByNo(int no);
  Member retrieveByEmail(String email);
  List<Member> list(int pageNo, int pageSize);
  void change(Member member);
  boolean exist(String email, String password);
  int countPage(int pageSize);
  List<Member> memberList(int pageNo, int pageSize, int bno);
}

