package model;


import java.util.HashMap;


public class Item extends HashMap<String, String> {
    private static final long serialVersionUID = 12872473L;
    public String string1;
    public String string2;
    public Object o;

    public static String KEY_STRING1 = "string1";
    public static String KEY_STRING2 = "string2";

    public Item(Object o, String s1, String s2) {
        this.o = o;
            this.string1 = s1;
            this.string2 = s2;
    }

    @Override
    public String get(Object k) {
            String key = (String) k;
            if (KEY_STRING1.equals(key))
                    return string1;
            else if (KEY_STRING2.equals(key))
                    return string2;
            return null;
    }
    
    public String parseOutput(Object o){
        if( o instanceof String)
            return (String) o;
        else if( o instanceof Integer)
            return o.toString();
        else if( o instanceof Double)
            return o.toString();
        else
            return "null";
    }
    

}
