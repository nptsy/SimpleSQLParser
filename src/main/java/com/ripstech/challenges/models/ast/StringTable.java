package com.ripstech.challenges.models.ast;

import java.util.HashMap;
import java.util.Map;

import com.ripstech.challenges.utils.Token;

class StringTable {

    //
    // Class Variables
    //
    static private Map map = new HashMap ();

    //
    // Fields
    //
    private String str;          // The key
    private int token;           // The value


    //
    //  Constructor
    //
    StringTable (String s, int t) {
        str = s;
        token = (byte) t;
    }


    //
    // insert (str, tokenType)
    //
    // This method inserts a new entry mapping String "str" to value "tokenType".
    //
    static void insert (String s, int tokenType) {
        StringTable entry = new StringTable (s, tokenType);
        map.put (s, entry);
    }


    //
    // lookupToken (str) --> tokenType
    //
    // This method looks string "str" up and returns the corresponding token
    // type.  It returns -1 if not found.
    //
    static int lookupToken (String s) {
        StringTable entry = (StringTable) map.get (s);
        if (entry == null) {
            return -1;
        } else {
            return entry.token;
        }
    }


    //
    // lookupString (str) --> str
    //
    // This method looks string "str" up and returns the cannonical (unique)
    // string.  It throws NullPointerException if "str" is not found.
    //
    static String lookupString (String s) {
        StringTable entry = (StringTable) map.get (s);
        return entry.str;
    }


    //
    // init ()
    //
    // This method initialize the string table, by adding the keywords to it.
    //
    static void init () {
        insert ("and",       Token.AND);
        insert ("array",     Token.ARRAY);
        insert ("begin",     Token.BEGIN);
        insert ("by",        Token.BY);
        insert ("div",       Token.DIV);
        insert ("do",        Token.DO);
        insert ("else",      Token.ELSE);
        insert ("elseif",    Token.ELSEIF);
        insert ("end",       Token.END);
        insert ("exit",      Token.EXIT);
        insert ("for",       Token.FOR);
        insert ("if",        Token.IF);
        insert ("is",        Token.IS);
        insert ("loop",      Token.LOOP);
        insert ("mod",       Token.MOD);
        insert ("not",       Token.NOT);
        insert ("of",        Token.OF);
        insert ("or",        Token.OR);
        insert ("procedure", Token.PROCEDURE);
        insert ("program",   Token.PROGRAM);
        insert ("read",      Token.READ);
        insert ("record",    Token.RECORD);
        insert ("return",    Token.RETURN);
        insert ("then",      Token.THEN);
        insert ("to",        Token.TO);
        insert ("type",      Token.TYPE);
        insert ("var",       Token.VAR);
        insert ("while",     Token.WHILE);
        insert ("write",     Token.WRITE);

    }
}