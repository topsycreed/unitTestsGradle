package configurations;

public class TestConfig {
    private static final String BASE_URL = "https://petstore.swagger.io/v2/";

    public String getBaseUrl() {
        String baseUrl = System.getProperty("baseUrl", BASE_URL);
//        String baseUrl = System.getProperty("baseUrl");
//        if (baseUrl == null) {
//            baseUrl = BASE_URL;
//        }
        return baseUrl;
    }
}
