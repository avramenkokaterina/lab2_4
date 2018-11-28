package main.java.avramenko.model;

public class Car {

    private String identificationNumber;
    private String carBrand;
    private String carModel;
    private int year;
    private String carColor;
    private String carNumber;
    private int carPrice;


    public Car(String identificationNumber, String carBrand, String carModel, int year, String carColor, String carNumber, int carPrice) {
        this.identificationNumber = identificationNumber;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.year = year;
        this.carColor = carColor;
        this.carNumber = carNumber;
        this.carPrice = carPrice;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getYear() {
        return year;
    }

    public String getCarColor() {
        return carColor;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public int getCarPrice() {
        return carPrice;
    }

    @Override
    public String toString() {
        String str1 = String.format("%22s", getIdentificationNumber());
        String str2 = String.format("%19s", getCarBrand());
        String str3 = String.format("%14s", getCarModel());
        String str4 = String.format("%9d", getYear());
        String str5 = String.format("%10s", getCarColor());
        String str6 = String.format("%11s", getCarNumber());
        String str7 = String.format("%14d", getCarPrice());
        return str1 + str2 + str3 + str4 + str5 + str6 + str7;
    }
}
