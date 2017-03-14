package JSONParser;

/**
 * JSONJstring
 * Specialisation of JSONValue for String
 *
 * @author Régis M. LeClerc
 */


public class JSONString extends JSONValue<String> {
    public JSONString(String v) { super(v ); }
    public JSONString()         { super(""); } // in RFC, a string is always initialised to a value, like any object

    public String getLeft   (int Length) { return Length >= Value.length() ? Value : Value.substring(0, Length); }
    public String escapeLeft(int Length) { return escapeString(getLeft(Length)); }
    
    public String escapeString(String S) {
        String ret = "";
        char[] cs  = S.toCharArray();
        
        for(int i = 0 ; i < cs.length ; i++) {
            char Car = cs[i];
            
            if(Car < 0x20) /* control characters 00-31 */
                switch(Car) {
                    /**
                     * Except the following 5 escapes, all values in [0-0x1f] must
                     * be converted to UTF-8 escapes (the default: case)
                    **/
                    case '\t' : ret += "\\t"; break;
                    case '\n' : ret += "\\n"; break;
                    case '\r' : ret += "\\r"; break;
                    case '\b' : ret += "\\b"; break;
                    case '\f' : ret += "\\f"; break;
                    default   : ret += "\\u" + String.format("%04x",(int)Car);
                } //  switch() where 0x00 <= Car <= 0x1f
             else if(Car < 0x80) /* ASCII 7-bits area */
                switch(Car) {
                    case '\"' : 
                    case '\\' :
                    case '/'  :
                        /* the SOLIDUS case is not *so* mandatory, it makes URLs pretty unreadable, but it's in the RFC */
                        ret += "\\";
                        // No break, this is intended (fallthrough)
                    default   : ret += Car;
                } // switch() where 0x20 <= Car <= 0x7f
             else ret += "\\u" + String.format("%04x",Character.codePointAt(cs, i));
        } // for()
        return ret;
    }
    
    /**
     * @param level
     * @return string for JSONString as a value, with QUOTATION_MARK at the beginning and the end of string
     */    
    @Override
    public String getString(int level) { return ('"' + escapeString(Value) + '"'); }
    public int length() { return Value.length(); }
}