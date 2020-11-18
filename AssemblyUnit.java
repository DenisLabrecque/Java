/**
 * Base class for printer assembly units.
 */
public abstract class AssemblyUnit
{
	protected boolean activated = false;
	private   int     errorCode = 0;
	
	public abstract void activate()   throws AssemblyException;
	public abstract void deactivate() throws AssemblyException;
	
	public boolean isActive() {
		return activated;
	}

	/**
	 *
	 * @param value Convert this from a printer issue.
	 */
	public void setError(int value) {
		errorCode = value;
	}
	
	public void setError() {
		setError(999);
	}
	
	public boolean isError() {
		return errorCode > 0;
	}
}
