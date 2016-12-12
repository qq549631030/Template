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
public class FavoriteFragment extends BaseFragment {



    TextView text;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        text = (TextView) view.findViewById(R.id.text);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
