package com.sportus.be.place.domain.type;

public enum FacilityCategory {
    PUBLIC,
    SCHOOL,
    DISABLED,
    PRIVATE,
    ;

    public static FacilityCategory fromString(String value) {
        return FacilityCategory.valueOf(value.toUpperCase());
    }
}
