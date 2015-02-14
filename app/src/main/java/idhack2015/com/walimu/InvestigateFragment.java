package idhack2015.com.walimu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

/**
 * @author Aaron on 2/14/2015.
 */
public class InvestigateFragment extends Fragment {

    private final String TAG = "InvestigateFragment";
    private View mLayout;

    public static MonitorFragment newInstance() {
        MonitorFragment frag = new MonitorFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_investigate, container, false);
        return mLayout;
    }

    public void setOptions(View view){
        Spinner timeframe = (Spinner) findViewById(R.id.timeframe);
        Spinner condition = (Spinner) findViewById(R.id.condition);

        int dischargeId = radioDischargeGroup.getCheckedRadioButtonId();
        int genderId = radioGenderGroup.getCheckedRadioButtonId();
        int hivStatusId = radioHivStatusGroup.getCheckedRadioButtonId();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
