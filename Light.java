/**
 * LED used to indicate the status of the printer.
 */
public class Light {
    private static final float FLASH_SECONDS = 0.8f;

    public enum Color {
        RED, YELLOW, GREEN
    }
    public enum Pattern {
        SOLID, FLASHING, OFF
    }

    private Color color;
    private Pattern pattern;

    /**
     * Creates a solid green light.
     */
    public Light() {
        color = Color.GREEN;
        pattern = Pattern.SOLID;
    }

    /**
     * Change the light's pattern, or turn it off.
     * @param pattern On/off pattern.
     */
    public void switchTo(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * @return Whether the light is currently lit. This will change based on the pattern.
     */
    public boolean isLit() {
        return true;
    }

    /**
     * @return Get the light's current color.
     */
    public Color color() {
        return color;
    }
}
