package shop.bookbom.shop.common.objectstorage.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String id;
    private LocalDateTime expires;
    private LocalDateTime issuedAt;
}
