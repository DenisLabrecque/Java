/**
 * Throw an exception about a printer's assembly. To be displayed to the user.
 */
public class AssemblyException extends Exception
{
	public AssemblyException(String message) {
		super( message );
	}
}
