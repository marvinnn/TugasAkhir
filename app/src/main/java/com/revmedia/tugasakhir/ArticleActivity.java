package com.revmedia.tugasakhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        String title = intent.getStringExtra(ArticleAdapter.EXTRA_TITLE);
        String content = intent.getStringExtra(ArticleAdapter.EXTRA_CONTENT);

        TextView tvTitle = (TextView)findViewById(R.id.txtTitle);
        TextView tvContent = (TextView)findViewById(R.id.txtContent);
        tvTitle.setText(title);
        tvContent.setText(content);;
    }
}
