package avramenko.controller;

import avramenko.filesWorks.*;
import avramenko.model.*;
import avramenko.validators.*;
import avramenko.view.*;

import java.io.IOException;

import org.apache.log4j.*;

public class Controller {

    private static final Logger log = Logger.getLogger(Controller.class);

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
        log.info("[System] Start working.");
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
                    log.info("[System] Stop working.");
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
        log.info("[Action] Show main menu.");
        view.menu();
        log.info("[User menu interaction] Get full list of main menu.");
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkMenuChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkMenuChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid input.");
                choice = view.readNumber();
            }
        } while (!validator.checkMenuChoice(choice).isEmpty());
        log.info("[User menu interaction] Users choice is " + choice + ".");
        return convertChoiceToConstant(choice);
    }

    private Activities generateCarList(View view, Model model) {
        log.info("[Action] Generate car list.");
        length = checkLengthInput(view);
        log.info("[Model changes] Generating list of cars with length " + length + ".");
        view.printCar(model.createCars(length));
        log.info("[Storage] List of cars with length " + length + " generated successful.");
        return Activities.SHOW_SUBMENU;
    }

    private Activities showSaveMenu(View view) {
        log.info("[Action] Show save menu.");
        view.menuSave();
        log.info("[User menu interaction] Get full list of save menu.");
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkSaveChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkSaveChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid input.");
                choice = view.readNumber();
            }
        } while (!validator.checkSaveChoice(choice).isEmpty());
        log.info("[User menu interaction] Users choice is " + choice + ".");
        return convertChoiceToConstant(choice + 6);
    }

    private Activities createCarList(View view, Model model) {
        log.info("[Action] Create car list.");
        boolean isSuccessful = false;

        length = checkLengthInput(view);
        for (int i = 0; i < length; i++) {
            identificationNumber = checkIDCar(view);
            view.printMessage(Messages.ENTER_BRAND);
            brand = view.readString();
            log.info("[Input] Brand entered correctly. Brand = " + brand + ".");
            view.printMessage(Messages.ENTER_MODEL);
            carModel = view.readString();
            log.info("[Input] Car Model entered correctly. Car Model = " + carModel + ".");
            year = checkYearInput(view);
            view.printMessage(Messages.ENTER_COLOR);
            carColor = view.readString();
            log.info("[Input] Car color entered correctly. Car color = " + carColor + ".");
            carNumber = checkCarNumber(view);
            view.printMessage(Messages.ENTER_PRICE);
            price = view.readNumber();
            log.info("[Input] Car price entered correctly. Car price = " + price + ".");
            Car car = new Car(identificationNumber, brand, carModel, year, carColor, carNumber, price);
            log.info("[Storage] Car created successfully.");
            isSuccessful = model.addCars(car);
            log.info("[Storage] Car added successfully.");
        }
        view.printCar(model.getCars());
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            log.info("[Storage] Car list created successfully.");
            log.info("[Output] Car list output successfully.");
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            log.error("[Error] Creation error.");
        }
        return Activities.SHOW_SAVE_MENU;
    }

    private Activities txtFileRead(View view, Model model) throws IOException {
        log.info("[Action] Read from TXT file.");
        boolean isSuccessful;
        workerWithStringFile = new WorkerWithStringFile();
        isSuccessful = workerWithStringFile.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities jsonFileRead(View view, Model model) throws IOException {
        log.info("[Action] Read from JSON file.");
        boolean isSuccessful;
        workerWithJSONFiles = new WorkerWithJSONFiles();
        isSuccessful = workerWithJSONFiles.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities serializationFileRead(View view, Model model) throws IOException {
        log.info("[Action] Read from serializable file.");
        boolean isSuccessful;
        workerWithSerialization = new WorkerWithSerialization();
        isSuccessful = workerWithSerialization.readFromFile(model);
        return isSuccessfulChecker(model, view, isSuccessful);
    }

    private Activities isSuccessfulChecker(Model model, View view, boolean isSuccessful) {
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
            log.info("[Checking] Reading from file is successful.");
            return Activities.SHOW_SUBMENU;
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            log.error("[Error] Reading from file is failed.");
            return Activities.SHOW_MAIN_MENU;
        }
    }

    private Activities txtFileWrite(View view, Model model) throws IOException {
        log.info("[Action] Write to TXT file");
        boolean isSuccessful;
        workerWithStringFile = new WorkerWithStringFile();
        isSuccessful = workerWithStringFile.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
            log.info("[Checking] Writing to file is successful.");
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            log.error("[Error] Writing to file is failed.");
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities jsonFileWrite(View view, Model model) throws IOException {
        log.info("[Action] Write to JSON file");
        boolean isSuccessful;
        workerWithJSONFiles = new WorkerWithJSONFiles();
        isSuccessful = workerWithJSONFiles.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
            log.info("[Checking] Writing to file is successful.");
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            log.error("[Error] Writing to file is failed.");
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities serializationFileWrite(View view, Model model) throws IOException {
        log.info("[Action] Write to Serializable file");
        boolean isSuccessful;
        workerWithSerialization = new WorkerWithSerialization();
        isSuccessful = workerWithSerialization.writeToFile(model);
        if (isSuccessful) {
            view.printMessage(Messages.SUCCESSFUL);
            view.printCar(model.getCars());
            log.info("[Checking] Writing to file is successful.");
        } else {
            view.printMessage(Messages.SOMETHING_WRONG);
            log.error("[Error] Writing to file is failed.");
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities showSubMenu(View view) {
        log.info("[Action] Show submenu.");
        view.subMenu();
        log.info("[User menu interaction] Get full list of submenu.");
        choice = view.readNumber();
        do {
            try {
                if (!validator.checkSubMenuChoice(choice).isEmpty()) {
                    throw new InvalidInputException(validator.checkSubMenuChoice(choice));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid input.");
                choice = view.readNumber();
            }
        } while (!validator.checkSubMenuChoice(choice).isEmpty());
        log.info("[User menu interaction] Users choice is " + choice + ".");
        return convertChoiceToConstant(choice + 10);
    }

    private Activities getListOfCarsBrand(View view, Model model) {
        log.info("[Action] Get car list with brand.");
        view.printMessage(Messages.ENTER_BRAND_LONG);
        brand = view.readString();
        log.info("[Input] Brand entered correctly. Brand = " + brand + ".");
        result = model.getListOfCarsBrand(brand);
        log.info("[Storage] Get list of cars of brand " + brand + " successfully. List contains " + result.length + " cars.");
        log.info("[Output] Car list output successfully.");
        view.printCar(result);
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities getListOfCarsYears(View view, Model model) {
        log.info("[Action] Get car list with number of year.");
        view.printMessage(Messages.ENTER_MODEL_LONG);
        carModel = view.readString();
        log.info("[Input] Car model entered correctly. Car model = " + carModel + ".");
        view.printMessage(Messages.ENTER_YEARS_LONG);
        year = checkYearInput(view);
        log.info("[Input] Year entered correctly. Year = " + year + ".");
        result = model.getListOfCarsYears(carModel, year);
        log.info("[Storage] Get list of cars, which used " + year + "years successfully. List contains " + result.length + " cars.");
        log.info("[Output] Car list output successfully.");
        view.printCar(result);
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private Activities getListOfCarsPrice(View view, Model model) {
        log.info("[Action] Get car list with entered price.");
        view.printMessage(Messages.ENTER_YEARS_LONG);
        year = checkYearInput(view);
        log.info("[Input] Year entered correctly. Year = " + year + ".");
        view.printMessage(Messages.ENTER_PRICE_LONG);
        do {
            price = view.readNumber();
        } while (price > 1000000001);
        log.info("[Input] Price entered correctly. Price = " + price + ".");
        result = model.getListOfCarsPrice(year, price);
        view.printCar(result);
        log.info("[Storage] Get list of cars, which used " + year + "years successfully. List contains " + result.length + " cars.");
        log.info("[Output] Car list output successfully.");
        if (result.length == 0) {
            view.printMessage(Messages.NO_RESULTS);
        }
        return Activities.SHOW_SUBMENU;
    }

    private int checkLengthInput(View view) {
        log.info("[Action] Check length Input.");
        view.printMessage(Messages.ENTER_LENGTH);
        length = view.readNumber();
        log.info("[Input] Length entered. Length = " + length + ".");
        do {
            try {
                if (!validator.checkLength(length).isEmpty()) {
                    throw new InvalidInputException(validator.checkLength(length));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid length.");
                length = view.readNumber();
            }
        } while (!validator.checkLength(length).isEmpty());
        log.info("[Input] Correct length.");
        return length;
    }

    private int checkYearInput(View view) {
        log.info("[Action] Check year Input.");
        view.printMessage(Messages.ENTER_YEAR);
        year = view.readNumber();
        log.info("[Input] Year entered. Year = " + year + ".");
        do {
            try {
                if (!validator.checkYear(year).isEmpty()) {
                    throw new InvalidInputException(validator.checkYear(year));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid year.");
                year = view.readNumber();
            }
        } while (!validator.checkYear(year).isEmpty());
        log.info("[Input] Correct year.");
        return year;
    }

    private String checkIDCar(View view) {
        log.info("[Action] Check Identification number of car Input.");
        view.printMessage(Messages.ENTER_ID);
        identificationNumber = view.readString();
        log.info("[Input] Identification number of car entered. ID = " + identificationNumber + ".");
        do {
            try {
                if (!validator.checkIdentificationNumber(identificationNumber).isEmpty()) {
                    throw new InvalidInputException(validator.checkIdentificationNumber(identificationNumber));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid identification number.");
                identificationNumber = view.readString();
            }
        } while (!validator.checkIdentificationNumber(identificationNumber).isEmpty());
        log.info("[Input] Correct identification number.");
        return identificationNumber;
    }

    private String checkCarNumber(View view) {
        log.info("[Action] Check Car Number Input.");
        view.printMessage(Messages.ENTER_NUMBER);
        carNumber = view.readString();
        log.info("[Input] Car Number entered. Number = " + carNumber + ".");
        do {
            try {
                if (!validator.checkCarNumber(carNumber).isEmpty()) {
                    throw new InvalidInputException(validator.checkCarNumber(carNumber));
                }
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
                log.error("[Error] Invalid car number.");
                carNumber = view.readString();
            }
        } while (!validator.checkCarNumber(carNumber).isEmpty());
        log.info("[Input] Correct car number.");
        return carNumber;
    }

}