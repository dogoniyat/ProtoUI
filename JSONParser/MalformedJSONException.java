package JSONParser;

/**
 * MalformedJSONException
 * Parse exception thrown by JSONReader
 * 
 * @author Régis M. LeClerc
 */

public class MalformedJSONException extends Exception {
    public MalformedJSONException(String msg) { super(msg); }
}
