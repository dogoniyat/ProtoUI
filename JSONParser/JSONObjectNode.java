package JSONParser;



/**
 * JSONObjectNode
 * Extends JSONObject to provide an Object Name and linked list capacity
 * 
 * @author Régis M. LeClerc
 */

public class JSONObjectNode extends JSONNode {
    protected String Name;
    
    private void init(String n) {
        Name = n;
        Next = null;
        Prev = null;
    }
    public JSONObjectNode()                       { super(null); init(""); }
    public JSONObjectNode(String n)               { super(null); init(n ); }
    public JSONObjectNode(String n , JSONValue v) { super(v   ); init(n ); }
    
    @Override
    public String getString(int level) {
        return ("\"" + this.Name + "\" , " + this.Value.getString(level));
    }

    @Override
    public String getName()          { return this.Name; }
    public void setName(String name) { this.Name = name; }
}
