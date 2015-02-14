package idhack2015.com.walimu.fragments;

/**
 * Created by Isaac on 2/14/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import idhack2015.com.walimu.R;
import idhack2015.com.walimu.ViewPagerAdapter;
import idhack2015.com.walimu.tabs.SlidingTabLayout;


public class ViewPagerFragment extends Fragment {

    private View mLayout;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingLayout;

    public static ViewPagerFragment newInstance() {
        ViewPagerFragment frag = new ViewPagerFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_main, container, false);
        return mLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initializePager();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Set up the ViewPager and sliding tab indicator
     */
    public void initializePager() {
        mViewPager = (ViewPager) mLayout.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), (Activity) getActivity()));
        mSlidingLayout = (SlidingTabLayout) mLayout.findViewById(R.id.sliding_tabs);
        mSlidingLayout.setViewPager(mViewPager);
    }
}
