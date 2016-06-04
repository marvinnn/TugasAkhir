package com.revmedia.tugasakhir;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.revmedia.tugasakhir.R;

/**
 * Created by Marvin Zeson on 1/26/2016.
 */

public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        Button buttonGunung = (Button)view.findViewById(R.id.buttonGunung);
        Button buttonPantai = (Button)view.findViewById(R.id.buttonPantai);
        Button buttonCandi = (Button)view.findViewById(R.id.buttonCandi);
        Button buttonHotel = (Button)view.findViewById(R.id.buttonHotel);
        Button buttonKuliner = (Button)view.findViewById(R.id.buttonKuliner);
        buttonGunung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stringCategory", "gunung");
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        buttonPantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stringCategory", "pantai");
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        buttonCandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stringCategory", "candi");
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        buttonHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stringCategory", "hotel");
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        buttonKuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("stringCategory", "kuliner");
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
        return view;
    }


}
