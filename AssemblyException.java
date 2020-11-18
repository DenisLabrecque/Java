import javafx.print.Printer;

/**
 * Throw an exception about a printer's assembly. To be displayed to the user.
 */
public class AssemblyException extends Exception
{
	public enum PrinterIssue {
		GENERAL,
		UNKNOWN,
		PAPER_JAM,
		FUSER,
		TONER,
		TONER_C,
		TONER_M,
		TONER_Y,
		TONER_K,
		DRUM
	}

	private PrinterIssue issue;
	private AssemblyUnit cause;

	/**
	 * Default constructor. Not recommended to use this, as the cause is generic. This means that multiple issues
	 * could replace each other in a listing, because they will all have the same cause.
	 * @param message Whatever message the user should see for this problem.
	 */
	public AssemblyException(String message) {
		super(message);
		issue = PrinterIssue.GENERAL;
	}

	/**
	 * Property.
	 * @return The problem causing this exception.
	 */
	public PrinterIssue issue() { return issue;	}

	/**
	 * Property.
	 * @return Type that is the cause of the failure.
	 */
	public AssemblyUnit cause() { return cause; }

	/**
	 * Constructor. Use this to define where the error came from so that the printer knows how it can be solved. A
	 * constant message is associated to each issue.
	 * @param issue The identifier of the problem.
	 * @param cause The AssemblyUnit type that caused the problem.
	 */
	public AssemblyException(PrinterIssue issue, AssemblyUnit cause) {
		super(messageFromIssue(issue));
		this.cause = cause;
	}

	private static String messageFromIssue(PrinterIssue issue) {
		switch(issue) {
			case UNKNOWN:
				return "Unknown problem";
			case PAPER_JAM:
				return "Paper jam";
			case FUSER:
				return "Fuser overheated";
			case TONER:
				return "Out of toner";
			case TONER_C:
				return "Out of cyan";
			case TONER_M:
				return "Out of magenta";
			case TONER_Y:
				return "Out of yellow";
			case TONER_K:
				return "Out of black";
			case DRUM:
				return "Drum worn out";
			default:
				return "General error";
		}
	}
}
