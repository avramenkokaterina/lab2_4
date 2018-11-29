package avramenko.filesWorks;

import avramenko.model.Car;
import avramenko.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerWithJSONFiles implements WorkerWithFiles {

    public static final String FILE_NAME = "D:\\My Documents\\универ\\3 курс\\Java\\labs\\lab2_2\\src\\main\\java\\avramenko\\data\\dataJSON.json";
    @Override
    public boolean readFromFile(Model model) throws IOException {
        BufferedReader bufferedReader = null;
        try (FileReader fileReader = new FileReader(FILE_NAME)) {
//            FileReader fileReader = new FileReader(FILE_NAME);
            bufferedReader = new BufferedReader(fileReader);

            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                builder.append(str);
            }
            String stringJson = builder.toString();
            model.resetCars();
            model.addCars(parser(new JSONObject(stringJson)));
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (JSONException ex) {
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
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            Car[] cars = model.getCars();
            StringBuilder builder = new StringBuilder();
            builder.append("{cars: [");
            for (Car tempCar : cars) {
                builder.append(
                        "{" + "\"identificationNumber\":\"" + tempCar.getIdentificationNumber()
                                + "\",\"carBrand\": \"" + tempCar.getCarBrand() +
                                "\",\"carModel\":\"" + tempCar.getCarModel() +
                                "\",\"carYear\":" + tempCar.getYear() + ",\"carColor\":\"" + tempCar.getCarColor() +
                                "\",\"carNumber\":\"" + tempCar.getCarNumber() + "\",\"carPrice\":" + tempCar.getCarPrice() + "},"
                );
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append("]}");


            JSONObject jsonObject = new JSONObject(builder.toString());
            bufferedWriter.write(jsonObject.toString());

            return true;
        } catch (FileNotFoundException ex) {
//            System.out.println("File not found.");
            System.err.println(ex.getMessage());
            return false;
        } catch (JSONException ex) {
            System.err.println(ex.getMessage());
            return false;
        } finally {
//            if (bufferedWriter != null) {
////                bufferedWriter.close();
//            }
        }
    }

    private Car[] parser(JSONObject jsonObject) {

        Object object = jsonObject.get("cars");
        JSONArray jsonArray = (JSONArray) object;
        List<Object> list = jsonArray.toList();
        Car[] result = new Car[list.size()];
        int index = 0;
        for (Object tempObject : list) {
            Map<String, Object> map = (HashMap<String, Object>) tempObject;
            result[index] = new Car();
            result[index].setCarBrand((String) map.get("carBrand"));
            result[index].setCarColor((String) map.get("carColor"));
            result[index].setCarModel((String) map.get("carModel"));
            result[index].setCarNumber((String) map.get("carNumber"));
            result[index].setIdentificationNumber((String) map.get("identificationNumber"));
            result[index].setYear((Integer) map.get("carYear"));
            result[index].setCarPrice((Integer) map.get("carPrice"));
            index++;
        }
        return result;
    }
}
