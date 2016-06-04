package com.revmedia.tugasakhir;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {
    View view;

    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String getCategory = getArguments().getString("stringCategory");
        view = inflater.inflate(R.layout.fragment_category_list, container, false);
        RecyclerView recList = (RecyclerView)view.findViewById(R.id.cardListCategory);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(getCategory);
        return view;
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, List<ArticleInfo>> {

        private String resp;
        @Override
        protected List<ArticleInfo> doInBackground(String... params) {
            ArrayList<String> titles = new ArrayList<>();
            String NAMESPACE = "http://service.fuzzy.com/";
            String METHOD_NAME = "getCategory";
            String SOAP_ACTION = "http://service.fuzzy.com/getCategory";
            String URL = "http://10.0.2.2:8080/FuzzyWebService/FuzzyService";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo p = new PropertyInfo();
            p.setName("category");
            p.setValue(params[0]);
            p.setType(String.class);

            request.addProperty(p);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            List<ArticleInfo> result = new ArrayList<>();

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject resultRequest = (SoapObject)envelope.bodyIn;

                for(int i = 0;i < resultRequest.getPropertyCount();i++){
                    //Article temp = (Article)resultRequest.getProperty(i);
                    Object property = resultRequest.getProperty(i);
                    if(property instanceof SoapObject){
                        SoapObject finalObject = (SoapObject)property;
                        ArticleInfo ai = new ArticleInfo();
                        ai.title = finalObject.getProperty("articleTitle").toString();
                        ai.content = finalObject.getProperty("articleContent").toString();
                        ai.summary = ai.content.substring(0, Math.min(ai.content.length(), 150));
                        ai.category = finalObject.getProperty("articleCategory").toString();
                        //titles.add(finalObject.getProperty("articleTitle").toString());
                        result.add(ai);
                    }
                    //titles.add(resultRequest.getProperty(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(List<ArticleInfo> result) {
            // execution of result of Long time consuming operation
            // In this example it is the return value from the web service
            RecyclerView artList = (RecyclerView)view.findViewById(R.id.cardListCategory);
            ArticleAdapter adapter = new ArticleAdapter(getContext(), result);
            //ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, result);
            artList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        /**
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
    }

}
