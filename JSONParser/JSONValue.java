package JSONParser;

/**
 * JSONValue
 * Generic type for JSON values
 * 
 * @author Régis M. LeClerc
 * @param <T>
 */


public abstract class JSONValue<T> {
    protected T Value;
    protected JSONValue Parent;
    
    public JSONValue(T v) { Value = v; }
    public JSONValue() { Value = null; }
    public T getValue() { return Value; }
    public JSONValue setValue(T v) { Value = v; return this; }

    abstract public String getString(int level);
    
    /**
     * getIndent()
     * return a string made of as many indentations as present in @param level
     * @param level: indentation level
     * @return as many indent blocks as requested in level
     */
    protected String getIndent(int level) {
        String ret = "";
        while(level-- > 0) ret += "    "; /* much faster than a for() loop */
        return ret;
    } // getIndent()

    /**
     * @return the Parent
     */
    public JSONValue getParent() { return Parent; }

    /**
     * @param Parent the Parent to set
     */
    public void setParent(JSONValue Parent) { this.Parent = Parent; }
} // class JSONValue<T>
