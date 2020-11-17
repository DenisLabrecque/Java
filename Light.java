/**
 * LED used to indicate the printer's status.
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
     * Creates a green light that's off.
     */
    public Light() {
        color = Color.GREEN;
        pattern = Pattern.OFF;
    }

    /**
     * Create a light that's off.
     * @param color The light's color.
     */
    public Light(Color color) {
        this.color = color;
        this.pattern = Pattern.OFF;
    }

    /**
     * Change the light's pattern, or turn it off.
     * @param pattern On/off pattern.
     */
    public void setPattern(Pattern pattern) {
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

    /**
     * @param color Set the light to the stated color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return A string that describes the light's color and pattern. For example, "GREEN light, SOLID".
     */
    @Override
    public String toString() {
        return color + " light, " + pattern;
    }
}
