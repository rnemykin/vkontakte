package com.abudko.reseller.huuto.query.enumeration;

import static com.abudko.reseller.huuto.query.enumeration.Category.LENKKARIT;
import static com.abudko.reseller.huuto.query.enumeration.Category.TALVIHAALARI;
import static com.abudko.reseller.huuto.query.enumeration.Category.TALVIHOUSUT;
import static com.abudko.reseller.huuto.query.enumeration.Category.TALVIKENGAT;
import static com.abudko.reseller.huuto.query.enumeration.Category.TALVITAKKI;
import static com.abudko.reseller.huuto.query.enumeration.Category.VALIKAUSIHAALARI;
import static com.abudko.reseller.huuto.query.enumeration.Category.VALIKAUSIHOUSUT;
import static com.abudko.reseller.huuto.query.enumeration.Category.VALIKAUSIKENGAT;
import static com.abudko.reseller.huuto.query.enumeration.Category.VALIKAUSITAKKI;
import static com.abudko.reseller.huuto.query.enumeration.Category.VILLAHAALARI;

import java.util.Arrays;
import java.util.List;

public enum Brand {

    NO_BRAND("", "Не важно", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSITAKKI, TALVIKENGAT, VALIKAUSIKENGAT,
            VALIKAUSIHOUSUT, LENKKARIT, VALIKAUSIHAALARI, VILLAHAALARI), //
    ADIDAS("Adidas", "Adidas", LENKKARIT), //
    ALTITUDE("Altitude", "Altitude", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    ASICS("Asics", "Asics", LENKKARIT), //
    BOGI("Bogi", "Bogi", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT, VILLAHAALARI), //
    CIFAF("Ciraf", "Ciraf", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT, TALVIKENGAT, VALIKAUSIKENGAT), //
    EVEREST("Everest", "Everest", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, TALVIKENGAT, VALIKAUSIKENGAT,
            VALIKAUSITAKKI, VALIKAUSIHOUSUT, VILLAHAALARI), //
    ECCO("Ecco", "Ecco", TALVIKENGAT, VALIKAUSIKENGAT), //
    DIDRIKSONS("Didriksons", "Didriksons", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT), //
    GREENHILL("Green", "Greenhill", VILLAHAALARI), //
    JONATHAN("Jonathan", "Jonathan", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT, VILLAHAALARI), //
    HALTI("Halti", "Halti", TALVIHOUSUT, TALVITAKKI, VALIKAUSITAKKI, VALIKAUSIHOUSUT, TALVIKENGAT, VALIKAUSIKENGAT), //
    HM("H&M", "H&M", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT, VILLAHAALARI), //
    HUPPA("Huppa", "Huppa", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    ICEPEAK("Ice", "Icepeak", TALVIHAALARI, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    KANGAROOS("Kangaroos", "Kangaroos", TALVIKENGAT, VALIKAUSIKENGAT), //
    KAPPAHL("Kappahl", "Kappahl", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT, VILLAHAALARI), //
    KAVAT("Kavat", "Kavat", TALVIKENGAT, VALIKAUSIKENGAT), //
    KIVAT("Kivat", "Kivat", VILLAHAALARI), //
    KUOMA("Kuoma", "Kuoma", TALVIKENGAT, VALIKAUSIKENGAT), //
    LASSIE("Lassie", "Lassie", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT, VILLAHAALARI), //
    LASSIETEC("Lassietec", "Lassietec", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, TALVIKENGAT,
            VALIKAUSIKENGAT, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    LEGO("Lego", "Lego", TALVIHAALARI, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    LENNE("Lenne", "Lenne", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    LINDEX("Lindex", "Lindex", VILLAHAALARI), //
    LUHTA("Luhta", "Luhta", TALVIHAALARI, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    MOTION("Motion", "Motion's", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT, VILLAHAALARI), //
    MYNIMO("Mynimo", "Mynimo", TALVIHAALARI, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    MOLO("Molo", "Molo Kids", TALVIHAALARI, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    NAMEIT("Name", "Name It", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT,
            TALVIKENGAT, VALIKAUSIKENGAT, VILLAHAALARI), //
    NIKE("Nike", "Nike", LENKKARIT), //
    PEUHU("Peuhu", "Peuhu", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    POLARN("Polarn", "Polarn o Pyret", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT), //
    PUMA("Puma", "Puma", LENKKARIT), //
    RACOON("Racoon", "Racoon", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    REIMA("Reima", "Reima", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, TALVIKENGAT, VALIKAUSIKENGAT,
            VALIKAUSITAKKI, VALIKAUSIHOUSUT, VILLAHAALARI), //
    REIMATEC("Reimatec", "Reimatec", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, TALVIKENGAT,
            VALIKAUSIKENGAT, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    REMU("Remu", "Travalle Remu", TALVIHAALARI, TALVITAKKI, TALVIHOUSUT, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT), //
    RUKKA("Rukka", "Rukka", TALVITAKKI, VALIKAUSITAKKI, VALIKAUSIHOUSUT), //
    SUPERFIT("Superfit", "Superfit", TALVIKENGAT, VALIKAUSIKENGAT), //
    TICKET("Ticket", "Ticket to Heaven", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT, VILLAHAALARI), //
    TIMBERLAND("Timberland", "Timberland", TALVIKENGAT, VALIKAUSIKENGAT), //
    TUTTA("Tutta", "Tutta", VILLAHAALARI), //
    TRAVALLE("Travalle", "Travalle", TALVIHAALARI, TALVIHOUSUT, TALVITAKKI, VALIKAUSIHAALARI, VALIKAUSITAKKI,
            VALIKAUSIHOUSUT), //
    VIKING("Viking", "Viking", LENKKARIT, TALVIKENGAT, VALIKAUSIKENGAT), //
    VINCENT("Vincent", "Vincent", TALVIKENGAT, VALIKAUSIKENGAT); //

    private String parseName;

    private String fullName;

    private Category[] categories;

    private static final List<Character> END_CHARS = Arrays.asList(' ', '.', ',');

    private Brand(String parseName, String fullName, Category... categories) {
        this.parseName = parseName;
        this.fullName = fullName;
        this.categories = categories;
    }

    public String getParseName() {
        return parseName;
    }

    public String getFullName() {
        return fullName;
    }

    public Category[] getCategories() {
        return categories;
    }

    public static Brand getBrandFrom(String string) {
        Brand[] brands = Brand.values();
        for (Brand brand : brands) {
            if (Brand.NO_BRAND.equals(brand)) {
                continue;
            }
            if (brandStrictlyMatches(string, brand)) {
                return brand;
            }
        }

        return null;
    }

    private static boolean brandStrictlyMatches(String string, Brand brand) {
        String brandStr = brand.getParseName().toLowerCase();
        int index = string.toLowerCase().indexOf(brand.getParseName().toLowerCase());
        if (index >= 0) {
            if (index + brandStr.length() < string.length()) {
                char c = string.charAt(index + brandStr.length());
                return END_CHARS.contains(c);
            }
            return true;
        }

        return false;
    }
}
