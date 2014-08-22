package com.abudko.reseller.huuto.query.enumeration;

public enum Category {

    TALVIHAALARI("talvihaalari%20OR%20toppahaalari%20OR%20talvipuku%20OR%20toppapuku", "Зим. комбинезоны",
            "Зимняя одежда", new String[] { "732", "735", "744", "748", "712", "713", "714" }),

    TALVIHOUSUT("talvihousut%20OR%20toppahousut", "Зим. штаны", "Зимняя одежда", new String[] { "732", "735", "744",
            "748", "712", "713", "714" }),

    TALVITAKKI("talvitakki%20OR%20toppatakki", "Зим. куртки", "Зимняя одежда", new String[] { "732", "735", "744",
            "748", "712", "713", "714" }),

    VALIKAUSIHAALARI("vÃ¤likausihaalari%20OR%20vÃ¤likausipuku", "Деми комбинезоны", "Деми одежда", new String[] {
            "732", "735", "744", "748", "712", "713", "714" }),

    VALIKAUSIHOUSUT("vÃ¤likausihousut", "Деми штаны", "Деми одежда", new String[] { "732", "735", "744", "748", "712",
            "713", "714" }),

    VALIKAUSITAKKI("vÃ¤likausitakki", "Деми куртки", "Деми одежда", new String[] { "732", "735", "744", "748", "712",
            "713", "714" }),

    VILLAHAALARI("villahaalari%20OR%20fleecehaalari%20OR%20villapuku%20OR%20fleecepuku", "Поддевы", "Поддевы",
            new String[] { "732", "735", "744", "748", "712", "713", "714" }),

    SADEHAALARI(
            "sadehaalari%20OR%20kurahaalari%20OR%20sadehousut%20OR%20kurahousut%20OR%20sadetakki%20OR%20kuratakki%20OR%20kurapuku%20OR%20sadepuku",
            "Прорезиненная одежда", "Прорезиненная одежда", new String[] { "732", "735", "744", "748", "712", "713",
                    "714" }),

    TALVIKENGAT("talvikengÃ¤t", "Зим. обувь", "Зимняя обувь", new String[] { "728", "736" }),

    VALIKAUSIKENGAT("vÃ¤likausikengÃ¤t", "Деми обувь", "Деми обувь", new String[] { "728", "736" }),

    LENKKARIT("lenkkarit", "Кроссовки", "Кроссовки", new String[] { "729", "737" });

    private final String value;

    private final String[] id;

    private final String label;

    private final String commonLabel;

    private Category(String value, String label, String commonLabel, String[] id) {
        this.value = value;
        this.label = label;
        this.commonLabel = commonLabel;
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    public String getCommonLabel() {
        return this.commonLabel;
    }

    public String[] getId() {
        return this.id;
    }
}
