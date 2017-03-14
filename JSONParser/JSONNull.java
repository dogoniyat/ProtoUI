package JSONParser;

/**
 * JSONNull
 * Class to handle the "null" value, for the sake of being orthodox.
 * 
 * @author Régis M. LeClerc
 * @email marabiloso@marabiloso.org
 */


public class JSONNull extends JSONValue<JSONValue> {
    public JSONNull() { this.Value = null; }

    @Override
    public String getString(int level) { return "null"; }
    
}
