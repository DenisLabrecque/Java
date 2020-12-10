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
		DRUM
	}

	private PrinterIssue issue;
	private AssemblyUnit cause;

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
		this.issue = issue;
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
			case DRUM:
				return "Drum worn out";
			default:
				return "General error";
		}
	}
}
