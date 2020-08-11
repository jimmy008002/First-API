package helper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import dto.DTOResponseMessage;

public class JWTHelper extends AppTech{
	private final static String SERECT_KEY = "Freedy";
	int db_position = addDBConnection("project_db", "seto_common");
	public String createToken(String username, String password) {
		String token = "";
		//HMAC
		try {
			Date dt = new Date();
            Calendar c = Calendar.getInstance(); 
            c.setTime(dt); 
            c.add(Calendar.DATE, 1);
            dt = c.getTime();

            Algorithm algorithmHS = Algorithm.HMAC256(SERECT_KEY);
            token = JWT.create()
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withExpiresAt(dt)
                    .sign(algorithmHS);         
            
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    			dbClose();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    			dbClose();
		} 
		dbClose();
		return token;
	}
	
	 public Object checkToken(String token, Object obj){
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		if(db_position > -1) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SERECT_KEY);
            
            //check token is exist
            ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("SELECT * FROM user_auth, user WHERE user_auth.token = '" + token + "' AND user_auth.user_id = user.user");
            
            if(rows.size() > 0) {
            		
	            JWTVerifier verifier = JWT.require(algorithmHS)
	                    .withClaim("username", String.valueOf(rows.get(0).get("user_login")))
	                          .withClaim("password", String.valueOf(rows.get(0).get("user_password")))
	                          .build();
	                  DecodedJWT jwt = verifier.verify(token);
	                  Date expiresAt = jwt.getExpiresAt(); 
				  
				//responseMessage.set200Message();
	           dbClose(); 
	           return obj;
			} else {
				dbClose(); 
				responseMessage.set401Message();
		        return new Gson().toJson(responseMessage);
			}
            
        } catch (IllegalArgumentException ex) {
        		//responseMessage.set401Message();
        		dbClose();
        } catch (UnsupportedEncodingException ex) {
        		//responseMessage.set401Message();
        		dbClose();
        } catch (TokenExpiredException ex) {
        		responseMessage.set409Message();
        		dbClose();
            return new Gson().toJson(responseMessage);
        } catch (InvalidClaimException ex) {
        		responseMessage.set401Message();
        		dbClose();
            return new Gson().toJson(responseMessage);
        }
		}
        return 0;
	 }
	 
	 
	 /*
	 public int checkToken(String token){
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SERECT_KEY);
            
            //check token is exist
            ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("SELECT * FROM USRAUTH, USR WHERE USRAUTH.TOKEN = '" + token + "' AND USRAUTH.USR_ID = USR.USR_ID");
            
            if(rows.size() > 0) {
            		
	            JWTVerifier verifier = JWT.require(algorithmHS)
	                    .withClaim("username", String.valueOf(rows.get(0).get("USR_ACCOUNT")))
	                          .withClaim("password", String.valueOf(rows.get(0).get("USR_PASSWORD")))
	                          .build();
	                  DecodedJWT jwt = verifier.verify(token);
	                  jwt.getExpiresAt();
				  
				//responseMessage.set200Message();
	                 
			} else {
        			return -401;
			}
            
        } catch (IllegalArgumentException ex) {
        		//responseMessage.set401Message();
        } catch (UnsupportedEncodingException ex) {
        		//responseMessage.set401Message();
        } catch (TokenExpiredException ex) {
        		return -409;
        } catch (InvalidClaimException ex) {
    			return -401;
        }
        
        return 0;
	 }
	 */
	 
	 public int authToken(String token){
		if(db_position > -1) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SERECT_KEY);
            
            //check token is exist
            ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("SELECT * FROM user_auth, user WHERE user_auth.token = '" + token + "' AND user_auth.user_id = user.user_id");
            
            if(rows.size() > 0) {
            		
	            JWTVerifier verifier = JWT.require(algorithmHS)
	                    .withClaim("username", String.valueOf(rows.get(0).get("user_login")))
	                          .withClaim("password", String.valueOf(rows.get(0).get("user_password")))
	                          .build();
	                  DecodedJWT jwt = verifier.verify(token);
	                  jwt.getExpiresAt();
				  
				//responseMessage.set200Message();
	                 
			} else {
        			return -401;
			}
            
        } catch (IllegalArgumentException ex) {
        		//responseMessage.set401Message();
    			dbClose();
        } catch (UnsupportedEncodingException ex) {
        		//responseMessage.set401Message();
    			dbClose();
        } catch (TokenExpiredException ex) {
    			dbClose();
        		return -409;
        } catch (InvalidClaimException ex) {
    			dbClose();
    			return -401;
        }
		}
        dbClose();
        return 0;
	} 
}
