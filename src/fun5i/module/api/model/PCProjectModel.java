package fun5i.module.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PCProjectModel {

@SerializedName("errno")
private String errno;

@SerializedName("data")
private Data data;

@SerializedName("errmsg")
private String errmsg;

/**
* No args constructor for use in serialization
*
*/
public PCProjectModel() {
}

/**
*
* @param errno
* @param data
* @param errmsg
*/
public PCProjectModel(String errno, Data data, String errmsg) {
super();
this.errno = errno;
this.data = data;
this.errmsg = errmsg;
}

public String getErrno() {
return errno;
}

public void setErrno(String errno) {
this.errno = errno;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

public String getErrmsg() {
return errmsg;
}

public void setErrmsg(String errmsg) {
this.errmsg = errmsg;
}

}