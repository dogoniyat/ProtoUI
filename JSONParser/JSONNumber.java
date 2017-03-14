package JSONParser;

/**
 * JSONNumber
 * Object type to store numerical values
 * They are simply stored as Strings, no need at this point for a deeper analysis of the type of Number
 * 
 * @author Régis M. LeClerc
 */


public class JSONNumber extends JSONValue<String> {
    public JSONNumber(String ts) { super(ts); }
    
    @Override
    public String getString(int level) { return getValue(); }
}
