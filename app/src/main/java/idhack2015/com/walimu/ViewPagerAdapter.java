package idhack2015.com.walimu;

/**
 * @author Aaron on 2/14/2015.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import idhack2015.com.walimu.fragments.ErrorFragment;
import idhack2015.com.walimu.fragments.InvestigateFragment;
import idhack2015.com.walimu.fragments.MonitorFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    /** List of fragments to fill the pages */
    @SuppressWarnings("unused")
    private final String TAG = "ViewPagerAdapter";

    /** To be passed to the fragments' list adapter for callback purposes */
    private Activity mActivity;

    public ViewPagerAdapter(FragmentManager fragmentManager, Activity activity) {
        super(fragmentManager);
        mActivity = activity;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) { // Event list
            return "Monitor";
        } else if (position == 1) { // Restaurant list
            return "Investigate";
        } else { // should never happen
            return "awkward...";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) { // Monitor
            return MonitorFragment.newInstance();
        } else if (position == 1) { // Investigate
            return InvestigateFragment.newInstance();
        } else { // should never happen
            return ErrorFragment.newInstance();
        }
    }

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return 2;
    }
}