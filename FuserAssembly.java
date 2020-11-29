import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.concurrent.TimeUnit;

public class FuserAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int TEMP_MAX = 450;   //maximum heat of 451 (honestly thought it was a boring book)
	private static final int TEMP_MIN  = 240;  //minimum heat of 240
	private static final int TEMP_INCREASE= 10;//increments of 25 degrees

	private int targetTemp; //fuser requested temperature
	private int currentTemp;
	AssemblyException exception = null;
	LaserPrinter printer;
	/**
	 * Constructor.
	 * @param printer Reference back to the printer this fuser assembly is part of.
	 */
	public FuserAssembly(LaserPrinter printer) {
		super();
		this.printer = printer;
		targetTemp = TEMP_MIN;
		currentTemp = 60;
	}
	
	
	@Override
    public void activate() throws AssemblyException {
		if(exception != null)
			throw exception;
		increase();
		activated = true;
		
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;
	}

	@Override
	public void setValue(int targetTemp) {
		if(targetTemp > TEMP_MAX)
			targetTemp = TEMP_MAX;
		else if (targetTemp < TEMP_MIN)
			targetTemp = TEMP_MIN;
		this.targetTemp = targetTemp;
		increase();
	}

	@Override
	public int getValue() {
		return currentTemp;
	}

	
	public boolean isActive() {
		return activated;
	}


	public int increase() {
		printer.push("Heating up fuser.");
		int sign;
		if (currentTemp < targetTemp)
			sign = 1;
		else 
			sign = -1;
		while (currentTemp < targetTemp) {
			currentTemp += TEMP_INCREASE * sign;

			Task<Void> sleeper = new Task<Void>() {
				@Override
				protected Void call() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					return null;
				}
			};
			sleeper.setOnSucceeded(event -> {
				System.out.println("Current: " + currentTemp);
				printer.push("Fuser warming up... " + currentTemp);
			});
			new Thread(sleeper).start();


			
//			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
//				@Override
//				public void handle(ActionEvent event) {
//					System.out.println("Current: " + currentTemp);
//					printer.push("Fuser warming up... " + currentTemp);
//				}
//			}));
//			timeline.play();



//			try {
//				Thread.sleep(100);
//				System.out.println("Current: " + currentTemp);
//				printer.push("Fuser warming up... " + currentTemp);
//				printer.display().refresh();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		printer.push("Fuser on.");
		return currentTemp;
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return exception;
	}
}
