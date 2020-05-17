package com.example.timetomeet;

public class Event {

    protected Long id;

    protected String name;
    protected String description;
    protected String country, city, street, house, building;
    protected String time;
    protected String date;
    private String username;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return "St. " + street + " h. " + house + " b. " + building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Event() {}

    public Event(String name, String description, /*Drawable photo,*/ String country,
                                  String city, String street, String house, String building, String time, String date) {

        this.name = name;
        this.description = description;
        this.time = time;
        this.date = date;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.username = Profile.username;
    }
}
