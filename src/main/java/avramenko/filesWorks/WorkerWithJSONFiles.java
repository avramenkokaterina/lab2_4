package avramenko.filesWorks;

import avramenko.model.Car;
import avramenko.model.Model;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class WorkerWithJSONFiles implements WorkerWithFiles{

    public static final String FILE_NAME = "D:\\My Documents\\универ\\3 курс\\Java\\labs\\lab2_2\\src\\main\\java\\avramenko\\data\\dataJSON.json";

    @Override
    public boolean readFromFile(Model model) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            bufferedReader = new BufferedReader(fileReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                model.addCars(parser(new JSONObject(str)));
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (JSONException ex){
            System.out.println("Something wrong... Try again.");
            return false;
        }
        finally {
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
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            Car[] cars = model.getCars();

            for (Car car : cars){
                JSONObject jsonObject = new JSONObject(car);
                printWriter.write(jsonObject.toString());
                printWriter.println();
            }

            return true;
        }catch (FileNotFoundException ex){
            System.out.println("File not found.");
            return false;
        } catch (JSONException ex){
            System.out.println("Something wrong... Try again.");
            return false;
        }
        finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    private Car parser(JSONObject jsonObject){
        Car car = new Car();
        try{
            car.setIdentificationNumber(jsonObject.getString("identificationNumber"));
            car.setCarBrand(jsonObject.getString("carBrand"));
            car.setCarModel(jsonObject.getString("carModel"));
            car.setYear(jsonObject.getInt("carYear"));
            car.setCarColor(jsonObject.getString("carColor"));
            car.setCarNumber(jsonObject.getString("carNumber"));
            car.setCarPrice(jsonObject.getInt("carPrice"));
        } catch (JSONException ex){
            System.out.println("Something wrong... Try again.");
        }
        return car;
    }
}
