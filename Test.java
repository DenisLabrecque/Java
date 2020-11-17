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
		System.out.print("\nEnter command (?=help): ");
		String userResponse = input.nextLine().toUpperCase();
		
		switch(userResponse) {
			case "?":  
				displayHelp();
				break;
			case "REPORT":
			case "STATUS":
				printer.reportStatus();
				break;
			case "POWERON":
				printer.powerOn();
				break;
			case "POWEROFF":
				printer.powerOff();
				break;
			case "QUEUE":
				printer.reportQueue();
				break;
			case "PRINT":
				printer.printJob();
				break;
			case "CANCEL":
				cancelJob(input, printer);
				break;
			case "ADD":
				addJob(input, printer);
				break;
			case "UNJAM":
				break;
			case "REPLACE TONER":
				break;
			case "REPLACE PAPER":
				break;
			case "QUIT":
			case "EXIT":
				continueLoop = false;
			default:
				System.out.println("Command " + userResponse.toUpperCase() + " not found.");
		}
	}
  }
	
	public static void addJob(Scanner scanner, LaserPrinter printer) {
		System.out.print("Job Name to add: ");
		String name = scanner.nextLine().toUpperCase();
		System.out.print("Job page count: ");
		String count = scanner.nextLine();
		int pageCount = Integer.parseInt(count);
		printer.addJob(name, pageCount);
	}

	public static void cancelJob(Scanner scanner, LaserPrinter printer) {
		System.out.print("Job to cancel: ");
		String name = scanner.nextLine().toUpperCase();
		printer.cancelJob(name);
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