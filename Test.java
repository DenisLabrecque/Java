import java.util.Scanner;

public class Test {
  public static void main(String[] args) {
	
	boolean continueLoop = true;
	
    LaserPrinter lp = new LaserPrinter();
	
	Scanner input = new Scanner(System.in);
	
	while(continueLoop) {
		System.out.print("\nEnter command (?=help): ");
		String userResponse = input.nextLine().toUpperCase();
		
		switch(userResponse) {
			case "?":  
				displayHelp();
				break;
			case "REPORT":
				displayReport(lp);
				break;
			case "POWERON":
				startPrinter(lp);
				break;
			case "POWEROFF":
				shutdownPrinter(lp);
				break;
			case "QUEUE":
				reportQueue(lp);
				break;
			case "PRINT":
				printJob(lp);
				break;
			case "CANCEL":
				cancelJob(lp);
				break;
			case "ADD":
				addJob(input, lp);
				break;
			case "QUIT":
			case "EXIT":
				continueLoop = false;
		}
	}
  }

	public static void startPrinter(LaserPrinter o) {
		System.out.println("Turning on Laser Printer.");
		o.powerOn();
	}
	
	public static void displayReport(LaserPrinter o) {
		o.reportStatus();
	}
	
	public static void shutdownPrinter(LaserPrinter o) {
		System.out.println("Turning off Laser Printer.");
		o.powerOff();
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