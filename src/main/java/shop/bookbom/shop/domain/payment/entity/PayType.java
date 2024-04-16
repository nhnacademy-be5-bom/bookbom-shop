package shop.bookbom.shop.domain.payment.entity;

public enum PayType {
    CARD("카드"),
    CASH("현금"),
    POINT("포인트");
    private final String typeValue;

    PayType(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
