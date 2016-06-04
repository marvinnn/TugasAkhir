package com.revmedia.tugasakhir;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    View view;

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String getTitle = getArguments().getString("title");
        String getContent = getArguments().getString("content");
        String getCategory = getArguments().getString("category");
        view = inflater.inflate(R.layout.fragment_article, container, false);
        ImageView vCategory = (ImageView)view.findViewById(R.id.imageCardDetail);
        TextView vTitle = (TextView)view.findViewById(R.id.titleDetail);
        TextView vContent = (TextView)view.findViewById(R.id.txtContentDetail);
        vTitle.setText(getTitle);
        vContent.setText(getContent);
        if(getCategory.equals("gunung")){
            vCategory.setImageResource(R.drawable.gunung);
        }
        else if(getCategory.equals("pantai")){
            vCategory.setImageResource(R.drawable.pantai);
        }
        else if(getCategory.equals("candi")){
            vCategory.setImageResource(R.drawable.candi);
        }
        else if(getCategory.equals("hotel")){
            vCategory.setImageResource(R.drawable.hotel);
        }
        else if(getCategory.equals("kuliner")){
            vCategory.setImageResource(R.drawable.kuliner);
        }
        return view;
    }
}
