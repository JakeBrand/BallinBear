package model;
import java.util.Date;



public class Comment {

    
    
    
private Date cTimeStamp;
private String value;


public Comment(Date c, String v){
    this.cTimeStamp = c;
    this.value = v;
}

public Date getTimestamp() {

    return cTimeStamp;
}

public void setTimestamp(Date timestamp){

    this.cTimeStamp = timestamp;
}

public String getValue(){

    return value;
}

public void setValue(String value){

    this.value = value;
}


}
