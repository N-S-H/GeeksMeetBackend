package com.lfg.controllers;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lfg.entities.Member;
import com.lfg.entities.MemberActivity;

@RestController
@RequestMapping("lowesforgeeks/member")
public class MemberContoller extends BaseController{
 
	@PostMapping("/{memberId}/create")
	public Member create(@PathVariable(name = "memberId") int memberId, @RequestBody Member member)
	{
	
		int newMemberId = member.getMemberId();
		if(!organization.isOrganizationAdmin(memberId))
		{
			System.out.println("Only organization admins can add members to the organization!");
		}
		else if(member.getFirstname().length()>100 || member.getLastname().length()>100)
		{
			System.out.println("The length of first name and last name must be maximum of 100 characters");
		}
		else if(!organization.findMemberWithIdExists(newMemberId))
		{
		MemberActivity memberActivity = organization.addMemberActivityToOrganization(newMemberId);
		memberActivityService.createMemberActivity(memberActivity);
		organization.addMemberToOrganization(member);
		if(member.isOrganizationAdmin())
			organization.addAsOrganizationAdmin(newMemberId);
		
		return memberService.createMember(member);
		}
		System.out.println("Failed to create new member in the organization");
		return null;
	}
	
	@PutMapping("/{memberId}/update")
	public Member update(@PathVariable(name = "memberId") int memberId, @RequestBody Member member)
	{
		int newMemberId = member.getMemberId();
		
		if(!organization.isOrganizationAdmin(memberId))
		{
			System.out.println("Only organization admins can update member details in the organization!");
		}
		else if(member.getFirstname().length()>100 || member.getLastname().length()>100)
		{
			System.out.println("The length of first name and last name must be maximum of 100 characters");
		}
		else if(organization.findMemberWithIdExists(newMemberId))
		{
		organization.updateMemberInOrganization(member);
		if(member.isOrganizationAdmin())
			organization.addAsOrganizationAdmin(newMemberId);
		return memberService.updateMember(member);
		}
		System.out.println("Failed to update the member details in the organization");
		return null;
	}
	
	@GetMapping("/{memberId}/all")
	public List<Member> findall(@PathVariable(name = "memberId") int memberId)
	{
		if(organization.findMemberWithIdExists(memberId))
		{
		return organization.getAllMembersInOrganization();
		}
		else
		{
			System.out.println("Only members in the organization can view all the member details, check the entered Id");
		}
		return null;
	}
	
	@GetMapping("/{memberId}/{targetmemberId}")
	public Member findById(@PathVariable(name = "memberId") int memberId, @PathVariable(name = "targetmemberId") int targetMemberId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in organization can view the details of other member");
		}
		else if(organization.findMemberWithIdExists(targetMemberId))
		{
		return organization.getMemberWithId(targetMemberId);
		}
		else
		{
			System.out.println("The member is not the part of organization to view details");
		}
		return null;
	}
	
	@DeleteMapping("/{memberId}/delete/{targetmemberId}")
	public String delete(@PathVariable(name = "memberId") int memberId,@PathVariable(name = "targetmemberId") int targetMemberId) 
	{
		if(!organization.isOrganizationAdmin(memberId))
		{
			return "Only organization admins can delete the members from the organization";
		}
		else if(organization.findMemberWithIdExists(targetMemberId))
		{
		organization.deleteMemberActivityFromOrganization(targetMemberId);
		memberActivityService.deleteById(targetMemberId);
		organization.deleteMemberFromOrganization(organization.getMemberWithId(targetMemberId));
		if(organization.isOrganizationAdmin(targetMemberId))
		   organization.removeAsOrganizationAdmin(targetMemberId);
			
		return memberService.deleteMemberById(targetMemberId);
		}
		return "The member with id: " + targetMemberId + " is not present in the given organization";
	}
	
	
}
