package JSONParser;

/**
 * JSONReader
 * Class reading a InputStream and creates a JSON structure out of it
 * The tree can be retrieved by getTree() or dumped by getString()
 * 
 * ToDo:
 * - create JSONNumber.getNumberClass() returning the smallest possible class able to handle JSONNumber.valueOf(Value)
 * 
 * @author Régis M. LeClerc
 * @email marabiloso@marabiloso.org
 **/


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class JSONReader {
    /* private definitions */
    private JSONObject Tree;      // Definitely a JSONObject, not a JSONValue
    private InputStream is; // InputStream taken from the constructor
    private char Car;             // last character read from InputStream
    public  long CarPos;
    private long LineNumber = 1;  // used by MalformedJSONException messages
    private final Pattern PATTERN = Pattern.compile( "^-?\\d+([,\\.]\\d+)?([eE]-?\\d+)?$" ); // used for number test in readValue()
    
    public static String readFileToString(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public long getCarPos() { return CarPos; }
    /**
     * @var read_ahead
     * When reading Literal values, it's impossible to know in advance the size of the literal, so readCar()
     * continues until the character read doesn't pertain to the Literal anymore.
     * This implies readCar() has consumed one character too many from the stream, hence the following call
     * to readCar() MUST NOT consume the character but "stutter", providing the same character that was used
     * to define the end of the Literal.
     * This actually makes the code for readArrayNode and readObjectNode much more straightforward, as you
     * don't need to make exceptions when reading the next character after a Literal as it is *really* the
     * character immediately following the Literal.
     */
    private boolean read_ahead;
    
    public final JSONObject readJSONFromStream(InputStream i) throws MalformedJSONException, IOException {
        initTree(null); // clear contents, make contents ready for linking
        is = i;
        read_ahead = true; // initialise reader
        skipWhitespace();
        throwBadChar('{');
        Tree = (JSONObject)readValue(null);
        return Tree;
    }
    
    public JSONReader(InputStream InputFile) throws IOException, MalformedJSONException {
        readJSONFromStream(InputFile); // will call initTree()
    }

    public JSONReader() {
        initTree(null); // empty tree
        is = null;      // no file
    }
    
    public final void initTree(JSONObject V) {
        CarPos = 0;
        LineNumber = 1;
        Tree = V;
    }
    
    // test if the character is the one expected, throw a MalformedJSONException if not.
    private void throwBadChar(char expected) throws MalformedJSONException {
        if(Car != expected) {
            String s =
                    "Line " + Long.toString(LineNumber) +
                    ": Expecting \'" + expected +
                    "\', found \'" + Car + "\' instead.";
            throw new MalformedJSONException(s);
        }
    }

/**
 * Function not used anymore after optimisation, as there were only 2 calls to this function.
 * A readable version of this test would be:
 * private boolean isWhitespace() {
 *     return
 *         (Car == CHAR_SPACE    ) ||
 *         (Car == CHAR_TAB      ) ||
 *         (Car == CHAR_FORMFEED ) ||
 *         (Car == CHAR_BACKSPACE) ||
 *         (Car == CHAR_RETURN   ) ||
 *         (Car == CHAR_NEWLINE  );
 * }
 * 
 * But for performance reasons, the following would be used:
 *     return (" \t\f\n\b\r".indexOf(Car) != -1);
 */

    // JSON hex values for Unicode are [0-9a-f], not [A-F] (RFC 7159)
    private boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c>='a' && c <= 'f');
    }
    
    /**
     * callback function
     * user-defined function called every time a character is read
     * This was required for the ProgressBar update during file read
     */
    protected Runnable callbackFileRead = null;
    public void setReadCallback(Runnable R) { callbackFileRead = R; }
    
    private int readCar() throws IOException {
        int ret;
        if(read_ahead) {
            if((ret = is.read()) != -1) {
                Car = (char)ret;
                CarPos++;
                if(callbackFileRead != null) callbackFileRead.run();
            }
            if(ret == '\n') LineNumber++;
        } else {
            ret = Car;
            read_ahead = true;
        }
        return ret;
    }
    
    private String readString() throws IOException, MalformedJSONException {
        HugeString ret = new HugeString();
        /**
         * the code handling Unicode mustn't repeat last character in Car
         * true by default, only set to false when handling UTF-8 escape so the contents of Car
         * is not appended to ret on parsing UTF-8 values
         */
        boolean APPEND_CHAR = true;
        while((readCar() != -1) && (Car != '\"')) {
            if(Car == '\\') { /* backspace found, the following character determines the escape type */
                switch(readCar()) {
                    // the following three are just escapes of themselves, doing nothing removes the REVERSE_SOLIDUS escape mark
                    case '\"' :
                    case '\\' :
                    case '/'  :
                        // do nothing, the content of Car will be appended to ret at the end of readString()
                        break;
                        
                    /**
                     * escape sequences, replace by their real value.
                     * Not sure if it counts, but order by how often you expect to see each escape code may save some CPU cycles.
                     * Meanwhile... It's Java, not C, who cares about CPU cycles?
                     */
                        
                    case 'n' : Car = '\n' ; break;
                    case 't' : Car = '\t' ; break;
                    case 'f' : Car = '\f' ; break;
                    case 'b' : Car = '\b' ; break;
                    case 'r' : Car = '\r' ; break;
                    case 'u' : // Unicode escape code
                        String num = "";
                        for(int i = 0 ; i < 4 ; i++)
                            if((readCar() != -1) && isHexDigit(Car)) num += Car;
                            else throw new MalformedJSONException(LineNumber + ": Bad character \'" + Car + "\' in unicode value.");
                        
                        /**
                         * num contains the codepoint after \\u
                         * Integer.valueOf(num,16) converts it to an integer (16 is the radix for hexadecimal)
                         * Character.toChars() converts the codepoint to a sequence of chars
                         * 
                         * Note: Character.tochars().toString() just returns junk.
                         */
                        ret.addChars(Character.toChars(Integer.valueOf(num,16)));
                        APPEND_CHAR = false;
                        break;
                    
                    default : // any other character coming after the escape sequence is an invalid character
                        throw new MalformedJSONException(LineNumber + ": Unrecognised escape sequence \'\\" + Car + "\'.");
                        
                }
            }
            if(APPEND_CHAR) ret.addChar(Car);
        }
        return ret.toString();
    }
    
    @SuppressWarnings("empty-statement") // not so empty... But Java doesn't like too much this C-style
    private void skipWhitespace() throws IOException {
        while((readCar() != -1) && (" \t\f\n\b\r".indexOf(Car) != -1));
    }
    
    private JSONValue readValue(JSONValue Parent) throws IOException, MalformedJSONException {
        JSONValue ret = null; // Never used as JSONValue but always as a generic container for the classes extending it
        // Car contains the first caracter of the value, from there, define the type of the value:
        switch(Car) {
            /**
             * There is no need to define a function for each of the following types, it would be called only from here.
             * However, readArrayNode and readObjectNode are recursive and must be defined separately.
             **/
            case '"' :
                ret = new JSONString(readString());
                break;
                
            case '{' :
                /**
                 * RFC 7159: an Object has the following format:
                 * BEGIN_OBJECT (ws) [ObjectNode (ws)] END_OBJECT
                 * - All whitespaces (ws) are optional;
                 * - an object can be empty ({});
                 * - see notes for readObjectNode() for the structure of the optional ObjectNode element.
                 */
                ret = new JSONObject();
                skipWhitespace();
                if(Car != '}') ((JSONObject)ret).setValue(readObjectNode(ret)); // will read all ObjectNodes recursively
                break;

            case '[' :
                /**
                 * RFC 7159: an Array has the following format:
                 * BEGIN_ARRAY (ws) [ValueNode (ws)] END_ARRAY
                 * - all whitespaces (ws) are optional;
                 * an array can be empty ([]);
                 * - see notes for readArrayNode() for the structure of the optional ArrayNode element.
                 */
                ret = new JSONArray();
                skipWhitespace();
                if(Car != ']') ((JSONArray)ret).setValue(readArrayNode(ret)); // will read all the ArrayNodes recursively
                break;

            default               : // Literal read as a string but checked to be null, boolean or Number, throws exception
                String ts = "";
                do
                    ts += Car;
                while ((readCar() != -1) && ("\b\f\n\r\t ,]}".indexOf(Car) == -1));
                
                /**
                 * An un-optimised (readable) version would more look like:
                 * while((readCar() != -1) && (!isWhitespace()) &&
                 *       (Car != VALUE_SEPARATOR) && (Car != END_ARRAY) && (Car != END_OBJECT));
                 */
                
                switch(ts) { /* switch() with String values was advised by NetBeans. Clearer than a chain of "if()" */
                    case "true"  : ret = new JSONBoolean(true) ; break;
                    case "false" : ret = new JSONBoolean(false); break;
                    case "null"  : ret = new JSONNull()        ; break;
                    default      :
                        if(PATTERN.matcher(ts).matches()) ret = new JSONNumber(ts);
                        else throw new MalformedJSONException(LineNumber + ": \"" + ts + "\" is not a number");
                }
                read_ahead = false; // Repeat current char on next call to readCar()
        }
        ret.setParent(Parent); // ret is never null
        return ret;
    }

    /**
     * RFC 7159: ArrayNode has the following format:
     * Value (ws) [VALUE_SEPARATOR (ws) JSONArrayNode]
     * - all white spaces (ws) are optional;
     * - Value is mandatory.
     **/
    private JSONArrayNode readArrayNode(JSONValue Parent) throws IOException, MalformedJSONException {
        JSONArrayNode ret = new JSONArrayNode(readValue(Parent));
        ret.Parent = Parent;
        skipWhitespace();
        if(Car == ',') {
            skipWhitespace();
            ret.setNext(readArrayNode(Parent));
        }
        throwBadChar(']');
        return ret;
    }

    /**
     * RFC 7159: an ObjectNode has the following format:
     * QUOTATION_MARK Name_String QUOTATION_MARK (ws) NAME_SEPARATOR (ws) Value (ws) [VALUE_SEPARATOR (ws) JSONObjectNode ]
     * - all white spaces (ws) are optional;
     * - Name_String and Value are mandatory.
     **/
    private JSONObjectNode readObjectNode(JSONValue Parent) throws IOException, MalformedJSONException {
        throwBadChar('"');        
        String Name = readString();
        skipWhitespace();
        throwBadChar(':');
        skipWhitespace();
        JSONObjectNode ret = new JSONObjectNode(Name,readValue(Parent));
        ret.Parent = Parent;
        skipWhitespace();
        if(Car == ',') {
            skipWhitespace();
            ret.setNext(readObjectNode(Parent)); // recursive call
        }
        throwBadChar('}');
        return ret;
    }
    
    public JSONObject getTree() { return Tree; }
    public String getString(int level) { return Tree.getString(level); }
}
