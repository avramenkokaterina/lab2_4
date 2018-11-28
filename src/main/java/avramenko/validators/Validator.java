package main.java.avramenko.validators;

public class Validator {

    private final static String CAR_NUMBER_REGEXP = "[A-Z]{2}[0-9]{4}[A-Z]{2}";
    private final static String ID_CAR_REGEXP = "\\w{16}";

    private final static String CHOICE_EXCEPTION_MENU = "There is no such menu item. Enter number from 1 to 3.";
    private final static String CHOICE_EXCEPTION_SUBMENU = "There is no such menu item. Enter number from 1 to 5.";
    private final static String MAX_LENGTH_EXCEPTION = "Bad length. Enter length from 1 to 10.";
    private final static String MAX_YEAR_EXCEPTION = "Enter year from 0 to 2018.";
    private final static String CAR_NUMBER_EXCEPTION = "Wrong car number. Enter car number in format 2 capital letters+4 numbers+2 capital letters.";
    private final static String ID_CAR_EXCEPTION = "Wrong identification number of car. Enter id car in format 16 symbols of letters and numbers.";

    public String checkMenuChoice(int number) {
        if (number < 1 || number > 3) {
            return CHOICE_EXCEPTION_MENU;
        }
        return "";
    }

    public String checkSubMenuChoice(int number) {
        if (number < 1 || number > 5) {
            return CHOICE_EXCEPTION_SUBMENU;
        }
        return "";
    }

    public String checkLength(int number) {
        if (number < 1 || number > 10) {
            return MAX_LENGTH_EXCEPTION;
        }
        return "";
    }

    public String checkYear(int number) {
        if (number > 2019) {
            return MAX_YEAR_EXCEPTION;
        }
        return "";
    }

    public String checkCarNumber(String carNumber) {
        if (!carNumber.matches(CAR_NUMBER_REGEXP)) {
            return CAR_NUMBER_EXCEPTION;
        }
        return "";
    }

    public String checkIdentificationNumber(String idNumber) {
        if (!idNumber.matches(ID_CAR_REGEXP)) {
            return ID_CAR_EXCEPTION;
        }
        return "";
    }
}
