package com.icia.mbboard.repository;

import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.dto.MemberProfileFileDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        System.out.println("디비에서 셀렉한거 = " + memberDTO);
        return memberDTO;

    }
}
