package shop.bookbom.shop.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {
    ACTIVE("가입"),
    SLEEP("휴면"),
    DELETED("탈퇴"),
    ;

    private String value;

    MemberStatus(String value) {
        this.value = value;
    }
}
