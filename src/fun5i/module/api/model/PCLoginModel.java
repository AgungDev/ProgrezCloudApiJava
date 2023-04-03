/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fun5i.module.api.model;
/**
 *
 * @author fun5i
 */
public class PCLoginModel {
    
    private final String flying_id,fullname,photo;
    private final PCCredentials credentials;
    
    public PCLoginModel(String flyingID, String fullname, String photo, PCCredentials cs){
        this.flying_id=flyingID;
        this.fullname= fullname;
        this.photo=photo;
        this.credentials=cs;
    }

    public String getFlying_id() {
        return flying_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public PCCredentials getCredentials() {
        return credentials;
    }
 
    
}
