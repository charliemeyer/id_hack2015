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

    private final String TAG = "Commcare_api";
    private final String CC_BASE_URL = "https://www.commcarehq.org/a";

    private String version = "0.5";
    private String user = null; // user type?
    private String password = null; // user type?
    private String last_response = null; // user type?
    private String last_request = null; // user type?

    /*
    TODO: types for all of the params
     */

    public Commcare_api(String user, String password, String version) {
        version = "0.5"; // we don't actually care what they pass in.

        this.user = user;
        this.password = password;
        this.version = "v#" + version;
        this.last_response = null;
        this.last_request = null;
    }

//    public String get_next_data() {
//        return get_contiguous_data("next");
//    }
//
//    public String get_previous_data() {
//        return get_contiguous_data("previous");
//    }

    public String get_cases(String domain, HashMap<String, String> options) {
        String url = build_url(domain, "case", options);
        return get_request(url);
    }

    public String get_case(String domain, int case_id, HashMap<String, String> options) {
        String url = build_url(domain, "case/" + case_id, options);
        return get_request(url);
    }

    public String get_forms(String domain, HashMap<String, String> options) {
        String url = build_url(domain, "form", options);
        return get_request(url);
    }

    public String get_form (String domain, int form_id, HashMap<String, String> options) {
        String url = build_url(domain, "form/" + form_id, options);
        return get_request(url);
    }

    public String get_groups(String domain, HashMap<String, String> options ) {
        String url = build_url(domain, "group", options);
        return get_request(url);
    }

    public String get_mobile_workers(String domain, HashMap<String, String> options) {
        String url = build_url(domain, "user", options);
        return get_request(url);
    }

    public String get_mobile_worker(String domain, int user_id, HashMap<String, String> options) {
        String url = build_url(domain, "user/" + user_id, options);
        return get_request(url);
    }

    public String get_web_users(String domain) {
        String url = build_url(domain, "web-user", null);
        return get_request(url);
    }

    public String get_web_user(String domain, int user_id) {
        String url = build_url(domain, "web-user/" + user_id, null);
        return get_request(url);
    }

    public String get_application_structure(String domain) {
        String url = build_url(domain, "application", null);
        return get_request(url);
    }

    public String get_data_forwarding(String domain) {
        String url = build_url(domain, "data-forwarding", null);
        return get_request(url);
    }

    public String get_fixtures(String domain) {
        String url = build_url(domain, "fixture", null);
        return get_request(url);
    }

    public String get_fixture(String domain, int type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("fixture_type", type + "");
        String url = build_url(domain, "fixture", map);
        return get_request(url);
    }

    public String get_fixture_item(String domain, int item_id) {
        String action = "fixture/" + item_id;
        String url = build_url(domain, action, null);
        return get_request(url);
    }

    /**
     * @return the JSON string from the request
     */
    public String get_request(String url) {
        String response = null;
        try {

            // make dat http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            // do the request
            HttpGet httpGet = authGet(url);

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // TODO: so what's the deal with saving the stuff
            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "response = '" + response + "'");
        this.last_response = response;
        return response;
    }

    private HttpGet authGet(String url) {
        HttpGet get = new HttpGet(url);
        String source = user + ":" + password;
        String auth= "Basic " + Base64.encodeToString(source.getBytes(),
                Base64.URL_SAFE | Base64.NO_WRAP);
        get.setHeader("Authorization", auth);
        return get;
    }

    /**
     * Builds a url, e.g. https://www.commcarehq.org/a/[domain]/api/[version]/case/[options]
     * @param domain
     * @param action
     * @param options
     * @return the url (String)
     */
    public String build_url(String domain, String action, HashMap<String, String> options) {
        String url = CC_BASE_URL + "/" + domain + "/api/" + version + "/" + action;
        String optionString = null;
        if (options != null) {
            for (String key : options.keySet()) {
                optionString += key + "=" + options.get(key) + "&";
            }
        }
        if (optionString != null) {
            // add options to url
            url += "?" + optionString;

            // remove trailing &
            url = url.substring(0, url.length() - 1); // TODO THIS MIGHT BE WRONG
            Log.d(TAG, "url = '" + url + "'");
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
