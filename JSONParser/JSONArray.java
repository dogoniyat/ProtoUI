package JSONParser;

/**
 * JSONArray
 * JSONValue for <JSONArrayNode> to handle Arrays as defined in RFC 7159
 * 
 * @author Régis M. LeClerc
 **/



public class JSONArray extends JSONList {

    /**
     *
     * @param v
     */
    public JSONArray(JSONArrayNode v) { super(v); }

    /**
     *
     */
    public JSONArray() { super(); }

    /**
     *
     * @param V
     * @return
     */
    @Override // need to override to include length calculation
    public JSONNode setValue(JSONNode V) {
        super.setValue(V);
        calcLength();
        return Value;
    }

    /**
     *
     * @param level
     * @return
     */
    @Override // from JSONValue
    public String getString(int level) {
        String ret = "[\n";
        JSONNode cursor = this.getValue();
            while(cursor != null) {
                ret += getIndent(level + 1) + cursor.getString(level + 1);
                if((cursor = cursor.Next) != null) ret += " ,\n";
        }
        ret += "\n" + getIndent(level) + "]";
        return ret;
    }
}
