package com.example.hatolkaa.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    private final int LIST_VIEW_ID_FAMILY_TAB = R.id.FamilyList;
    private final int LIST_VIEW_ID_FREINDS_TAB = R.id.FriendsList;
    private final String FAMILY = "FAMILY";
    private final String FRIENDS = "FRIENDS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = createAndSetupTabHost();
        createTab(tabHost, FAMILY, LIST_VIEW_ID_FAMILY_TAB);
        createTab(tabHost, FRIENDS, LIST_VIEW_ID_FREINDS_TAB);

        ArrayList<String> familyList = addMembersToFamilyTab();
        ArrayList<String> friendsList = addMembersToFriendsTab();

        addListToTab(familyList, LIST_VIEW_ID_FAMILY_TAB);
        addListToTab(friendsList, LIST_VIEW_ID_FREINDS_TAB);
    }

    private void createTab(TabHost tabHost, String tabName, int listViewId) {
        TabHost.TabSpec spec;
        spec = tabHost.newTabSpec(tabName);
        spec.setContent(listViewId);
        spec.setIndicator(tabName);
        tabHost.addTab(spec);
    }

    private ArrayList<String> addMembersToFamilyTab() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Aai");
        arrayList.add("Baba");
        return arrayList;
    }

    private ArrayList<String> addMembersToFriendsTab() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Tushar Rathi");
        arrayList.add("Abhiram Hatolkar");
        return arrayList;
    }

    private TabHost createAndSetupTabHost() {
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        return host;
    }

    private void addListToTab(ArrayList<String> list, int listViewId) {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_layout,list);
        final ListView listView = (ListView) findViewById(listViewId);
        listView.setAdapter(adapter);
        setOnClickListenerToListElements(listView);
    }

    private void setOnClickListenerToListElements(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                
                String selectedFromList = (String) (listView.getItemAtPosition(position)).toString().toLowerCase();

                Toast.makeText(getApplicationContext(),
                        selectedFromList, Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(MainActivity.this, DetailedMessage.class);
                Bundle b = new Bundle();
                b.putString("author", selectedFromList);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });
    }

}
