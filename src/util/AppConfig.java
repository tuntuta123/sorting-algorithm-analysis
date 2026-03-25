package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class that loads and exposes application configuration from config.txt.
 * The file is read once when the class is first loaded.
 * If the file is missing, all getters fall back to hardcoded default values.
 */
public class AppConfig {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream in = new FileInputStream("Config.txt")) {
            props.load(in);
        } catch (IOException e) {
            System.out.println("Config.txt not found, using defaults");
        }
    }


    /**
     * Returns the name of the first (or only) sorting algorithm to pre-select in the menu.
     *
     * @return The algorithm name, or "Bubble Sort" if not set in config.
     */
    public static String getAlgorithm() {
        return props.getProperty("algorithm", "Bubble Sort");
    }

    /**
     * Returns the name of the second sorting algorithm to pre-select in the compare menu.
     *
     * @return The algorithm name, or "Insertion Sort" if not set in config.
     */
    public static String getAlgorithm2() {
        return props.getProperty("algorithm2", "Insertion Sort");
    }

    /**
     * Returns the data generator type to pre-select in the menu.
     *
     * @return The generator name, or "Entropy" if not set in config.
     */
    public static String getGenerator() {
        return props.getProperty("generator", "Entropy");
    }

    /**
     * Returns the entropy value used when the generator is set to "Entropy" or "Reverse Entropy".
     *
     * @return The entropy value between 0.0 and 1.0, or 0.5 if not set in config.
     */
    public static double getEntropy() {
        return Double.parseDouble(props.getProperty("entropy", "0.5"));
    }

    /**
     * Returns the initial size of the array to sort.
     *
     * @return The array size, or 50 if not set in config.
     */
    public static int getArraySize() {
        return Integer.parseInt(props.getProperty("array_size", "50"));
    }

    /**
     * Returns the initial animation speed for the visualizer.
     * Maps to the speed slider value, where higher means faster.
     *
     * @return The animation speed between 1 and 100, or 50 if not set in config.
     */
    public static int getAnimationSpeed() {
        return Integer.parseInt(props.getProperty("animation_speed", "100"));
    }
}
