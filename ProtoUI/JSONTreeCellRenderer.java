/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protoui;

import JSONParser.JSONArray;
import JSONParser.JSONBoolean;
import JSONParser.JSONNumber;
import JSONParser.JSONObject;
import JSONParser.JSONString;
import JSONParser.JSONValue;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author rleclerc
 */
public class JSONTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;
    private final String IconObjectResourceName = "Images/icon_object.png";
    private final String IconArrayResourceName  = "Images/icon_array.png";
    private final String IconStringResourceName = "Images/icon_cyan.png";
    private final String IconTrueResourceName   = "Images/icon_true.png";
    private final String IconFalseResourceName  = "Images/icon_false.png";
    private final String IconNumberResourceName = "Images/icon_blue.png";
    private final String IconNullResourceName   = "Images/icon_yellow.png";

    private final ImageIcon IconObject;
    private final ImageIcon IconArray;
    private final ImageIcon IconString;
    private final ImageIcon IconTrue;
    private final ImageIcon IconFalse;
    private final ImageIcon IconNumber;
    private final ImageIcon IconNull;

    public JSONTreeCellRenderer() {
        // Read the icons from the resources in the Jar and store them in memory once for all
        IconNull   = new ImageIcon(getClass().getResource(IconNullResourceName  ));
        IconNumber = new ImageIcon(getClass().getResource(IconNumberResourceName));
        IconFalse  = new ImageIcon(getClass().getResource(IconFalseResourceName ));
        IconTrue   = new ImageIcon(getClass().getResource(IconTrueResourceName  ));
        IconString = new ImageIcon(getClass().getResource(IconStringResourceName));
        IconArray  = new ImageIcon(getClass().getResource(IconArrayResourceName ));
        IconObject = new ImageIcon(getClass().getResource(IconObjectResourceName));
    }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
    {
        final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value; // get the selected node

        // depending on the type of JSON value, assign a different icon
        if(node instanceof CustomMutableTreeNode) {
            JSONValue ValuePointer = ((CustomMutableTreeNode)node).ValuePointer;
            setIcon(
                ValuePointer instanceof JSONObject  ? IconObject  :
                ValuePointer instanceof JSONArray   ? IconArray   :
                ValuePointer instanceof JSONString  ? IconString  :
                ValuePointer instanceof JSONNumber  ? IconNumber  :
                ValuePointer instanceof JSONBoolean ? 
                        (((JSONBoolean)ValuePointer).getValue() ? IconTrue : IconFalse) :
                                                      IconNull
            );
            setText(
                    ValuePointer instanceof JSONString ? value.toString() + " ["  + ((JSONString)ValuePointer).length() + "]" :
                    ValuePointer instanceof JSONNumber ? value.toString() + " = " + ((JSONNumber)ValuePointer).getValue()     :
                    value.toString()
            );
        } else setText(value.toString()); // display node's name found in UserObject
        setOpaque(sel);
        setBackground(sel ? backgroundSelectionColor : backgroundNonSelectionColor);
        return ret;
    }
}
