package avramenko.filesWorks;

import avramenko.model.Car;
import avramenko.model.Model;

import java.io.*;

public class WorkerWithSerialization implements WorkerWithFiles {
    public static final String FILE_NAME = "D:\\My Documents\\универ\\3 курс\\Java\\labs\\lab2_2\\src\\main\\java\\avramenko\\data\\dataSerialization.ser";

    @Override
    public boolean readFromFile(Model model) throws IOException {
        Car[] cars;
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);

            cars = (Car[]) objectInputStream.readObject();
            model.addCars(cars);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Something Wrong. Try again.");
            return false;
        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        }
    }

    @Override
    public boolean writeToFile(Model model) throws IOException {
        Car[] cars = model.getCars();
        ObjectOutputStream objectOutputStream = null;
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cars);
            return true;
        } catch(FileNotFoundException ex) {
            System.out.println("File not found.");
            return false;
        } finally{
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }
}
