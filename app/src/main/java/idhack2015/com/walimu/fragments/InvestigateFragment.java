package idhack2015.com.walimu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.HashMap;

import idhack2015.com.walimu.R;
import idhack2015.com.walimu.activities.InvestigateActivity;

/**
 * @author Aaron on 2/14/2015.
 */
public class InvestigateFragment extends Fragment {

    private final String TAG = "InvestigateFragment";
    private View mLayout;

    public static InvestigateFragment newInstance() {
        InvestigateFragment frag = new InvestigateFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_investigate, container, false);

        Spinner spinnerTimeFrame = (Spinner) mLayout.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.timeFrame, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTimeFrame.setAdapter(adapter);

        Spinner spinnerCondition = (Spinner) mLayout.findViewById(R.id.spinner2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.condition));
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCondition.setAdapter(adapter2);

        setUpSubmitButton();

        return mLayout;
    }

    public void setUpSubmitButton(){
        mLayout.findViewById(R.id.buttonInvestigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> options = new HashMap<String, String>();

                Spinner tframe = (Spinner) getActivity().findViewById(R.id.spinner);
                Spinner cond = (Spinner) getActivity().findViewById(R.id.spinner2);
                String tFrame = tframe.getSelectedItem().toString();
                String condition = cond.getSelectedItem().toString();
                options.put("condition", condition);

                int timeFrame = getTimeFrame(tFrame);
                Log.d(TAG, "timeFrame = " + timeFrame);
                options.put("timeFrame", timeFrame + "");

                if (((RadioButton) getActivity().findViewById(R.id.dischargeAll)).isChecked()) {
//                    dischargeType = 0;
                    // do nothing, courtesy of dkane
                } else if (((RadioButton) getActivity().findViewById(R.id.dischargeAlive)).isChecked()) {
//                    dischargeType = 1;
                    options.put("discharge_status", "alive");
                } else {
//                    dischargeType = 2;
                    options.put("discharge_status", "dead");
                }
                if (((RadioButton) getActivity().findViewById(R.id.genderAll)).isChecked()) {
//                    genderType = 0;
                    // do nothing, courtesy of dkane
                } else if (((RadioButton) getActivity().findViewById(R.id.genderMale)).isChecked()) {
//                    genderType = 1;
                    options.put("gender", "male");
                } else { //female
//                    dischargeType = 2;
                    options.put("gender", "female");
                }
                if (((RadioButton) getActivity().findViewById(R.id.hivAll)).isChecked()) {
//                    hivStatus = 0;
                } else if (((RadioButton) getActivity().findViewById(R.id.hivPos)).isChecked()) {
//                    hivStatus = 1;
                    options.put("hiv_status", "positive"); // TODO - correct key?
                } else { //negative HIV status
//                    hivStatus = 2;
                    options.put("hiv_status", "negative");
                }
                makeSearch(options);
            }
        });
    }

    public void makeSearch(HashMap<String, String> options){
        //show the results view
        Intent intent = new Intent(getActivity(), InvestigateActivity.class);
        intent.putExtra("options", options);
        getActivity().startActivity(intent);
    }

    private int getTimeFrame(String tFrame) {
        String[] timeFrameArray = getActivity().getResources().getStringArray(R.array.timeFrame);

        if (timeFrameArray[0].equals(tFrame)) {
            return 0;
        } else if (timeFrameArray[1].equals(tFrame)) {
            return 1;
        } else {
            return 2;
        }
    }

}
