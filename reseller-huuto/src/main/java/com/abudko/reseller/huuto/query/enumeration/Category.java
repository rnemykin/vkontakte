package com.abudko.reseller.huuto.query.enumeration;

public enum Category {

    TALVIHAALARI("talvihaalari%20OR%20toppahaalari%20OR%20talvipuku%20OR%20toppapuku", null, "Зим. комбинезоны",
            "Зимняя одежда", new String[] { "732", "735", "744", "748", "712", "713", "714" }),

    TALVIHOUSUT("talvihousut%20OR%20toppahousut", null, "Зим. штаны", "Зимняя одежда", new String[] { "732", "735", "744",
            "748", "712", "713", "714" }),

    TALVITAKKI("talvitakki%20OR%20toppatakki", null, "Зим. куртки", "Зимняя одежда", new String[] { "732", "735", "744",
            "748", "712", "713", "714" }),

    VALIKAUSIHAALARI("välikausihaalari%20OR%20välikausipuku", "vÃ¤likausihaalari%20OR%20vÃ¤likausipuku", "Деми комбинезоны", "Деми одежда", new String[] {
            "732", "735", "744", "748", "712", "713", "714" }),

    VALIKAUSIHOUSUT("välikausihousut", "vÃ¤likausihousut", "Деми штаны", "Деми одежда", new String[] { "732", "735", "744", "748", "712",
            "713", "714" }),

    VALIKAUSITAKKI("välikausitakki", "vÃ¤likausitakki", "Деми куртки", "Деми одежда", new String[] { "732", "735", "744", "748", "712",
            "713", "714" }),

    VILLAHAALARI("villahaalari%20OR%20fleecehaalari%20OR%20villapuku%20OR%20fleecepuku", null, "Поддевы", "Поддевы",
            new String[] { "732", "735", "744", "748", "712", "713", "714" }),

    SADEHAALARI(
            "sadehaalari%20OR%20kurahaalari%20OR%20sadehousut%20OR%20kurahousut%20OR%20sadetakki%20OR%20kuratakki%20OR%20kurapuku%20OR%20sadepuku",
            null, "Прорезиненная одежда", "Прорезиненная одежда", new String[] { "732", "735", "744", "748", "712",
                    "713", "714" }),

    TALVIKENGAT("talvikengät", "talvikengÃ¤t", "Зим. обувь", "Зимняя обувь", new String[] { "728", "736" }),

    VALIKAUSIKENGAT("välikausikengät", "vÃ¤likausikengÃ¤t", "Деми обувь", "Деми обувь", new String[] { "728", "736" }),

    LENKKARIT("lenkkarit", null, "Кроссовки", "Кроссовки", new String[] { "729", "737" });

    private final String valueHtml;

    private final String valueAtom;

    private final String[] id;

    private final String label;

    private final String commonLabel;

    private Category(String valueHtml, String valueAtom, String label, String commonLabel, String[] id) {
        this.valueHtml = valueHtml;
        this.valueAtom = valueAtom;
        this.label = label;
        this.commonLabel = commonLabel;
        this.id = id;
    }

    public String getValueHtml() {
        return this.valueHtml == null ? this.valueAtom : this.valueHtml;
    }

    public String getValueAtom() {
        return this.valueAtom == null ? this.valueHtml : this.valueAtom;
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
