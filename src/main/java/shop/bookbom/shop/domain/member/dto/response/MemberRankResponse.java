package shop.bookbom.shop.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRankResponse {
    private String nickname;
    private String userrank;

    @Builder
    @QueryProjection
    public MemberRankResponse(String nickname, String userrank) {
        this.nickname = nickname;
        this.userrank = userrank;
    }
}
