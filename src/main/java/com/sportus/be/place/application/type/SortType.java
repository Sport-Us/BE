package com.sportus.be.place.application.type;

public enum SortType {
    STAR_DESC,
    DISTANCE_ASC,
    ;

    public static SortType fromString(String value) {
        for (SortType sortType : SortType.values()) {
            if (sortType.name().equalsIgnoreCase(value)) {
                return sortType;
            }
        }
        return null;
    }
}
