package com.abudko.reseller.huuto.query.enumeration;

public enum Addtime {

    NONE("", "Не важно"),

    ONE_DAY("-86400", "1 дн."),

    TWO_DAYS("-172800", "2 дн."),

    FIVE_DAYS("-432000", "5 дн."),

    ONE_WEEK("-604800", "7 дн.");

    private String value;

    private String label;

    private Addtime(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

}
