/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package fun5i.module.api;

import fun5i.module.api.model.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

/**
 *
 * @author fun5i
 * version 1.0.1
 */
public class ProgrezCloudApi {
    
    @FunctionalInterface
    public interface ProjectCallback{
        void response(int errno2, String errmsg2, String body);
    }
    
    @FunctionalInterface
    public interface LoginCallback{
        void response(int errno, String errmsg, PCLoginModel account);
    }
    
    public ProgrezCloudApi(){
    }
    
    public void setProject(PCLoginModel account,ProjectCallback a, String tokenProject, String fields){
        
        JSONObject payload = new JSONObject();
        JSONObject payload2 = new JSONObject();
        JSONObject payload3 = new JSONObject();
        
        payload3.put("fields", fields);
        payload2.put("maintask", payload3);
        payload2.put("task", payload3);
        payload2.put("subtask", payload3);
        payload.put("tasks", payload2);
        payload.put("token", tokenProject);
        
        String projt = this.actProject(
                account,
                payload.toString()
        );

        JSONObject res = new JSONObject(projt);
        a.response(
                res.getInt("errno"),
                res.getString("errmsg"),
                (res.getInt("errno") > 0)? res.toString(): res.getJSONObject("data").toString()
        );
    }
    
    public void login(LoginCallback a, String username, String password){
        String res = actLogin("login="+username+"&password="+password);
        JSONObject respond = new JSONObject(res);
        
        a.response(
                respond.getInt("errno"),
                respond.getString("errmsg"),
                (respond.getInt("errno") > 0)?null:generateAccount(respond)
        );
        
    }
    
    public void login(LoginCallback a, String userkey){
        String res = actLogin("type=userkey&userkey="+userkey);
        JSONObject respond = new JSONObject(res);
        
        a.response(
                respond.getInt("errno"),
                respond.getString("errmsg"),
                (respond.getInt("errno") > 0)?null:generateAccount(respond)
        );
        
    }
    
    private PCLoginModel generateAccount(JSONObject respond){
        JSONObject resCrident = respond.getJSONObject("credentials");
        PCLoginModel pcLogin = new PCLoginModel(
                respond.getString("flying_id"),
                respond.getString("fullname"),
                respond.getString("photo"),
                new PCCredentials(
                        resCrident.getString("d"),
                        resCrident.getString("s"),
                        resCrident.getString("o")
                )
                
        );
        
        return pcLogin;
    }
    
    private String actLogin(String urlParameters){
        String result = null;
        try {
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "https://progrez.cloud/s/fox/login";
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                sb.append(line);
            }
            String response = sb.toString();
            result = response;
        }catch(IOException e){
            
        }
        
        return result;
    }
    
    private String actProject(PCLoginModel accouts, String payload){
        String result = null;
        PCCredentials credentials = accouts.getCredentials();
        try {
            byte[] postData       = payload.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "https://progrez.cloud/s/fox/project";
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            
            JSONObject crFox = new JSONObject();
            crFox.put("d", credentials.getD());
            crFox.put("s", credentials.getS());
            crFox.put("o", credentials.getO());
            
            conn.setRequestProperty( "Credential-Fox", crFox.toString());
            conn.setRequestProperty( "charset", "utf-8"); 
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                sb.append(line);
            }
            String response = sb.toString();
            result = response;
        }catch(IOException e){
            
        }
        
        return result;
    }
}