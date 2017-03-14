/**
 * HugeString
 * store string as a linked list of pre-allocated strings (StringBuilder objects)
 * for performance reasons.
 * 
 * The following code is awfully slow:
 * String ret = "";
 * char Car = '-';
 * for(int i = 0 ; i < 1000000 ; i++) ret += Car;
 */
package JSONParser;

import java.util.Arrays;

/**
 *
 * @author rleclerc
 */
public class HugeString {
    
    private class StringBlock {
        private int BlockSize;
        private StringBuilder Block;
        private StringBlock next;

        StringBlock() { initB(8192); }
        StringBlock(int sz) { initB(sz); }

        private void initB(int sz) {
            next = null;
            BlockSize = sz;
            Block = new StringBuilder(BlockSize);
        }

        public int addChar(char c) {
            Block.append(c);
            return BlockSize - Block.length();
        }

        @Override
        public String toString() {            
            return Block.toString();
        }

        StringBlock setNext(StringBlock NextBlock) { return (next = NextBlock); }
    }

    StringBlock First,Last;
    HugeString() {
        Last = First = new StringBlock();
    }
    
    public void addChar(char C) {
        if(Last.addChar(C) == 0)
            Last = Last.setNext(new StringBlock());
    }
    
    public void addChars(char[] s) {
        for(int i = 0 ; i < s.length ; i++) this.addChar(s[i]);
    }
    
    @Override
    public String toString() {
    /**
     * build a very large string (up to 2GB, there's no such thing as unsigned int in Java)
     * to copy the string in it without the need to reallocate everything at each block.
     */
        StringBuilder T = new StringBuilder(length());
        StringBlock cursor = First;
        while(cursor != null) {
            T.append(cursor.toString());
            cursor = cursor.next;
        }
        return T.toString();
    }
    
    public int length() {
        int ret = 0;
        StringBlock cursor = First;
        while(cursor != null) {
            ret += cursor.Block.length();
            cursor = cursor.next;
        }
        return ret;
    }
}
