package com.perscholas.voyaging.model;

public enum Amenities {
    TV("55Inch flat-screen TV"),
    DRYER("Hair dryer"),
    MINI_FRIDGE("Minifridge"),
    NEWSPAPER("Daily newspaper upon request"),
    CRIB("Crib upon request");

    private final String amenityValue;
    Amenities(String amenity){
        this.amenityValue= amenity;
    }

    public String getAmenity(){
        return amenityValue;
    }

}
