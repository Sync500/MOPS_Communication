/**
 * 
 */
package communicationInterface;

/**
 * @author Daniel Fay
 *
 */
@SuppressWarnings("serial")
public class Data implements java.io.Serializable {

	public String name;
	public String vorname;
	transient public int telefon;
	public boolean unsicher;
}
