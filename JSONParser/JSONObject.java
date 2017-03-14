package JSONParser;

/**
 * JSONObject
 * Specialisation of JSONList to handle <JSONObjectNode>
 * 
 * @author Régis M. LeClerc
 */



public class JSONObject extends JSONList {
    public JSONObject(JSONObjectNode v) { super(v);    }
    public JSONObject()                 { super(null); }

    public JSONNode getObjectNode(JSONValue node) {
        JSONNode ret = this.Value;
        while((ret!=null) && (ret.Value != node)) ret = ret.Next;
        return ret;
    }
    
    /**
     *
     * @param level
     * @return the string in JSON format
     */
    @Override
    public String getString(int level) {
        String s = "{\n";
        
        JSONNode cursor = getValue();
        while(cursor !=null) {
            s += getIndent(level + 1) + cursor.getString(level + 1);
            if((cursor = cursor.getNext()) != null) s += " ,\n";
        }
        s += "\n" + getIndent(level) + "}";
        return s;
    }
}
