package com.icia.mbboard.service;

import com.icia.mbboard.dto.MemberDTO;
import com.icia.mbboard.dto.MemberProfileFileDTO;
import com.icia.mbboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    public void saveMember(MemberDTO memberDTO) throws IOException {
//        int resultsave;
        System.out.println("컨트롤러에서 넘어왔짜 = " + memberDTO);

        if(memberDTO.getMemberProfileFile().get(0).isEmpty()){
            //파일없음
            System.out.println("파일없음");
            memberDTO.setMemberProfile(0);
            memberRepository.saveMember(memberDTO);

        }else {
            // 파일 있음.
            /*
                1. 파일첨부 여부 값 1로 세팅하고 DB에 제목 등 내용 board_table에 저장 처리
                2. 파일의 이름을 가져오고 DTO 필드값에 세팅
                3. 저장용 파일 이름 만들고 DTO 필드값에 세팅
                4. 로컬에 파일 저장
                5. board_file_table에 저장 처리
             */
            System.out.println("프로필파일 있음");
            memberDTO.setMemberProfile(1);
            MemberDTO dto = memberRepository.saveMember(memberDTO);
            for(MultipartFile memberprofileFile : memberDTO.getMemberProfileFile()){
                String profileFileName = memberprofileFile.getOriginalFilename();
                System.out.println("profileFileName = " + profileFileName);
                //저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                System.out.println(UUID.randomUUID().toString());
                String storedFileName = System.currentTimeMillis()+"-"+profileFileName;
                MemberProfileFileDTO memberProfileFileDTO = new MemberProfileFileDTO();
                memberProfileFileDTO.setProfileFileName(profileFileName);
                memberProfileFileDTO.setProfileStoredFileName(storedFileName);
                memberProfileFileDTO.setMemberId(dto.getId());
                //로컬에 파일 저장
                //저장할 경로 설정 (저장할폴더+저장할이름)
                String savePath = "D:\\springframework_img\\"+storedFileName;
                //저장처리
                memberprofileFile.transferTo(new File(savePath));
                memberRepository.saveprofileFile(memberProfileFileDTO);
            }
        }



        return ;
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        System.out.println("컨트롤러에서 받은거야 = " + memberEmail);
        MemberDTO memberDTO =memberRepository.findByMemberEmail(memberEmail);
        System.out.println("레파지에서 받아온거야 = " + memberDTO);
       return memberDTO;
    }
}
