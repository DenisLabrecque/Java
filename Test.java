import java.util.Scanner;

/**
 * Test the creation and execution of the laser printer homework.
 */
public class Test {
  public static void main(String[] args) {
	
	boolean continueLoop = true;
    LaserPrinter printer = new LaserPrinter();
	Scanner input = new Scanner(System.in);
	
	while(continueLoop) {
		if(printer.isOn() == false)
			System.out.println("[Printer is off.]");

		System.out.print("\nEnter command (?=help): ");
		String userResponse = input.nextLine().toUpperCase();
		
		switch(userResponse) {
			case "?":  
				displayHelp();
				break;
			case "REPORT":
				displayReport(printer);
				break;
			case "POWERON":
				printer.powerOn();
				break;
			case "POWEROFF":
				printer.powerOff();
				break;
			case "QUEUE":
				reportQueue(printer);
				break;
			case "PRINT":
				printJob(printer);
				break;
			case "CANCEL":
				cancelJob(printer);
				break;
			case "ADD":
				addJob(input, printer);
				break;
			case "QUIT":
			case "EXIT":
				continueLoop = false;
			default:
				System.out.println("Command " + userResponse.toUpperCase() + " not found.");
		}
	}
  }
	
	public static void displayReport(LaserPrinter o) {
		o.reportStatus();
	}
	
	public static void reportQueue(LaserPrinter o) {
		//o.reportQueue();
	}
	
	public static void printJob(LaserPrinter o) {
		//o.printJob();
	}
	
	public static void cancelJob(LaserPrinter o) {
		//o.cancelJob();
	}
	
	public static void addJob(Scanner s, LaserPrinter o) {
		//System.out.print("Job Name to add: ");
		//String name = s.nextLine().toUpperCase();
		//System.out.print("Job page count: ");
		//String count = s.nextLine();
		//int pageCount = Integer.parseInt(count);
		//o.addJob(name, pageCount);
	}
	
	public static void displayHelp() {
		System.out.println("Commands:");
		System.out.println("  Quit/Exit:  Exit program");
		System.out.println("     Report:  Generate printer report");
		System.out.println("    PowerOn:  Turn the printer on");
		System.out.println("   PowerOff:  Turn the printer off");
		System.out.println("      Queue:  Report of jobs in queue");
		System.out.println("      Print:  Print first job in queue");
		System.out.println("     Cancel:  Cancel first job in queue");
		System.out.println("        Add:  Add job to print queue");
	}
  }