package idhack2015.com.walimu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * @author Aaron on 2/14/2015.
 */
public class MonitorFragment extends Fragment {

    private final String TAG = "MonitorFragment";
    private View mLayout;

    public static MonitorFragment newInstance() {
        MonitorFragment frag = new MonitorFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_monitor, container, false);
        return mLayout;
    }
    public void setTimeFrame(View view){
        SeekBar timeFrame = (SeekBar) findViewById(R.id.timeFrame);
        onProgressChanged()
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
