package com.icia.mbboard.repository;

import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.dto.MemberProfileFileDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    @Autowired
    private SqlSessionTemplate sql;


    public MemberDTO saveMember(MemberDTO memberDTO) {
        System.out.println("before insert = " + memberDTO);
        sql.insert("Mb_member.mb_membersave", memberDTO);
        System.out.println(" insert 성공 = " + memberDTO);
         return memberDTO;
    }

    public void saveprofileFile(MemberProfileFileDTO memberProfileFileDTO) {
        sql.insert("Mb_member.saveProfileFile",memberProfileFileDTO);
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        System.out.println("서비스에서 받으거 = " + memberEmail);
        MemberDTO memberDTO =sql.selectOne("Mb_member.findByEmail",memberEmail);
        if (memberDTO!=null) {
            System.out.println("memberEmail 조회 성공");
        }else {
            System.out.println(memberEmail + "reposi딴 해당 이메일회원이 없습니다.");
        }
        return memberDTO;

    }

    public MemberDTO memberLogin(MemberDTO memberDTO) {
        MemberDTO dto = sql.selectOne("Mb_member.memberLogin",memberDTO);
        return dto;
    }

    public List<MemberProfileFileDTO> findFile(Long memberId) {
        List<MemberProfileFileDTO> memberProfileFileDTOList = sql.selectList("Mb_member.findFile",memberId);
        return memberProfileFileDTOList;
    }

    public MemberDTO findMemberById(Long memberId) {
        MemberDTO memberDTO = sql.selectOne("Mb_member.findMemberById",memberId);
        return memberDTO;
    }

    public List<MemberDTO> memberFindAll() {
        List<MemberDTO> memberDTOList = sql.selectList("Mb_member.memberList");
        return memberDTOList;
    }

    public void memberDelete(Long memberId) {
        System.out.println("REPOSImemberId = " + memberId);
        sql.delete("Mb_member.memberDel",memberId);
    }

    public int memberUpdate(MemberDTO memberDTO) {
        System.out.println("레파지토리까지 온memberDTO = " + memberDTO);
        int memberUpdateResult = sql.update("Mb_member.memberUpdate",memberDTO);
        return memberUpdateResult;
    }
    public Long findIdBySessionEmail(String loginEmailchk) {
        return sql.selectOne("Mb_member.findIdByEmail",loginEmailchk);
    }
}
