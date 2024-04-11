package shop.bookbom.shop.common.objectstorage.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class TokenInfo {
    private String id;
    private LocalDateTime expires;
    private LocalDateTime issuedAt;
}
