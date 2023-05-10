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
//        System.out.println("서비스에서 받으거 = " + memberEmail);
        MemberDTO memberDTO =sql.selectOne("Mb_member.findByEmail",memberEmail);
        if (memberDTO!=null)
            System.out.println("reposi디비에서 찾아봤는데 이미 있음 = " + memberDTO);
        else
            System.out.println(memberEmail+"reposi좋은아이디 회원가입 진행");
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
}
