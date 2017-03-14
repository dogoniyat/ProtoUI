/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONParser;

/**
 *
 * @author regisleclerc
 */
public abstract class JSONNode extends JSONValue<JSONValue> {
    protected JSONNode Prev,Next;
    private void init() {
        Next = null;
        Prev = null;
    }
    JSONNode()            { super(null); init(); }
    JSONNode(JSONValue v) { super(v   ); init(); }
    public JSONNode getNext() { return Next; }
    
    protected JSONNode setNext(JSONNode n) {
        Next = n;
        if(Next != null) Next.Prev = this;
        return Next;
    }
    
    public JSONNode getPrev() { return Prev; }
    protected JSONNode setPrev(JSONNode p) {
        Prev = p;
        if(Prev != null) Prev.Next = this;
        return Prev;
    }
    
    public boolean hasNext() { return (this.Next != null); }
    public boolean hasPrev() { return (this.Prev != null); }


    public void remove() {
        if(Next != null) Next.Prev = this.Prev;
        if(Prev != null) Prev.Next = this.Next;
        if((Parent != null) && (this == Parent.Value)) ((JSONList)Parent).setValue(Next);
    }

    /**
     *
     * @return empty string
     */
    public String getName() { return ""; }
}
