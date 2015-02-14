package idhack2015.com.walimu.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import idhack2015.com.walimu.R;
import idhack2015.com.walimu.utilities.Commcare_api;

/**
 * @author team on 2/14/2015.
 */
public class InvestigateActivity extends ActionBarActivity {

    private static final String TAG = "InvestigateActivity";
    int ITERATION = 0;
    protected static ArrayList<JSONObject> forms = null;
    protected static HashMap<String, String> queryParams = null;

    protected static boolean isLastForm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigate);
        Intent intent = getIntent();
        forms = new ArrayList<JSONObject>();
        queryParams = (HashMap<String, String>)
                intent.getExtras().getSerializable("options");

        Toast.makeText(this, "you're awesome! - " + queryParams.get("timeFrame"), Toast.LENGTH_LONG).show();

        startQueries();
    }

    private void startQueries() {
        // query cases based on date (date modified) and gender
            // from those cases get "xform_ids" (list of every form associated with case)
        // query all forms with those IDs AND vital signs AND discharge status
        // now output stats based on the surviving forms

        int timeFrame = Integer.parseInt(queryParams.get("timeFrame"));
        long millis = 0;
        if (timeFrame == 0) { // 1 day
            millis = 1000 * 60 * 60 * 24;
        } else if (timeFrame == 1) { // 1 week
            millis = 1000 * 60 * 60 * 24 * 7;
        } else { // 1 month
            millis = 1000 * 60 * 60 * 24 * 31;
        }

        Date date = new Date();
        date.setTime(date.getTime() - millis);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String date_modified_start = cal.get(Calendar.YEAR) + "-"
                + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);

        HashMap<String, String> dateParams = new HashMap<String, String>();
        dateParams.put("date_modified_start", date_modified_start);

        String casesUrl = Commcare_api.get_cases_url("walimu-sandbox", dateParams);

//        String casesUrl = "https://www.commcarehq.org/a/walimu-sandbox/api/v0.5/case/"; // for now just get all cases

        new DownloadTask(casesUrl, ITERATION++, this).execute();
    }


        // URL to get contacts JSON
        // private static String url = "http://api.androidhive.info/contacts/";

    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String CASE_TYPE = "case_type";
    private static final String TAG_GENDER = "gender";
    private static final String START_DATE = "started_on";
    private static final String END_DATE = "ended_on";
    private static final String AGE = "age_questions.age";
    private static final String TAG_HIV_STATUS = "hiv_status";
    private static final String TAG_ADMISSION_DIAG = "admission_diagnosis";
    private static final String TAG_VITAL_SIGNS = "vital_signs";
    private static final String TAG_HR = "hr_measured";
    private static final String TAG_SBP = "sbp_measured";
    private static final String TAG_RR = "rr_measured";
    private static final String TAG_SPO2 = "spo2_measured";
    private static final String TAG_TEMP = "temp_measured";
    private static final String TAG_INFECTION = "suspected_infection";
    private static final String TAG_VITAL = "vital_sign_measurements";
    private static final String TAG_VITAL_RR = "vital_sign_measurements";
    private static final String TAG_DISCHARGE = "discharge_status";
    private static final String TAG_CASE_ID = "case_id";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_FORM_IDS = "xform_ids";
    private static final String TAG_OBJECTS = "objects";


    public void getFormIdsFromCases(String json) {
        ArrayList<String> formIds = new ArrayList<String>();
        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray cases = jsonObj.getJSONArray(TAG_OBJECTS);

                // looping through All Contacts
                for (int i = 0; i < cases.length(); i++) {
                    JSONObject singleCase = cases.getJSONObject(i);

                    boolean passedGender = true;
                    boolean passedHIV = true;
                    boolean passedDischarge = true;

                    JSONObject c = singleCase.getJSONObject("properties");

                    /*try {
                        // NARROW by (gender, discharge, and HIV
                        if (c.getString(TAG_GENDER).equals(queryParams.get("gender")) || queryParams.get("gender") == null) { // gender)
                            passedGender = true;
                        }
                    } catch (JSONException e) { // GENDER exception
                        Log.d(TAG, "GENDER exception");
//                        e.printStackTrace();
                    }*/
                    /*try {
                        if (c.getString(TAG_HIV_STATUS).equals(queryParams.get("hiv_status"))) { // HIV
                            passedHIV = true;
                        }
                    } catch (JSONException e) { // HIV exception
                        Log.d(TAG, "HIV exception");
//                        e.printStackTrace();
                    }*/
/*                    try {
                        if (c.getString(TAG_DISCHARGE).equals(queryParams.get("discharge_status"))) { // DISCHARGE
                            passedDischarge = true;
                        }
                    } catch (JSONException e) { // discharge exception
                        Log.d(TAG, "discharge exception");
//                        e.printStackTrace();
                    }*/
                    if (passedDischarge && passedGender && passedHIV) {
                        JSONArray myFormIds = singleCase.getJSONArray(TAG_FORM_IDS);
                        for (int j = 0; j < myFormIds.length(); j++) {
                            formIds.add(myFormIds.getString(j)); // add all the form ids for this case
                        }
                    }
                }
            } catch (JSONException e) {
                Log.d(TAG, "Full JSON Parse exception");
                e.printStackTrace();
            }
        } else {
            Log.e("JSONParser", "Couldn't get any data from the url");
        }
        Log.d(TAG, "FORM COUNT: " + formIds.size());
        for (int i = 0; i < formIds.size(); i++) {
            String formsUrl = Commcare_api.get_form_url("walimu-sandbox", formIds.get(i), null); // make a query for every xform_id!
            if (i == formIds.size() - 1) {
                isLastForm = true;
            }
            new DownloadTask(formsUrl, ITERATION, this).execute();
        }
        ITERATION++;
    }

    public void parseForms() {
        for (int i = 0; i < forms.size(); i++) {
            String[] conditions = getResources().getStringArray(R.array.condition);
            JSONObject form = forms.get(i);

            if (conditions[0].equals(queryParams.get("condition"))) { // shock

                try {
                    JSONObject vitalSigns = form.getJSONObject(TAG_VITAL);
                    if (vitalSigns.getInt(TAG_HR) <= 100 && vitalSigns.getInt(TAG_SBP) >= 90) { // fails shock
                        forms.remove(i);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException getting vitals from form");
//                    e.printStackTrace();
                };

            } else if (conditions[1].equals(queryParams.get("condition"))) { // septic shock
                try {
                    JSONObject vitalSigns = form.getJSONObject(TAG_VITAL);
                    if (vitalSigns.getInt(TAG_SBP) >= 90 || vitalSigns.getBoolean(TAG_INFECTION) == false
                            || (vitalSigns.getInt(TAG_SBP) <= 100  && vitalSigns.getInt(TAG_RR) <= 24
                            &&  vitalSigns.getInt(TAG_TEMP) <= 38 && vitalSigns.getInt(TAG_TEMP) >= 36 )) { // fails stress
                        forms.remove(i);
                    }
                } catch (JSONException e) {
                    ((TextView) findViewById(R.id.TV_investigate_main)).setText((String) ((TextView) findViewById(R.id.TV_investigate_main)).getText() + "JSONException getting vitals from form");
                    Log.d(TAG, "JSONException getting vitals from form");
//                    e.printStackTrace();
                };
            } else if (conditions[2].equals(queryParams.get("condition"))) { // severe respiratory distress

                try {
                    JSONObject vitalSigns = form.getJSONObject(TAG_VITAL);
                    if (vitalSigns.getInt(TAG_RR) <= 30 && vitalSigns.getInt(TAG_SPO2) >= 90) { // fails stress
                        forms.remove(i);
                    }
                } catch (JSONException e) {
                    ((TextView) findViewById(R.id.TV_investigate_main)).setText((String) ((TextView) findViewById(R.id.TV_investigate_main)).getText() + "JSONException getting vitals from form");
                    Log.d(TAG, "JSONException getting vitals from form");
//                    e.printStackTrace();
                };
            }
        }
        workWithFinalData();
    }

    public void workWithFinalData() {
        Log.d(TAG, "at least we made it this far");
        ((TextView) findViewById(R.id.TV_investigate_main)).setText((String) ((TextView) findViewById(R.id.TV_investigate_main)).getText() + "at least we made it this far");
        Toast.makeText(this, "# of forms = " + forms.size(), Toast.LENGTH_SHORT).show();
    }

//          HashMap<String, String> params = addVitalSignParams(options);
//          String casesUrl = "https://www.commcarehq.org/a/walimu-sandbox/api/v0.5/case/"; // for now just get all cases



    class DownloadTask extends AsyncTask<String, String, String> {
        private String mUrl;
        private int mIter;
        private Context mContext;

        public DownloadTask(String url, int inter, Context context) {
            mIter = inter;
            mUrl = url;
            mContext = context;
        }

        protected String doInBackground(String... options) {
            return Commcare_api.get_request(mUrl, "sims.app@walimu.org", "UQ7q'4z3*W");
        }

        protected void onProgressUpdate(String... progress) {
//            setProgressPercent(progress[0]);
        }

        public void onPostExecute(String results) {
            switch (mIter) {
                case 0: // getting cases by gender and date
                    Log.d("DownloadTask", mIter + " - JSON = " + results);
                    ((TextView) ((InvestigateActivity) mContext).findViewById(R.id.TV_investigate_main)).setText(((TextView) ((InvestigateActivity) mContext).findViewById(R.id.TV_investigate_main)).getText() + results);
                    ((InvestigateActivity) mContext).getFormIdsFromCases(results);
                    break;
                case 1: // we have one more form that we like
                    ((TextView) ((InvestigateActivity) mContext).findViewById(R.id.TV_investigate_main)).setText(((TextView) ((InvestigateActivity) mContext).findViewById(R.id.TV_investigate_main)).getText() + results);
                    Log.d("DownloadTask", mIter + " - JSON = " + results);
                    try {
                        JSONObject jsonObj = new JSONObject(results);
                        ((InvestigateActivity) mContext).forms.add(jsonObj); // TODO - DATE satisfies queryParams
                        if (((InvestigateActivity) mContext).isLastForm) {
//                            ((InvestigateActivity) mContext).parseForms();
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "JSON Parse exception on form");

                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
