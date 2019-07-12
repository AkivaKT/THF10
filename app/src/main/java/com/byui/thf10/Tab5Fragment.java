package com.byui.thf10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by User on 2/28/2017.
 */

public class Tab5Fragment extends Fragment {
    private static final String TAG3 = "Tab5Fragment";

    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5_fragment,container2,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST5);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 5", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
