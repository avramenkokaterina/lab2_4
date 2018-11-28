package main.java.avramenko.view;

import main.java.avramenko.model.Car;

import java.util.Scanner;

public class View {

    private Scanner sc;

    public void menu() {
        System.out.println("1. Generate car list");
        System.out.println("2. Create car list");
        System.out.println("3. Exit");
        printMessage(Messages.CHOOSE);
    }

    public void subMenu() {
        System.out.println("1. Get list of cars of my brand");
        System.out.println("2. Get list of cars of my model which are used for more than N years");
        System.out.println("3. Get list of cars of my year that cost for more my price");
        System.out.println("4. Return to menu");
        System.out.println("5. Exit");
        printMessage(Messages.CHOOSE);
    }

    public void printMessage(Messages messages) {
        switch (messages) {
            case CHOOSE:
                System.out.println("Choose number: ");
                break;
            case ENTER_LENGTH:
                System.out.println("How many cars do you want to add? ");
                break;
            case ENTER_ID:
                System.out.print("Identification Number: ");
                break;
            case ENTER_YEAR:
                System.out.print("Year: ");
                break;
            case ENTER_BRAND:
                System.out.print("Brand: ");
                break;
            case ENTER_COLOR:
                System.out.print("Color: ");
                break;
            case ENTER_MODEL:
                System.out.print("Model: ");
                break;
            case ENTER_PRICE:
                System.out.print("Price, $: ");
                break;
            case ENTER_NUMBER:
                System.out.print("Number: ");
                break;
            case ENTER_BRAND_LONG:
                System.out.println("Enter name of brand: ");
                break;
            case ENTER_MODEL_LONG:
                System.out.println("Enter name of model: ");
                break;
            case ENTER_PRICE_LONG:
                System.out.println("Enter price: ");
                break;
            case ENTER_YEARS_LONG:
                System.out.println("Enter number of years: ");
                break;
            case NO_RESULTS:
                System.out.println("No results.");
                break;
            default:
                break;
        }
    }

    public int readNumber() {
        int n = 0;
        Scanner sc = new Scanner(System.in);
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Not a number.");
                sc.next();
            }
            n = sc.nextInt();
        } while (n < 0);
        return n;
    }


    public String readString() {
        Scanner scan = new Scanner(System.in);
        String string = new String(scan.nextLine());
        return string;
    }

    public void printCar(Car[] cars) {
        System.out.println(" Identification Number |      Brand      |    Model    |  Year  |  Color  |  Number  |   Price, $   ");
        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
