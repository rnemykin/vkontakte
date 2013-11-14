package com.abudko.reseller.huuto.query.enumeration;

public enum Classification {

    ALL("0"),

    NEW("1"),

    ALMOST_NEW("2"),

    GOOD("3"),

    SATISFIED("4"),

    BAD("5");

    private String conditionParam;

    private Classification(String conditionParam) {
        this.conditionParam = conditionParam;
    }

    public String getConditionParam() {
        return this.conditionParam;
    }

    public static Classification getClassification(String conditionParam) {
        Classification[] classifications = Classification.values();
        for (Classification classification : classifications) {
            if (conditionParam.equals(classification.getConditionParam())) {
                return classification;
            }
        }
        return null;
    }
}
