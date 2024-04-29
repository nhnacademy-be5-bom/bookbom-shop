package shop.bookbom.shop.domain.member.repository;

import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;

public interface MemberRepositoryCustom {
    MemberInfoResponse findMemberInfo(Long id);
}
