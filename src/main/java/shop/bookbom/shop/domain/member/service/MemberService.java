package shop.bookbom.shop.domain.member.service;

import shop.bookbom.shop.domain.member.dto.request.SignUpRequest;
import shop.bookbom.shop.domain.member.dto.request.WithDrawDTO;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.dto.response.MemberRankResponse;

public interface MemberService {
    /**
     * 마이페이지에 출력할 회원 정보를 가져오는 메서드입니다.
     *
     * @param id 회원 id
     * @return 회원 정보
     */
    MemberInfoResponse getMemberInfo(Long id);

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

    /**
     * 회원 등급 조회페이지에 출력할 정보를 가져오는 메서드입니다.
     */
    MemberRankResponse getUserRank(Long id);
}
