package shop.bookbom.shop.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.rank.entity.Rank;

@Getter
public class MemberRankResponse {
    private String nickname;
    private String userrank;
    private List<Rank> ranks;

    @Builder
    @QueryProjection
    public MemberRankResponse(String nickname, String userrank, List<Rank> ranks) {
        this.nickname = nickname;
        this.userrank = userrank;
        this.ranks = ranks;
    }
}
