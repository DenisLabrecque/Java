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
			case "ON":
				printer.powerOn();
				break;
			case "POWEROFF":
			case "OFF":
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
			case "RESET":
				printer.display().reset();
				break;
			case "UNJAM":
				printer.paperTray().unjam();
				break;
			case "REPLACE TONER":
				printer.toner().refill();
				break;
			case "REPLACE PAPER":
				printer.paperTray().refill();
				break;
			case "REPLACE DRUM":
				printer.replaceDrum();
				break;
			case "QUIT":
			case "EXIT":
				continueLoop = false;
				break;
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
		int id = Integer.parseInt(scanner.nextLine());
		printer.cancelJob(id);
	}
	
	public static void displayHelp() {
		System.out.println("     Commands:");
		System.out.println("    Quit/Exit:  Exit program");
		System.out.println("Report/Status:  Generate printer report");
		System.out.println("   PowerOn/On:  Turn the printer on");
		System.out.println(" PowerOff/Off:  Turn the printer off");
		System.out.println("        Queue:  Report of jobs in queue");
		System.out.println("        Print:  Print first job in queue");
		System.out.println("       Cancel:  Cancel first job in queue");
		System.out.println("          Add:  Add job to print queue");
		System.out.println("        Reset:  Reset errors");
		System.out.println("        Unjam:  The user solves a paper jam");
		System.out.println("Replace Toner:  The user replaces the toner");
		System.out.println("Replace Paper:  The user replaces the paper");
		System.out.println(" Replace Drum:  The user replaces the drum");
	}
  }