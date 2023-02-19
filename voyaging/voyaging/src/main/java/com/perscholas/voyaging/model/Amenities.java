package com.perscholas.voyaging.model;

public enum Amenities {
    TV("55Inch flat-screen TV"),
    DRYER("Hair dryer"),
    MINI_FRIDGE("Minifridge"),
    NEWSPAPER("Daily newspaper upon request"),
    CRIB("Crib upon request");

    private final String amenity;
    Amenities(String amenity){
        this.amenity= amenity;
    }

    public String getAmenity(){
        return amenity;
    }

}
