package com.sharnoxz.vacxinity.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sharnoxz.vacxinity.R;
import com.sharnoxz.vacxinity.Scheduling;
import com.sharnoxz.vacxinity.Vaccination;
import com.sharnoxz.vacxinity.accounts.SharedPrefs;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    CardView cardView1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cardView1 = root.findViewById(R.id.CardView1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = Boolean.valueOf(SharedPrefs.readSharedSetting(getContext(),"Scheduling","true"));
                if(check) {
                    Intent intent = new Intent(getContext(), Scheduling.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getContext(), Vaccination.class);
                    startActivity(intent);

                }
            }
        });
        return root;
    }
}
