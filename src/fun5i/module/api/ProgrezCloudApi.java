package fun5i.module.api;

import com.google.gson.Gson;
import fun5i.module.api.model.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import org.json.JSONObject;
import java.util.logging.Logger;

/**
 *
 * @author fun5i
 * version 2.0.0
 */
public class ProgrezCloudApi {
    
    private static final Logger log = Logger.getLogger(ProgrezCloudApi.class.getName());
    
    // update 2.0.0
    public interface ProgrezApiListener{
        void onSuccess();
        void onError(int errorCode, String errorMessage);
    }
    
    // update 1.2.0
    @FunctionalInterface
    public interface ProjectCallback{
        void responseProject(int errno2, String errmsg2, PCProjectModel body);
    }
    
    // update 1.2.0
    @FunctionalInterface
    public interface LoginCallback{
        void responseLogin(int errno, String errmsg, PCLoginModel account);
    }
    
    // update 1.2.0
    public ProgrezCloudApi(){
    }
    
    // update 1.2.0
    private String generatePayload(String tokenProject, String[] fields){
        String result = null;
        try {
            JSONObject payload = new JSONObject();
            JSONObject payload2 = new JSONObject();

            payload2.put("maintask", new JSONObject().put("fields", fields[0]));
            payload2.put("task", new JSONObject().put("fields", fields[1]));
            payload2.put("subtask", new JSONObject().put("fields", fields[2]));
            
            payload.put("tasks", payload2);
            payload.put("token", tokenProject);

            result = payload.toString();
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return result;
    }
    
    // update 2.0.0
    private int error = -1;
    private String errorMessage = "lah kok kosong";
    private boolean loginType;
    private String userkey, username, password;
    private PCLoginModel loginModel;
    private PCCredentials credentials;
    private Project project;
    private List<Maintask> maintasks;
    
    
    // update 2.0.0
    private void setSemuaObjectProject(PCProjectModel projectModel){
        if (Integer.parseInt(projectModel.getErrno()) != 0){
            this.error = Integer.parseInt(projectModel.getErrno());
            this.errorMessage = projectModel.getErrmsg();
        }else{
            Data data = projectModel.getData();
            this.project = data.getProject();
            this.maintasks = data.getMaintask();
        }
        
    }

    // update 2.0.0
    public Project getProject() {
        return project;
    }

    // update 2.0.0
    public List<Maintask> getMaintasks() {
        return maintasks;
    }
    
    // update 2.0.0
    public String getUserkey() {
        return userkey;
    }

    // update 2.0.0
    public PCLoginModel getProfileUser() {
        return loginModel;
    }

    // update 2.0.0
    private void setLoginModel(PCLoginModel loginModel) {
        this.loginModel = loginModel;
    }

    // update 2.0.0
    public PCCredentials getCredentials() {
        return credentials;
    }

    // update 2.0.0
    public void setCredentials(PCCredentials credentials) {
        this.credentials = credentials;
    }
    
    // update 2.0.0
    public int getError() {
        return error;
    }

    // update 2.0.0
    private void setError(int error) {
        this.error = error;
    }

    // update 2.0.0
    public String getErrorMessage() {
        return errorMessage;
    }

    // update 2.0.0
    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // update 2.0.0
    public boolean isLoginType() {
        return loginType;
    }

    // update 2.0.0
    public String getUsername() {
        return username;
    }
    
    // update 2.0.0
    public void setProject(String tokenProject, String[] fields){
        PCProjectModel out = null;
        
        String payload = generatePayload(tokenProject, fields);
        String jsonString = this.actProject(
                this.credentials,
                payload
        );
        JSONObject res = new JSONObject(jsonString);
        
        // convert to object
        if (res.getInt("errno") == 0){
            // setCredential
            JSONObject crid = res.getJSONObject("credentials");
            PCCredentials newCCredentials = new PCCredentials(
                    crid.getString("d"),
                    crid.getString("s"),
                    crid.getString("o")
            );
            setCredentials(newCCredentials);
            setLoginModel(new PCLoginModel(
                    this.loginModel.getFlying_id(),
                    this.loginModel.getFullname(),
                    this.loginModel.getPhoto(),
                    newCCredentials
            ));
            
            Gson gson =new Gson();
            out = gson.fromJson(
                    res.getJSONObject("data").toString(), PCProjectModel.class);
            //System.out.println("Berhasil " + projectModel.getData().getMaintask().get(0).getTaskName());
            //apiListener.onSuccess();
            setSemuaObjectProject(out);
        }else{
            this.error= res.getInt("errno");
            this.errorMessage = res.getString("errmsg");
        }
        
    }
    
    // update 2.0.0
    public void getProject(PCCredentials credential,ProjectCallback listenerProject, String tokenProject, String[] fields){
        PCProjectModel out = null;
        String payload = generatePayload(tokenProject, fields);
        String jsonString = this.actProject(
                credential,
                payload
        );
        JSONObject res = new JSONObject(jsonString);
        //System.err.println(jsonString);
        // convert to object
        if (res.getInt("errno") == 0){
            // setCredential
            JSONObject crid = res.getJSONObject("credentials");
            PCCredentials newCCredentials = new PCCredentials(
                    crid.getString("d"),
                    crid.getString("s"),
                    crid.getString("o")
            );
            setCredentials(newCCredentials);
            setLoginModel(new PCLoginModel(
                    this.loginModel.getFlying_id(),
                    this.loginModel.getFullname(),
                    this.loginModel.getPhoto(),
                    newCCredentials
            ));
            
            Gson gson =new Gson();
            out = gson.fromJson(
                    res.getJSONObject("data").toString(), PCProjectModel.class);
            //System.out.println("Berhasil " + projectModel.getData().getMaintask().get(0).getTaskName());
            setSemuaObjectProject(out);
            
        }else{
            setError(res.getInt("errno"));
            setErrorMessage(res.getString("errmsg"));
        }
        
        // Update interface
        listenerProject.responseProject(
                res.getInt("errno"),
                res.getString("errmsg"),
                out
        );
        
    }
    
    // update 2.0.0
    public ProgrezCloudApi setUserKey(String userkey){
        this.userkey = userkey;
        this.loginType = true;
        return this;
    }
    
    // update 2.0.0
    public ProgrezCloudApi setUserLogin(String username, String password){
        this.username = username;
        this.password = password;
        this.loginType = false;
        return this;
    }
    
    //update 2.0.0
    public void login(LoginCallback listener){
        String request = "";
        if (this.isLoginType()){
            request = actLogin("type=userkey&userkey="+userkey);
        }else{
            request = actLogin("login="+username+"&password="+password);
        }
        JSONObject respond = new JSONObject(request);
        
        PCLoginModel abc = null;
        if(respond.getInt("errno") == 0){
            abc = generateAccount(respond);
            setLoginModel(abc);
            setCredentials(abc.getCredentials());
        }
        
        listener.responseLogin(
                respond.getInt("errno"),
                respond.getString("errmsg"),
                (respond.getInt("errno") > 0)?null:abc
        );
        
        setError(respond.getInt("errno"));
        setErrorMessage(respond.getString("errmsg"));
    }
    
    // update 2.0.0
    public void loadNewCridential(){
        String res = actLogin("type=userkey&userkey="+userkey);
        JSONObject respond = new JSONObject(res);
        
        if (respond.getInt("errno") > 0){
            this.error = respond.getInt("errno");
            this.errorMessage = respond.getString("errmsg");
        }else{
            //success set loginModel and credential
            setLoginModel(generateAccount(respond));
        }
    }
    
    // update 2.0.0
    private PCLoginModel generateAccount(JSONObject respond){
        JSONObject resCrident = respond.getJSONObject("credentials");
        PCCredentials cc = new PCCredentials(
        resCrident.getString("d"),
        resCrident.getString("s"),
        resCrident.getString("o")
        );
        setCredentials(cc);
        PCLoginModel pcLogin = new PCLoginModel(
                respond.getString("flying_id"),
                respond.getString("fullname"),
                respond.getString("photo"),
                cc
                
        );
        
        return pcLogin;
    }
    
    // update 1.2.0
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
    
    // update 1.2.0
    private String actProject(PCCredentials credentials, String payload){
        String result = null;
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
