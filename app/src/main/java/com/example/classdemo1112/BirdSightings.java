package com.example.classdemo1112;

public class BirdSightings {
    public String PersonEmail;
    public String ZipCode;
    public String BirdName;
    public Integer Importance;

    public BirdSightings() {
    }

    public BirdSightings(String PersonEmail, String ZipCode, String BirdName, Integer Importance){
        this.PersonEmail = PersonEmail;
        this.ZipCode = ZipCode;
        this.BirdName = BirdName;
        this.Importance = Importance;
    }
}
