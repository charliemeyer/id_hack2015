package idhack2015.com.walimu.utilities;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author Aaron on 2/14/2015.
 */
public class Commcare_api {

//    require "commcare_api/version"
//    require "httpi"
//    require "httpclient"
//    require "json"

    private static final String TAG = "Commcare_api";
    private static final String CC_BASE_URL = "https://www.commcarehq.org/a";

    private static String version = "v0.5";
    private String user = null; // user type?
    private String password = null; // user type?
    private String last_response = null; // user type?
    private String last_request = null; // user type?

    /*
    TODO: types for all of the params
     */

//    public Commcare_api(String user, String password, String version) {
//        version = "0.5"; // we don't actually care what they pass in.
//
//        this.user = user;
//        this.password = password;
//        this.version = "v" + version;
//        this.last_response = null;
//        this.last_request = null;
//    }

//    public String get_next_data() {
//        return get_contiguous_data("next");
//    }
//
//    public String get_previous_data() {
//        return get_contiguous_data("previous");
//    }

    public static String get_cases_url (String domain, HashMap<String, String> options) {
        return build_url(domain, "case", options);
    }

    public static String get_case_url (String domain, int case_id, HashMap<String, String> options) {
        return build_url(domain, "case/" + case_id, options);
    }

    public static String get_forms_url (String domain, HashMap<String, String> options) {
        return build_url(domain, "form", options);
    }

    public static String get_form_url (String domain, String form_id, HashMap<String, String> options) {
        return build_url(domain, "form/" + form_id, options);
    }

    public static String get_groups_url (String domain, HashMap<String, String> options ) {
        return build_url(domain, "group", options);
    }

    public static String get_mobile_workers_url (String domain, HashMap<String, String> options) {
        return build_url(domain, "user", options);
    }

    public static String get_mobile_worker_url (String domain, int user_id, HashMap<String, String> options) {
        return build_url(domain, "user/" + user_id, options);
    }

    public static String get_web_users_url (String domain) {
        return build_url(domain, "web-user", null);
    }

    public static String get_web_user_url(String domain, int user_id) {
        return build_url(domain, "web-user/" + user_id, null);
    }

    public static String get_application_structure_url (String domain) {
        return build_url(domain, "application", null);
    }

    public static String get_data_forwarding_url (String domain) {
        return build_url(domain, "data-forwarding", null);
    }

    public static String get_fixtures_url (String domain) {
        return build_url(domain, "fixture", null);
    }

    public static String get_fixture_url (String domain, int type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("fixture_type", type + "");
        return build_url(domain, "fixture", map);
    }

    public static String get_fixture_item_url (String domain, int item_id) {
        String action = "fixture/" + item_id;
        return build_url(domain, action, null);
    }

    /**
     * @return the JSON string from the request
     */
    public static String get_request(String url, String user, String password) {
        String response = null;
        try {

            // make dat http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            // do the request
            HttpGet httpGet = authGet(url, user, password);

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

            // TODO: so what's the deal with caching data?
            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "response = '" + response + "'");
//        this.last_response = response;
        return response;
    }

    private static HttpGet authGet(String url, String user, String password) {
        HttpGet get = new HttpGet(url);
        String source = user + ":" + password;
        String auth= "Basic " + Base64.encodeToString(source.getBytes(),
                Base64.URL_SAFE | Base64.NO_WRAP);
        get.setHeader("Authorization", auth);
        Log.d(TAG, "httpget: " + get.getAllHeaders().toString());
        return get;
    }

    /**
     * Builds a url, e.g. https://www.commcarehq.org/a/[domain]/api/[version]/case/[options]
     * @param domain
     * @param action
     * @param options
     * @return the url (String)
     */
    public static String build_url(String domain, String action, HashMap<String, String> options) {
        boolean first = true;

        String url = CC_BASE_URL + "/" + domain + "/api/" + version + "/" + action + "/";
        if (options != null) {
            for (String key : options.keySet()) {
                if (key.equals("") == false) {
                    url += ((first) ? "?" : "") + key + "=" + options.get(key) + "&";
                } else {
                    url += options.get(key) + "&";
                }
                first = false;
            }
            // remove trailing &
            url = url.substring(0, url.length() - 1);
        }

        Log.d(TAG, "url = '" + url + "'");
        return url;
    }

// TODO: I doubt the value of this kind of paging for this small project lmao
//    def get_contiguous_data(direction)
//    return nil if @last_response.nil?
//    j = JSON.parse(@last_response.body)
//            return nil if j["meta"][direction].nil?
//    @last_request.url.query = j["meta"][direction].gsub(/\?/, "")
//    url = @last_request.url.to_s
//          get_request(url)
//    end
//
//    end # Class
//
//    end # Module

}
