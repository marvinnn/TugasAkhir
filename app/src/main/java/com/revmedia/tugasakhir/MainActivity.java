package com.revmedia.tugasakhir;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.revmedia.tugasakhir.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavItems.add(new NavItem("Home", "Start here", R.drawable.ic_menu_home));
        mNavItems.add(new NavItem("Category", "Choose article category", R.drawable.ic_menu_settings));
        mNavItems.add(new NavItem("Search", "Find Article", R.drawable.ic_menu_about));

        Fragment fragment = new HomeFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);


            }
        });
    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }

    private void selectItemFromDrawer(int position) {
        if (position == 0){
            Fragment fragment = new HomeFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .commit();
        }
        else if(position == 1){
            Fragment fragment = new CategoryFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .commit();
        }
        else if(position == 2){
            Fragment fragment = new AboutFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .commit();

            new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Marv"));
        }
        /*Fragment fragment = new HomeFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();*/

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    public void searchCommited(View view){
        final SearchView sv = (SearchView)findViewById(R.id.doSearch);
        String query = sv.getQuery().toString();
        //List<String> coba = getServiceResponse(query);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(query);
    }



    private class AsyncTaskRunner extends AsyncTask<String, Void, List<ArticleInfo>> {

        private String resp;
        @Override
        protected List<ArticleInfo> doInBackground(String... params) {
            ArrayList<String> titles = new ArrayList<>();
            String NAMESPACE = "http://service.fuzzy.com/";
            String METHOD_NAME = "doSearch";
            String SOAP_ACTION = "http://service.fuzzy.com/doSearch";
            String URL = "http://192.168.1.7:8080/FuzzyWebService/FuzzyService";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo p = new PropertyInfo();
            p.setName("querry");
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
            RecyclerView artList = (RecyclerView)findViewById(R.id.cardList);
            ArticleAdapter adapter = new ArticleAdapter(result);
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
