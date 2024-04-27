package shop.bookbom.shop.domain.member.repository;

import java.util.Optional;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;

public interface MemberRepositoryCustom {
    Optional<MemberInfoResponse> findMemberInfo(Long id);
}
