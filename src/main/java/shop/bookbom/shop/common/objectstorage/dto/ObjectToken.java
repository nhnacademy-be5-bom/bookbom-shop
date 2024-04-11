package shop.bookbom.shop.common.objectstorage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ObjectToken {
    private Access access;

    @Getter
    @NoArgsConstructor
    public static class Access {
        private TokenInfo token;
    }
}
