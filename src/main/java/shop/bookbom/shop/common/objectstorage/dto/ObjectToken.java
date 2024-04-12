package shop.bookbom.shop.common.objectstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectToken {
    private Access access;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Access {
        private TokenInfo token;
    }
}
