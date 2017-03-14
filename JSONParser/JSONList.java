package JSONParser;

/**
 * JSONObject
 * Specialisation of JSONValue to handle JSONNode objects
 * Abstract class ancestor of JSONObject and JSONArray
 * 
 * @author Régis M. LeClerc
 */

public abstract class JSONList extends JSONValue<JSONNode> {
    protected int length = 0;

    JSONList(JSONNode v) {
        super(v);
        calcLength(); // calcLength() must be final to be called in constructor
    }
    
    JSONList() {
        super(null);
        length = 0;
    }

    public JSONNode insertNode(JSONNode N) {
        if (null != N) {
            N.Prev = null;
            N.Next = this.Value;
            N.Parent = this;
        }
        if(this.Value != null) this.Value.Prev = N;
        return this.Value = N;
    }
    /**
     * getNode(JSONValue Node)
     * @param V
     * @return the node record holding the value V
     */
    public JSONNode getNode(JSONValue V) {
        JSONNode ret = Value;
        while((ret != null) && (ret.Value != V)) ret = ret.Next;
        return ret;
    }
        
    @Override // need to override to include length calculation
    public JSONNode setValue(JSONNode V) {
        super.setValue(V);
        this.calcLength();
        return Value;
    }
    
    public final int calcLength() {
        length = 0;
        JSONNode cursor = this.Value;
        while(cursor!=null) { cursor = cursor.Next; length++; }
        return length;
    }
    
    @Override // from JSONValue
    public abstract String getString(int level);
}
