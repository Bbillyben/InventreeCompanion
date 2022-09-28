/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree;

import com.jcabi.http.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author blegendre
 */


public class ApiResponse {
    private int status;
    private String error;
    private String response;
    
    
    public ApiResponse(Response resp){
        status = resp.status();
        response = resp.body();
        checkError();
        
    }
    
    private void checkError(){
        String pattern ;
        pattern = "\"(?:error|detail)\":\"(.*)\"";
        pattern += "|\"pk\":\\[\"(.*)\"";
        
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(response);
       
        if(m.find()){
            if(m.group(1)!= null){
                error = m.group(1);
            }else if(Integer.valueOf(status/10) == 40 && m.group(2) != null){
                error = m.group(2);
            }
        }
    }
    public JSONObject getJson(){
        return new JSONObject(response);
       
    }
    public JSONArray getArray(){
        try{
            return new JSONArray(response);
        }catch(JSONException e){
            try{
                return new JSONArray("["+response+"]");
            }catch(JSONException er){

                return null;
            }
            
        }       
    }
    public String getError(){
        return error;
    }
    
    public boolean check(){
         switch (status) {
                case 400, 401, 402, 403, 404:
                    return false;
                case 200,201,202: // action ok
                    return true;
                default: // other status not yet handled
                    return false;
            }
    }
    
    @Override
    public String toString(){
        String str ="[Inventree.ApiResponse]"
                + "\n   - status : "+status
                + "\n   - error : "+error
                + "\n   - response : "+response;
        return str;
    }
    
}
