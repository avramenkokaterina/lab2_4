package avramenko.model;

import java.io.Serializable;

public class Car implements Serializable {

    private String identificationNumber;
    private String carBrand;
    private String carModel;
    private int year;
    private String carColor;
    private String carNumber;
    private int carPrice;

    public Car(){

    }

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

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String str1 = String.format("%22s", identificationNumber);
        String str2 = String.format("%19s", carBrand);
        String str3 = String.format("%14s", carModel);
        String str4 = String.format("%9d", year);
        String str5 = String.format("%10s", carColor);
        String str6 = String.format("%11s", carNumber);
        String str7 = String.format("%14d", carPrice);
        return str1 + str2 + str3 + str4 + str5 + str6 + str7;
    }

    public String toFileString(){
        String str1 = "Identification number: " + identificationNumber;
        String str2 = "Brand: " + carBrand;
        String str3 = "Model: " + carModel;
        String str4 = "Year: "+ year;
        String str5 = "Color: "+ carColor;
        String str6 = "Number: "+ carNumber;
        String str7 = "Price: "+ carPrice;
        return str1 + str2 + str3 + str4 + str5 + str6 + str7;
    }
}
