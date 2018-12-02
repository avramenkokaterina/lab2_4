package avramenko.controller;

//lab 2_4

import avramenko.filesWorks.*;
import avramenko.model.Car;
import avramenko.model.Model;
import avramenko.validators.InvalidInputException;
import avramenko.validators.Validator;
import avramenko.view.Activities;
import avramenko.view.Messages;
import avramenko.view.View;

import java.io.IOException;

public class Controller {
    private Car[] result;
    private int choice;
    private int length;
    private String brand;
    private String carModel;
    private int year = 0;
    private int price = 0;
    private String identificationNumber;
    private String carColor;
    private String carNumber;
    private Validator validator;
    private WorkerWithStringFile workerWithStringFile;
    private WorkerWithJSONFiles workerWithJSONFiles;
    private WorkerWithSerialization workerWithSerialization;

    public Controller() {
        validator = new Validator();
    }

    public void startWorking() throws IOException {
        View view = new View();
        Model model = new Model();
        Activities activities;

        activities = Activities.SHOW_MAIN_MENU;
        while (true) {
            switch (activities) {
                case SHOW_MAIN_MENU:
                    activities = showMainMenu(view);
                    break;
                case GENERATE_CAR_LIST:
                    activities = generateCarList(view, model);
                    break;
                case CREATE_CAR_LIST:
                    activities = createCarList(view, model);
                    break;
                case SHOW_SAVE_MENU:
                    activities = showSaveMenu(view);
                    break;
                case TXT_FILE_READ:
                    activities = txtFileRead(view, model);
                    break;
                case JSON_FILE_READ:
                    activities = jsonFileRead(view, model);
                    break;
                case SERIALIZATION_FILE_READ:
                    activities = serializationFileRead(view, model);
                    break;
                case TXT_FILE_WRITE:
                    activities = txtFileWrite(view, model);
                    break;
                case JSON_FILE_WRITE:
                    activities = jsonFileWrite(view, model);
                    break;
                case SERIALIZATION_FILE_WRITE:
                    activities = serializationFileWrite(view, model);
                    break;
                case SHOW_SUBMENU:
                    activities = showSubMenu(view);
                    break;
                case GET_BRAND:
                    activities = getListOfCarsBrand(view, model);
                    break;
                case GET_MODEL:
                    activities = getListOfCarsYears(view, model);
                    break;
                case GET_YEAR:
                    activities = getListOfCarsPrice(view, model);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private Activities convertChoiceToConstant(int choice) {
        switch (choice) {
            case 1:
                return Activities.GENERATE_CAR_LIST;
            case 5:
                return Activities.CREATE_CAR_LIST;
            case 2:
                return Activities.TXT_FILE_READ;
            case 3:
                return Activities.JSON_FILE_READ;
            case 4:
                return Activities.SERIALIZATION_FILE_READ;
            case 6:
                return Activities.EXIT;
            case 7:
                return Activities.TXT_FILE_WRITE;
            case 8:
                return Activities.JSON_FILE_WRITE;
            case 9:
                return Activities.SERIALIZATION_FILE_WRITE;
            case 10:
                return Activities.SHOW_SUBMENU;
            case 11:
                return Activities.GET_BRAND;
            case 12:
                return Activities.GET_MODEL;
            case 13:
                return Activities.GET_YEAR;
            case 14:
                return Activities.SHOW_MAIN_MENU;
            case 15:
                return Activities.EXIT;
        }
        return null;
    }

    private Activities showMainMenu(View view) {
        view.menu();
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkMenuChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkMenuChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                choice = view.readNumber();
            }
        } while (!validator.checkMenuChoice(choice).isEmpty());
        return convertChoiceToConstant(choice);
    }

    private Activities generateCarList(View view, Model model) {
        length = checkLengthInput(view);
        view.printCar(model.createCars(length));
        return Activities.SHOW_SUBMENU;
    }

    private Activities showSaveMenu(View view) {
        view.menuSave();
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkSaveChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkSaveChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                choice = view.readNumber();
            }
        } while (!validator.checkSaveChoice(choice).isEmpty());
        return convertChoiceToConstant(choice + 6);
    }

    private Activities createCarList(View view, Model model) {
        boolean isSuccessful = false;

        length = checkLengthInput(view);

        for (int i = 0; i < length; i++) {
            identificationNumber = checkIDCar(view);

            view.printMessage(Messages.ENTER_BRAND);
            brand = view.readString();

            view.printMessage(Messages.ENTER_MODEL);
            carModel = view.readString();

            year = checkYearInput(view);

            view.printMessage(Messages.ENTER_COLOR);
            carColor = view.readString();

            carNumber = checkCarNumber(view);

            view.printMessage(Messages.ENTER_PRICE);
            price = view.readNumber();

            Car car = new Car(identificationNumber, brand, carModel, year, carColor, carNumber, price);
            isSuccessful = model.addCars(car);
        }
        view.printCar(model.getCars());
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
        }
        return Activities.SHOW_SAVE_MENU;
    }

    private Activities txtFileRead(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithStringFile = new WorkerWithStringFile();
        isSuccessful = workerWithStringFile.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities jsonFileRead(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithJSONFiles = new WorkerWithJSONFiles();
        isSuccessful = workerWithJSONFiles.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities serializationFileRead(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithSerialization = new WorkerWithSerialization();
        isSuccessful = workerWithSerialization.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities isSuccessfulChecker (Model model, View view, boolean isSuccessful){
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
            return Activities.SHOW_SUBMENU;
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            return Activities.SHOW_MAIN_MENU;
        }
    }

    private Activities txtFileWrite(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithStringFile = new WorkerWithStringFile();
        isSuccessful = workerWithStringFile.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities jsonFileWrite(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithJSONFiles = new WorkerWithJSONFiles();
        isSuccessful = workerWithJSONFiles.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities serializationFileWrite(View view, Model model) throws IOException {
        boolean isSuccessful = false;
        workerWithSerialization = new WorkerWithSerialization();
        isSuccessful = workerWithSerialization.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities showSubMenu(View view) {
        view.subMenu();
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkSubMenuChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkSubMenuChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                choice = view.readNumber();
            }
        } while (!validator.checkSubMenuChoice(choice).isEmpty());
        return convertChoiceToConstant(choice + 10);
    }

    private Activities getListOfCarsBrand(View view, Model model) {
        view.printMessage(Messages.ENTER_BRAND_LONG);
        brand = view.readString();
        result = model.getListOfCarsBrand(brand);
        view.printCar(result);
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities getListOfCarsYears(View view, Model model) {
        view.printMessage(Messages.ENTER_MODEL_LONG);
        carModel = view.readString();

        view.printMessage(Messages.ENTER_YEARS_LONG);
        year = checkYearInput(view);

        result = model.getListOfCarsYears(carModel, year);
        view.printCar(result);
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities getListOfCarsPrice(View view, Model model) {
        view.printMessage(Messages.ENTER_YEARS_LONG);
        year = checkYearInput(view);

        view.printMessage(Messages.ENTER_PRICE_LONG);
        do {
            price = view.readNumber();
        } while (price > 1000000001);

        result = model.getListOfCarsPrice(year, price);
        view.printCar(result);
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private int checkLengthInput(View view) {
        view.printMessage(Messages.ENTER_LENGTH);
        length = view.readNumber();
        do {
            try {
                if (!validator.checkLength(length).isEmpty()) {
                    throw new InvalidInputException(validator.checkLength(length));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                length = view.readNumber();
            }
        } while (!validator.checkLength(length).isEmpty());
        return length;
    }

    private int checkYearInput(View view) {
        view.printMessage(Messages.ENTER_YEAR);
        year = view.readNumber();
        do {
            try {
                if (!validator.checkYear(year).isEmpty()) {
                    throw new InvalidInputException(validator.checkYear(year));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                year = view.readNumber();
            }
        } while (!validator.checkYear(year).isEmpty());
        return year;
    }

    private String checkIDCar(View view) {
        view.printMessage(Messages.ENTER_ID);
        identificationNumber = view.readString();
        do {
            try {
                if (!validator.checkIdentificationNumber(identificationNumber).isEmpty()) {
                    throw new InvalidInputException(validator.checkIdentificationNumber(identificationNumber));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                identificationNumber = view.readString();
            }
        } while (!validator.checkIdentificationNumber(identificationNumber).isEmpty());
        return identificationNumber;
    }

    private String checkCarNumber(View view) {
        view.printMessage(Messages.ENTER_NUMBER);
        carNumber = view.readString();
        do {
            try {
                if (!validator.checkCarNumber(carNumber).isEmpty()) {
                    throw new InvalidInputException(validator.checkCarNumber(carNumber));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                carNumber = view.readString();
            }
        } while (!validator.checkCarNumber(carNumber).isEmpty());
        return carNumber;
    }

}