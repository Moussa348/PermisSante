package com.keita.permis.enums;


public enum PermitCategory {
    LITTLE_KID(10),
    KID(17),
    ADULT(40),
    SENIOR(100);

    private final int maxAge;

    PermitCategory( int maxAge){
        this.maxAge = maxAge;
    }

    public static PermitCategory determinePermitCategory(int age){
        if(age <= PermitCategory.LITTLE_KID.maxAge)
            return PermitCategory.LITTLE_KID;
        if(age <= PermitCategory.KID.maxAge)
            return PermitCategory.KID;
        if ( age <= PermitCategory.ADULT.maxAge)
            return PermitCategory.ADULT;
        return PermitCategory.SENIOR;
    }
}
