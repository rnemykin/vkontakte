package com.abudko.reseller.huuto.query.enumeration;



public enum Category {

    TALVIHAALARI("talvihaalari%20OR%20toppahaalari", "Зим. комбинезоны", null),
    
    TALVIHOUSUT("talvihousut%20OR%20toppahousut", "Зим. штаны", null),
    
    TALVITAKKI("talvitakki%20OR%20toppatakki", "Зим. куртки", null),

    VALIKAUSIHAALARI("vÃ¤likausihaalari", "Деми комбинезоны", null),

    VALIKAUSIHOUSUT("vÃ¤likausihousut", "Деми штаны", null),
    
    VALIKAUSITAKKI("vÃ¤likausitakki", "Деми куртки", null),
    
    VILLAHAALARI("villahaalari%20OR%20fleecehaalari", "Поддевы", null),

    TALVIKENGAT("talvikengÃ¤t", "Зим. обувь", new String[] {"728","736"}),
    
    VALIKAUSIKENGAT("vÃ¤likausikengÃ¤t", "Деми обувь", new String[] {"728","736"}),
    
    LENKKARIT("lenkkarit", "Кроссовки", new String[] {"729","737"});

    private String value;

    private String[] id;

    private String label;

    private Category(String value, String label, String[] id) {
        this.value = value;
        this.label = label;
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getId() {
        return this.id;
    }
}
