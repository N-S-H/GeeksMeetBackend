package com.lfg.serviceinterfaces;

import java.util.List;
import java.util.Optional;

import com.lfg.entities.Member;

public interface MemberServiceInterface {
      Member createMember(Member member);
      
      String deleteMemberById(int id);
      
      Member updateMember(Member member);
      
      Optional<Member> getMemberbyId(int memberId);
      
      List<Member> getAllMembers();
      
      boolean CollectionIsEmpty();
}
