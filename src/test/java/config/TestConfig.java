package config;

import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {

    private static final Properties PROPS = new Properties();

    static {
        String env = System.getenv().getOrDefault("TEST_ENV", "test");
        load(env + ".properties");
    }

    private TestConfig() {
    }

    private static void load(String fileName) {
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                PROPS.load(is);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
    }

    public static String get(String key) {
        String envKey = key.toUpperCase().replace('.', '_');

            String fromEnv = System.getenv(envKey);
            if (fromEnv != null && !fromEnv.isBlank()) {
                return fromEnv.trim();
        }

        String fromFile = PROPS.getProperty(key);
        if (fromFile != null && !fromFile.isBlank()) {
            return fromFile;
        }

        throw new IllegalStateException("Missing config key: " + key + " (env: " + envKey + ")");
    }
}
