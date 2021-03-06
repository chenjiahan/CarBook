package com.Doric.CarBook.member;

import android.content.Context;
import com.Doric.CarBook.Constant;
import com.Doric.CarBook.utility.DatabaseHelper;
import com.Doric.CarBook.utility.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {

    //服务器请求相关变量
    private static String loginURL = Constant.BASE_URL + "/login.php"; //登录请求的url,务必加上http://或https://
    private static String registerURL = Constant.BASE_URL + "/register.php";
    private static String loginTag = "login";
    private static String registerTag = "register";
    DatabaseHelper db;
    private JSONParser jsonParser;

    // constructor
    public UserFunctions(Context context) {
        jsonParser = new JSONParser();
        db = new DatabaseHelper(context);
    }

    /**
     * function make Login Request
     *
     * @param username
     * @param password
     */
    public JSONObject loginUser(String username, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", loginTag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        addUserToDatabase(json);
        return json;
    }

    /**
     * function make Login Request
     *
     * @param name
     * @param email
     * @param password
     */
    public JSONObject registerUser(String name, String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", registerTag));
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        addUserToDatabase(json);
        // return json
        return json;
    }

    public void addUserToDatabase(JSONObject json) {
        if (json != null) {
            try {
                if (json.getString("success").equals("1")) {
                    db.addUser(json.getString("username"),
                            Integer.parseInt(json.getString("user_id")),
                            json.getString("created_at"));
                    db.addUserIdToCollection(Integer.parseInt(json.getString("user_id")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function get Login status
     */
    public boolean isUserLoggedIn() {
        int count = db.getUserCount();
        if (count > 0) {
            // user logged in
            return true;
        }
        return false;
    }

    /**
     * Function to logout user
     * Reset Database
     */
    public boolean logoutUser() {
        db.resetCollection();
        db.resetTables();
        return true;
    }


    public String getUsername() {
        return db.getUserDetails().get(DatabaseHelper.KEY_USER_NAME);
    }

    public int getUserId() {
        return Integer.parseInt(db.getUserDetails().get(DatabaseHelper.KEY_USER_ID));
    }

    //TODO 收藏
    public boolean addToCollection(int carId) {
        if (isUserLoggedIn()) {
            return (db.addCollection(getUserId(), carId) > -1);
        } else {
            return (db.addCollection(carId) > -1);
        }
    }
    public boolean cancelCollect(int carId) {
        if (isUserLoggedIn()) {
            return (db.deleteCollection(getUserId(), carId) > 0);
        } else {
            return (db.deleteCollection(carId) > 0);
        }
    }

    public boolean isCollected(int carId) {
        return db.isCarCollected(carId);
    }
}
