package shop.bookbom.shop.domain.member.repository;

import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;

public interface MemberRepositoryCustom {
    MemberInfoResponse findMemberInfo(Long id);
    Member findMemberByIdFetchRank(Long id);
}
