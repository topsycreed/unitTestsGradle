package configurations;

public class TestConfig {
    private static final String BASE_URL = "https://petstore.swagger.io/v2/";
    private static final String DEFAULT_API_KEY = "special-key";
    private static final String BAG_BASE_URL = "https://www.ae.com/ugp-api/bag/v1";
    private static final String AUTH_URL = "https://www.ae.com/ugp-api/auth/oauth/v4/token";
    private static final String BASE_AUTH = "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==";

    public String getBaseUrl() {
        String baseUrl = System.getProperty("baseUrl", BASE_URL);
//        String baseUrl = System.getProperty("baseUrl");
//        if (baseUrl == null) {
//            baseUrl = BASE_URL;
//        }
        return baseUrl;
    }

    public String getApiKey() {
        return System.getProperty("apiKey", DEFAULT_API_KEY);
    }

    public String getBagBaseUrl() {
        return System.getProperty("bagBaseUrl", BAG_BASE_URL);
    }

    public String getAuthUrl() {
        return System.getProperty("authUrl", AUTH_URL);
    }

    public String getBasicAuth() {
        return System.getProperty("baseAuth", BASE_AUTH);
    }
}
