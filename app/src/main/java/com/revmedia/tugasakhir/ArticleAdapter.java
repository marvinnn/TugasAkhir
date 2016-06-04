package com.revmedia.tugasakhir;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marvin Zeson on 1/26/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    public final static String EXTRA_TITLE = "com.revmedia.tugasakhir.title";
    public final static String EXTRA_CONTENT = "com.revmedia.tugasakhir.content";
    public final static String EXTRA_CATEGORY = "com.revmedia.tugasakhir.category";

    private List<ArticleInfo> articleList;
    private Context context;

    public ArticleAdapter(Context con, List<ArticleInfo> contactList) {
        this.context = con;
        this.articleList = contactList;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder articleViewHolder, int i) {
        ArticleInfo ci = articleList.get(i);
        final String title = ci.title;
        final String content = ci.content;
        final String category = ci.category;
        articleViewHolder.vTitle.setText(ci.title);
        articleViewHolder.vSummary.setText(ci.summary);
        if(category.equals("gunung")){
            articleViewHolder.vCategory.setImageResource(R.drawable.gunung);
        }
        else if(category.equals("pantai")){
            articleViewHolder.vCategory.setImageResource(R.drawable.pantai);
        }
        else if(category.equals("candi")){
            articleViewHolder.vCategory.setImageResource(R.drawable.candi);
        }
        else if(category.equals("hotel")){
            articleViewHolder.vCategory.setImageResource(R.drawable.hotel);
        }
        else if(category.equals("kuliner")){
            articleViewHolder.vCategory.setImageResource(R.drawable.kuliner);
        }
        articleViewHolder.vSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ArticleFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", content);
                bundle.putString("category", category);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .commit();
            }
        });
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ArticleViewHolder(itemView);
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vSummary;
        protected ImageView vCategory;

        public ArticleViewHolder(View v) {
            super(v);
            vSummary =  (TextView) v.findViewById(R.id.txtContent);
            vTitle = (TextView) v.findViewById(R.id.title);
            vCategory = (ImageView)v.findViewById(R.id.imageCard);
        }
    }
}