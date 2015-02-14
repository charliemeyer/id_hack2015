package idhack2015.com.walimu;

/**
 * Created by Isaac on 2/14/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ErrorFragment extends Fragment {
    private final static String TAG = "ErrorFragment";

    public static Fragment newInstance() {
        Log.e(TAG, "We've create an extra page");
        return new ErrorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_error, container, false);
        return v;
    }
}
