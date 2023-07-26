import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=" + API_KEY;

    public static void main(String[] args) {
        runWeatherApp();
    }

    private static void runWeatherApp() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int choice;
            do {
                displayMenu();
                choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        getWeatherData(br);
                        break;
                    case 2:
                        getWindSpeed(br);
                        break;
                    case 3:
                        getPressure(br);
                        break;
                    case 0:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
    }

    private static void getWeatherData(BufferedReader br) throws IOException {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = br.readLine();
        String url = BASE_URL;

        JSONObject jsonObject = getJsonObjectFromAPI(url);
        if (jsonObject != null) {
            JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject weatherData = list.getJSONObject(i);
                if (weatherData.getString("dt_txt").contains(date)) {
                    JSONObject main = weatherData.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    System.out.println("Temperature on " + date + ": " + temp + "°C");
                    return;
                }
            }
            System.out.println("No data available for the given date.");
        }
    }
    private static void getWindSpeed(BufferedReader br) throws IOException {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = br.readLine();
        String url = BASE_URL;

        JSONObject jsonObject = getJsonObjectFromAPI(url);
        if (jsonObject != null) {
            JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject weatherData = list.getJSONObject(i);
                if (weatherData.getString("dt_txt").contains(date)) {
                    JSONObject wind = weatherData.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");
                    System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
                    return;
                }
            }
            System.out.println("No data available for the given date.");
        }
    }

    private static void getPressure(BufferedReader br) throws IOException {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = br.readLine();
        String url = BASE_URL;

        JSONObject jsonObject = getJsonObjectFromAPI(url);
        if (jsonObject != null)  {
            JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject weatherData = list.getJSONObject(i);
                if (weatherData.getString("dt_txt").contains(date)) {
                    JSONObject main = weatherData.getJSONObject("main");
                    double pressure = main.getDouble("pressure");
                    System.out.println("Pressure on " + date + ": " + pressure + " hPa");
                    return;
                }
            }
            System.out.println("No data available for the given date.");
        }
    }

    private static JSONObject getJsonObjectFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return JSONObject(response.toString());
           
      } else {
            System.out.println("Failed to fetch data from the API. Response Code: " + responseCode);
            return null;
        }
    }

	private static JSONObject JSONObject(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}

