package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseFragment;




/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {



    TextView text;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        text = (TextView) view.findViewById(R.id.text);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
