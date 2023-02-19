package com.perscholas.voyaging.model;

public enum RoomCategory {
    KING_GUEST_ROOM("King Guest Room","Enjoy your stay in this stylish room with one king bed. Stay connected with complimentary WiFi or unwind and watch the 48-inch HDTV. Start your day with a fresh cup of coffee from the espresso machine."),
    TWIN_GUEST_ROOM("Twin Guest Room","Enjoy your stay in this stylish room with twin beds. Stay connected with complimentary WiFi or unwind and watch the 48-inch HDTV. Start your day with a fresh cup of coffee from the espresso machine."),
    KING_EXECUTIVE_ROOM("King Executive Room","Upgrade to this king executive room and enjoy access to the Executive Lounge with a range of benefits including complimentary breakfast and refreshments. Stay connected with complimentary WiFi or unwind and watch the 48-inch HDTV."),
    TWIN_EXECUTIVE_ROOM("Twin Executive Room","Upgrade to this twin executive room and enjoy access to the Executive Lounge with a range of benefits including complimentary breakfast and refreshments. Stay connected with complimentary WiFi or unwind and watch the 48-inch HDTV.");
    private final String category;
    private final String description;
    RoomCategory(String category, String description){
        this.category= category;
        this.description = description;
    }

    public String getCategory(){
        return category;
    }
    public String getDescription(){
        return description;
    }
}