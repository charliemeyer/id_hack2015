package idhack2015.com.walimu.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import idhack2015.com.walimu.R;
import idhack2015.com.walimu.utilities.Commcare_api;

/**
 * @author team on 2/14/2015.
 */
public class InvestigateActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigate);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.TV_investigate_main)).setText(intent.getExtras().getString("time"));
        Toast.makeText(this, "you're awesome!", Toast.LENGTH_LONG).show();

        DownloadTask task = new DownloadTask((TextView) findViewById(R.id.TV_investigate_main));
        task.execute();
    }

    private class DownloadTask extends AsyncTask<HashMap<String, String>, String, String> {
        private TextView textView;

        public DownloadTask(TextView tv) {
            textView = tv;
        }

        protected String doInBackground(HashMap<String, String>... optionsMap) {
            Log.d("DownloadTask", "at least we made it this far");
            Commcare_api api = new Commcare_api("sims.app@walimu.org", "UQ7q'4z3*W", "0");
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("gender", "female");
            return api.get_cases("walimu-sandbox", options);
        }

        protected void onProgressUpdate(String... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            textView.setText(result);
            Toast.makeText(InvestigateActivity.this, "JSON!", Toast.LENGTH_SHORT).show();
        }
    }
}
