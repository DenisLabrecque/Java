/**
 * Takes information sent to the display and prints it in console format.
 * Denis Labrecque, November 2020
 */
public class ConsoleDisplay extends DisplayAssembly {

    /**
     * Trigger a re-print of the display's information. Will not do anything if the display is not activated.
     */
    @Override
    public void refresh() {
        if(activated == false)
            return;


    }
}
