package shop.bookbom.shop.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    boolean existsByNickname(String nickname);
}
