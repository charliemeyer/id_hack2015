package idhack2015.com.walimu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        Spinner spinnerTimeFrame = (Spinner) getActivity().findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.timeFrame));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.fragment_investigate);
        // Apply the adapter to the spinner
        spinnerTimeFrame.setAdapter(adapter);

        Spinner spinnerCondition = (Spinner) getActivity().findViewById(R.id.spinner2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.condition));
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(R.layout.fragment_investigate);
        // Apply the adapter to the spinner
        spinnerCondition.setAdapter(adapter2);


        return mLayout;
    }

    public void setOptions(View view){
        Spinner timeframe = (Spinner) getActivity().findViewById(R.id.spinner);
        Spinner condition = (Spinner) getActivity().findViewById(R.id.spinner2);
        switch(getActivity().findViewById(R.id.radioDischargeGroup)) {
            case getActivity().findViewById(R.id.dischargeAll).isChecked():
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
        int dischargeId = getActivity().findViewById(R.id.radioDischargeGroup).getCheckedRadioButtonId();
        int genderId = radioGenderGroup.getCheckedRadioButtonId();
        int hivStatusId = radioHivStatusGroup.getCheckedRadioButtonId();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
