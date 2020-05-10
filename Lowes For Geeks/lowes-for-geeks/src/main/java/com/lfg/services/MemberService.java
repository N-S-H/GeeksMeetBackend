package com.lfg.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lfg.entities.Member;
import com.lfg.repositories.MemberRepository;
import com.lfg.serviceinterfaces.MemberServiceInterface;

@Service
public class MemberService implements MemberServiceInterface{

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public String deleteMemberById(int id) {
		memberRepository.deleteById(id);
		return "The member with id: " + id + " has been successfully deleted";
	}


	@Override
	public Member updateMember(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public Optional<Member> getMemberbyId(int memberId) {
		return memberRepository.findById(memberId);
	}

	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	public boolean CollectionIsEmpty() {
		if( memberRepository.count() ==0) return true;
		else return false;
	}
	
}
