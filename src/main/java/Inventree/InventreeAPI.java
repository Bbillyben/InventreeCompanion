/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventree;

import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import javax.naming.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONObject;

/** Class dedicated to API request to Inventree Server
 *
 * @author blegendre
 */
public class InventreeAPI {
    


    
    public static String METHOD_GET="GET";
    public static String METHOD_POST="POST";
    

    
    /**Get the token from Invetree server by passing username and password
     * 
     * from API : /api/user/token/
     * @param invUrl : url of Inventree Server
     * @param username
     * @param password
     * @return 
     * @throws javax.naming.AuthenticationException 
     * @throws java.io.IOException 
     */
    public static String requestToken(String invUrl, String username, String password) throws AuthenticationException, IOException{
        System.out.println("URL : "+invUrl+ " / user : "+ username + " / pass : "+password);
        String userAuth = username+":"+password;
        String headerAuth = "Basic " + Base64.getEncoder().encodeToString(userAuth.getBytes());
        Request re = buildQuery(invUrl, "/api/user/token/", METHOD_GET,  headerAuth, null, null);
        Response response = re.fetch();
        return check(response.status()) ? new JSONObject(response.body()).getString("token") :  null;  
            
          
    }
    
    /* =================================================================== */
    /* --------------------   FOR STOCK ITEMS  --------------------------- */
    /* =================================================================== */
    
    /**Get Information Of Stock items, 
     * 
     * @param invUrl
     * @param token
     * @param searchItem object map with key : search terme (eg name, batch) and value : the value searched
     *                    could be set as null to get all stock items
     * @return
     *     - success (200) : JSONArray with all stock item descripiont
     *     - fail : throw error
     *     - IOException : null
     * @throws AuthenticationException 
     * @throws java.io.IOException 
     */
    public static JSONArray requestStockInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl, "/api/stock/", METHOD_GET,  "Token "+token, searchItem, null);  
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;  
        
    }
    /**Get the information of a specific stock item
     * 
     * API : /api/stock/{partNumber}/
     * 
     * @param invUrl
     * @param token
     * @param partNumber the stock item id (aka pk)
     * @return
     *     - success (200) : JSONObject with all stock item descripiont
     *     - fail : throw error
     *     - IOException : null
     * @throws AuthenticationException 
     * @throws java.io.IOException 
     */
     public static JSONObject requestStockItemInfo(String invUrl, String token, int partNumber) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl, "/api/stock/"+String.valueOf(partNumber)+"/", METHOD_GET,  "Token "+token, null, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONObject(response.body()) :  null;
        
    }
     
     /**Add a quantity to a specific StockItem (update a current stock item)
      * 
      * API : /api/stock/add/
      * 
      * @param invUrl
      * @param token
     * @param quantityMap map <Integer, Integer> where key is Stock item id and value is quantity
      * @throws AuthenticationException 
     * @throws java.io.IOException 
      */
     public static int addToStockItem(String invUrl, String token, Map<Integer, Integer> quantityMap) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl, "/api/stock/add/", METHOD_POST,  "Token "+token, null, quantityMap);             
        Response response =re.fetch();
        int status =response.status();
        return check(status) ? status :  null;
    }
     /**Remove  a quantity to a specific StockItem
      * 
      * API : /api/stock/remove/
      * 
      * @param invUrl
      * @param token
     * @param quantityMap map <Integer, Integer> where key is Stock item id and value is quantity
     * @return int status of the http request if not failed or null ??
      * @throws AuthenticationException 
     * @throws java.io.IOException 
      */
     public static int removeToStockItem(String invUrl, String token, Map<Integer, Integer> quantityMap) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl, "/api/stock/remove/", METHOD_POST,  "Token "+token, null, quantityMap);             
        Response response =re.fetch();
        int status = response.status();
        return check(status) ? status :  null;
    }
     /** Add  an item to stock from part description (not update
      * 
      * @param invUrl
      * @param token
      * @param data : formated JSON of part adding
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static int addItemToStock(String invUrl, String token, JSONObject data) throws AuthenticationException, IOException{
        //System.out.println("Inventree.InventreeAPI.addItemToStock() data :\n"+data);
        Request re = buildQuery(invUrl, "/api/stock/", METHOD_POST,  "Token "+token, data);             
        Response response =re.fetch();
        int status = response.status();
        return check(status) ? status :  null;
         
     }
     
     /** Transfert  an item to stock from part item list
      * 
      * @param invUrl
      * @param token
      * @param data : formated JSON of part transfert
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static int transfertItemToStock(String invUrl, String token, JSONObject data) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl, "/api/stock/transfer/", METHOD_POST,  "Token "+token, data);             
        Response response =re.fetch();
        int status = response.status();
        return check(status) ? status :  null;
     }
    
    /* =================================================================== */
    /* ------------------------------   FOR PART ------------------------- */
    /* =================================================================== */
    
    /**Get part part info from number
     * 
     * API : /api/part/{partNumber}
     * 
     * @param invUrl
     * @param token
     * @param partNumber the id of the part
     * @return 
     *     - success (200) : JSONArray with all parts descripiont
     *     - fail : throw error
     *     - IOException : null
     * @throws AuthenticationException 
     * @throws java.io.IOException 
     */
    public static JSONArray requestPartInfo(String invUrl, String token, int partNumber) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/part/"+String.valueOf(partNumber)+"/", METHOD_GET,  "Token "+token, null, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray("["+response.body()+"]"):  null;
    }
     /**Get part Information from search
     * 
     * API : /api/part/{partNumber}
     * 
     * @param invUrl
     * @param token
     * @param searchItem a map of serache terme (key) by value
     * @return 
     *     - success (200) : JSONArray with all parts descripiont
     *     - fail : throw error
     *     - IOException : null
     * @throws AuthenticationException 
     * @throws java.io.IOException 
     */
     public static JSONArray requestPartInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/part/", METHOD_GET,  "Token "+token, searchItem, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
     
    /**Add a new PART to the serveur
     * 
     * @param invUrl
     * @param token
     * @param jso : formatted JSON of part see requirement un API-DOC
     * @return
     * @throws AuthenticationException
     * @throws IOException 
     */
    public static JSONObject addPart(String invUrl, String token, JSONObject jso) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/part/", METHOD_POST,  "Token "+token, jso);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONObject(response.body()) :  null;
    }
     
     
     
    
    /* =================================================================== */
    /* -------------------------   FOR Category ------------------------- */
    /* =================================================================== */
    public static JSONArray requestCategoryInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/part/category/", METHOD_GET,  "Token "+token, searchItem, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
     
    /* =================================================================== */
    /* ---------------   FOR MANUFACTURER a SUPPLIER  -------------------- */
    /* =================================================================== */
     
     /**Get the full list of Manufacturer info
     * 
     * API : /api/company/part/manufacturer/
     * 
     * @param invUrl
     * @param token
     * @param searchItem a map of serache terme (key) by value
     * @return JSON Array of stock location
     * @throws IOException
     * @throws AuthenticationException 
     */ 
     public static JSONArray requestManufacturerInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/company/part/manufacturer/", METHOD_GET,  "Token "+token, searchItem, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
     
    /**Get the full list of Supplier info
     * 
     * API : /api/company/part/supplier/
     * 
     * @param invUrl
     * @param token
     * @param searchItem a map of serache terme (key) by value
     * @return JSON Array of stock location
     * @throws IOException
     * @throws AuthenticationException 
     */ 
     public static JSONArray requestSupplierInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/company/part/supplier/", METHOD_GET,  "Token "+token, searchItem, null);  
        //System.out.println("-------------------------------------- > "+re.uri());
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
     
     /**retrieve information from Company
      * 
      * API /api/company/
      * 
      * @param invUrl
      * @param token
      * @param searchItem
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static JSONArray requestCompanyInfo(String invUrl, String token, Map<String, String> searchItem ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/company/", METHOD_GET,  "Token "+token, searchItem, null);  
        //System.out.println("-------------------------------------- > "+re.uri());
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
     
     /**retrieve a specific part information from Company
      * 
      * @param invUrl
      * @param token
      * @param pk the identifier of the Company part (supplyer)
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static JSONObject requestPartCompanyInfo(String invUrl, String token, Integer pk ) throws AuthenticationException, IOException{
        Request re = buildQuery(invUrl,"/api/company/part/"+String.valueOf(pk)+"/", METHOD_GET,  "Token "+token, null, null);  
        Response response =re.fetch();
        return check(response.status()) ? new JSONObject(response.body()) :  null;
    }
     /**Add a part to a supplier
      * 
      * @param invUrl
      * @param token
      * @param jso JSON formatted see api-doc https://inventree.legendre-ratajczak.fr/api-doc/#company-part-create
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static int addSupplierPart(String invUrl, String token, JSONObject jso) throws AuthenticationException, IOException{
         
         Request re = buildQuery(invUrl,"/api/company/part/", METHOD_POST,  "Token "+token, jso);  
         Response response =re.fetch();
         int status = response.status();
         return check(status) ? status :  0;
     }
     
     /**Add a part to a manufacturer
      * 
      * @param invUrl
      * @param token
      * @param jso JSON formatted see api-doc https://inventree.legendre-ratajczak.fr/api-doc/#company-part-manufacturer-read
      * @return
      * @throws AuthenticationException
      * @throws IOException 
      */
     public static int addManufacturerPart(String invUrl, String token, JSONObject jso) throws AuthenticationException, IOException{
         Request re = buildQuery(invUrl,"/api/company/part/manufacturer/", METHOD_POST,  "Token "+token, jso);  
         Response response =re.fetch();
         int status = response.status();
         return check(status) ? status :  0;
     }
     
     
     
     /* =================================================================== */
    // --------------------------------- FOR STOCK LOCATION -------------------- //
     /* =================================================================== */
     
     
    /**Get the full list of stocks location
     * 
     * @param invUrl inventree server URL
     * @param token the token to identify user
     * @return JSON Array of stock location
     * @throws IOException
     * @throws AuthenticationException 
     */ 
    public static JSONArray getStockLocation(String invUrl, String token) throws IOException, AuthenticationException{
        Request re = buildQuery(invUrl,"/api/stock/location/", METHOD_GET,  "Token "+token, null, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray(response.body()) :  null;
    }
    /**Get information of a specific stock location
     * 
     * @param invUrl
     * @param token
     * @param locationId the id of the location
     * @return JSON Array of stock location
     * @throws IOException
     * @throws AuthenticationException 
     */ 
    public static JSONArray getStockLocation(String invUrl, String token, int locationId) throws IOException, AuthenticationException{
        Request re = buildQuery(invUrl,"/api/stock/location/"+String.valueOf(locationId)+"/", METHOD_GET,  "Token "+token, null, null);             
        Response response =re.fetch();
        return check(response.status()) ? new JSONArray("[" + response.body()+"]") :  null;
    }
    
    /* =================================================================== */
    /* -------------------------   FOR Barcode   ------------------------- */
    /* =================================================================== */
    /**Search for a specific barcode,
     * 
     * @param invUrl
     * @param token
     * @param barcode
     * @return JSONObject o fthe object found or null
     * @throws IOException
     * @throws AuthenticationException 
     */
    public static JSONObject getBarcodeInfo(String invUrl, String token, String barcode) throws IOException, AuthenticationException{
        JSONObject bcData = new JSONObject();
        bcData.put("barcode", barcode);
        Request re = buildQuery(invUrl,"/api/barcode/", METHOD_POST,  "Token "+token, bcData);             
        Response response =re.fetch();
        //System.out.println("Inventree.InventreeAPI.getBarcodeInfo()  \n   -- response "+response);
        return check(response.status()) ? new JSONObject(response.body()):  null;
    }
    /**Assign a barcode to : 
     * 'part', 'stockitem', 'stocklocation', 'supplierpart'
     * 
     * @param invUrl
     * @param token
     * @param bcAssJSO structure : { "part":id, "barcode":bc }
     * @return
     * @throws IOException
     * @throws AuthenticationException 
     */
    public static int assignBarcode(String invUrl, String token, JSONObject bcAssJSO) throws IOException, AuthenticationException{
        Request re = buildQuery(invUrl,"/api/barcode/link/", METHOD_POST,  "Token "+token, bcAssJSO);             
        Response response =re.fetch();
        int status = response.status();
        return check(status) ? status :  0;
    }
        
    /* =================================================================== */
    // ------------------ gestion des status des réponses ----------------- //
    /* =================================================================== */
    
    public static boolean check(int status) throws AuthenticationException{
         switch (status) {
                case 400:
                    return false;
                case 401: // authentication error
                    throw new AuthenticationException("Invalid Credential") ;
                case 404: // not found
                    return false;
                case 200: // connection ok
                case 201: // action ok
                    return true;
                default: // other status not yet handled
                    throw new AuthenticationException("Un Handled error ( status :"+status + ")") ;
            }
    }
    
    /* =================================================================== */
    // ------------------------- QUERY BUILDERs -------------------------- //
    /* =================================================================== */
    /**bUILD Query for API call
     * 
     * @param invUrl
     * @param APIPath
     * @param method
     * @param authStr
     * @return 
     */
    protected static Request buildQuery(String invUrl, String APIPath,String method, String authStr){
        // clean of the url and force https 
        String urlI = invUrl;
         
         // base request : url + path + header (type of data and authentication)
        JdkRequest jdkr = new JdkRequest(urlI);
            Request jdR = jdkr.uri().path(APIPath).back()
                .method(method)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", authStr);            
            return jdR;
    }
    protected static Request buildQuery(String invUrl, String APIPath,String method, String authStr, Map<String, String> searchItem, Map<Integer, Integer> quantityMap){
        // clean of the url and force https 

            Request jdR = buildQuery(invUrl, APIPath,method, authStr);
            // add alls params from searchItem Map
            if(searchItem != null){
                for (Map.Entry<String, String> entry : searchItem.entrySet()) {
                    jdR =  jdR.uri().queryParam(entry.getKey(),  entry.getValue()).back();
                }                
            }
            if(quantityMap != null){
                JSONObject jso = new JSONObject();
        
                for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
                    jso.append("items", createItemQuantity(entry.getKey(), entry.getValue()));
                }      
                jdR =  jdR.body().set(jso.toString()).back();
            }
            return jdR;
    }
    protected static Request buildQuery(String invUrl, String APIPath,String method, String authStr, JSONObject data){
        // clean of the url and force https 
            Request jdR = buildQuery(invUrl, APIPath,method, authStr);
            jdR =  jdR.body().set(data.toString()).back();
            return jdR;
    }
    
      /* =================================================================== */
    // ---------------------------  UTILITAIRES  ------------------------ //
    /* =================================================================== */
    /**Create a json object with quantity
     * 
     * @param partNumber
     * @param quantity
     * @return 
     */
    public static JSONObject createItemQuantity(int partNumber, int quantity){
        JSONObject it = new JSONObject();
        it.put("pk", partNumber)
            .put("quantity", quantity);
        return it;
    }
    
    
    
    
    
    
}
