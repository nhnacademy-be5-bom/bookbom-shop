package shop.bookbom.shop.domain.member.service;

import shop.bookbom.shop.domain.member.dto.request.SignUpRequest;
import shop.bookbom.shop.domain.member.dto.request.WithDrawDTO;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;

public interface MemberService {
    /**
     * 마이페이지에 출력할 회원 정보를 가져오는 메서드입니다.
     *
     * @param id 회원 id
     * @return 회원 정보
     */
    MemberInfoResponse getMemberInfo(Long id);

    /**
     * 멤버 아이디로 멤버 찾기
     *
     * @param memberId
     * @return
     */
    Member getMemberById(Long memberId);

    /**
     * 회원가입을 처리하는 메서드입니다.
     *
     * @param signUpRequest 회원가입 요청 정보
     */
    void save(SignUpRequest signUpRequest);

    void deleteMember(Long memberId, WithDrawDTO withDrawDTO);


    /**
     * 회원의 닉네임을 중복 체크하는 메서드입니다.
     * @param nickname
     * @return
     */
    boolean checkNicknameCanUse(String nickname);
}
