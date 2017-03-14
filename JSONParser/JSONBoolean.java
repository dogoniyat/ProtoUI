package JSONParser;

/**
 * JSONBoolean
 * class to handle the boolean values "true" and "false".
 * 
 * @author Régis M. LeClerc
 * @email marabiloso@marabiloso.org
 */

public class JSONBoolean extends JSONValue<Boolean> {
    public JSONBoolean(boolean b) { super(b); }
    
    @Override
    public String getString(int level) { return Value?"true":"false"; }
}
