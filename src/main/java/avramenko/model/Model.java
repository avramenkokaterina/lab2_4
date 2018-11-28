package main.java.avramenko.model;

import java.util.Arrays;

public class Model {

    private Car[] cars;
    private int length;
    private final int initialLength = 10;
    private int currentLength = 0;

    public Model() {
        length = initialLength;
        Car[] cars = new Car[length];
    }

    public boolean addCars(Car... cars) {
        if (this.cars != null && cars != null) {
            if (cars.length <= length - currentLength) {
                for (int i = 0; i < cars.length; i++) {
                    this.cars[currentLength] = cars[i];
                    currentLength++;
                }
            } else {
                this.length += cars.length;
                this.cars = Arrays.copyOf(this.cars, length);

                for (int i = 0; i < cars.length; i++) {
                    this.cars[currentLength] = cars[i];
                    currentLength++;
                }
            }
            return true;
        } else {
            if (this.cars == null && cars != null) {
                this.cars = cars;
                this.length = cars.length;
                this.currentLength = this.length;
            }
            return false;

        }
    }

    public Car[] getCars() {
        return cars;
    }

    public Car[] getListOfCarsBrand(String carBrand) {
        if (cars != null && carBrand != null) {
            Car[] result = new Car[this.currentLength];
            int counter = 0;
            for (Car car : this.cars) {
                if (car.getCarBrand().equals(carBrand)) {
                    result[counter] = car;
                    counter++;
                }
            }
            result = Arrays.copyOf(result, counter );
            return result;
        } else {
            return null;
        }
    }

    public Car[] getListOfCarsYears(String carModel, int year) {
        if (cars != null && carModel != null && year != 0) {
            Car[] result = new Car[this.currentLength];
            int counter = 0;
            for (Car car : this.cars) {
                if (car.getCarModel().equals(carModel) && (2018 - car.getYear()) > year) {
                    result[counter] = car;
                    counter++;
                }
            }
            result = Arrays.copyOf(result, counter);
            return result;
        } else {
            return null;
        }
    }

    public Car[] getListOfCarsPrice(int year, int carPrice) {
        if (cars != null && carPrice >= 0 && year != 0) {
            Car[] result = new Car[this.currentLength];
            int counter = 0;
            for (Car car : this.cars) {
                if (car.getYear() == year && car.getCarPrice() > carPrice) {
                    result[counter] = car;
                    counter++;
                }
            }
            result = Arrays.copyOf(result, counter);
            return result;
        } else {
            return null;
        }
    }

    public Car[] createCars(int N) {
        Car[] cars = new Car[10];
        cars[0] = new Car("1A2S3D4F5GhH7J8K", "Aston Martin", "DB8", 2009, "Black", "AA1234AS", 90000);
        cars[1] = new Car("1A2SH7J8K3D4F5Gh", "Lamborghini", "Gallardo", 2015, "Yellow", "BB1234AS", 120000);
        cars[2] = new Car("A21S3D4FhH75GJ8K", "Lamborghini", "Murcielago", 2013, "Black", "CC1234AS", 265000);
        cars[3] = new Car("S3D1A25GhHJ8K74F", "Mercedes-Benz", "SL 500", 2010, "Silver", "DD1234AS", 75000);
        cars[4] = new Car("4F5GhH71A2S3DJ8K", "Mercedes-Benz", "SLR", 2013, "Black", "EE1234AS", 300000);
        cars[5] = new Car("D41A2GhS3F5H7J8K", "Porsche", "911 Turbo S", 2016, "Black", "FF1234AS", 105000);
        cars[6] = new Car("D41A2hH7S3F5GJ8K", "Porsche", "Carrera GT", 2016, "White", "GG1234AS", 280000);
        cars[7] = new Car("1D4GA2S3hH7F5J8K", "Porsche", "Cayman S", 2010, "Black", "HH1234AS", 160000);
        cars[8] = new Car("5GhH7J8K1A2S3D4F", "BMW", "M3 GTR", 2012, "White", "II1234AS", 150000);
        cars[9] = new Car("J8K1A3D4F5G2ShH7", "Mitsubishi", "Lancer", 2014, "Black", "JJ1234AS", 85000);
        this.cars = cars;
        this.length = cars.length;
        this.currentLength = cars.length;
        cars = Arrays.copyOf(cars, N);
        return cars;
    }
}
