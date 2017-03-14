package JSONParser;

/**
 * JSONArrayNode
 * Specialisation of JSONList
 * 
 * @author Régis M. LeClerc
 **/


public class JSONArrayNode extends JSONNode {
//    protected JSONArrayNode Prev,Next;
    
    public JSONArrayNode()            { super( ); init();  }
    public JSONArrayNode(JSONValue v) { super(v); init (); }

    private void  init() {
        Prev = null;
        Next = null;
    }

    @Override
    public String getString(int level) {
        return this.Value.getString(level);
    }
}