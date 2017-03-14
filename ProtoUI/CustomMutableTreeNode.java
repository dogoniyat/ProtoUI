package protoui;

import JSONParser.JSONValue;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rleclerc
 */
class CustomMutableTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;
    JSONValue ValuePointer;
    CustomMutableTreeNode(Object O               ) { super(O); ValuePointer = null; }
    CustomMutableTreeNode(Object O, JSONValue Ptr) { super(O); ValuePointer = Ptr;  }
    JSONValue setValuePointer(JSONValue Ptr) { return (ValuePointer = Ptr); }
    JSONValue getValuePointer() { return ValuePointer; }
}