package avramenko.filesWorks;

import avramenko.model.Car;
import avramenko.model.Model;

import java.io.*;

public class WorkerWithStringFile implements WorkerWithFiles {

    public static final String FILE_NAME = "D:\\My Documents\\универ\\3 курс\\Java\\labs\\lab2_2\\src\\main\\java\\avramenko\\data\\dataString.txt";

    @Override
    public boolean readFromFile(Model model) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            bufferedReader = new BufferedReader(fileReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                model.addCars(parser(str));
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    @Override
    public boolean writeToFile(Model model) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, false);
            bufferedWriter = new BufferedWriter(fileWriter);

            Car[] cars = model.getCars();

            for (Car car : cars){
                bufferedWriter.write(car.toFileString());
                bufferedWriter.newLine();
            }

            return true;
        }catch (FileNotFoundException ex){
            System.out.println("File not found.");
            return false;
        }
        finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    private Car parser(String str) {
        Car car = new Car();
        String[] cars = str.split(", ");
        car.setIdentificationNumber(cars[0]);
        car.setCarBrand(cars[1]);
        car.setCarModel(cars[2]);
        car.setYear(Integer.parseInt(cars[3]));
        car.setCarColor(cars[4]);
        car.setCarNumber(cars[5]);
        car.setCarPrice(Integer.parseInt(cars[6]));
        return car;
    }
}
