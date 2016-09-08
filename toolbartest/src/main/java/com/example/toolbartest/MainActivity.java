package com.example.toolbartest;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager= (ViewPager) findViewById(R.id.pager);
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

        listView= (ListView) findViewById(R.id.left_drawer);
        ArrayList<Integer> arrayList=new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        listView.setAdapter(new MyListAdapter(MainActivity.this,R.layout.layout_listview,
                arrayList));


        /*ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer,
                R.string.drawer);*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_purple:
                Toast.makeText(MainActivity.this,"purple",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_red:
                Toast.makeText(MainActivity.this,"red",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settting:
                Toast.makeText(MainActivity.this,"settting",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        //set search action view
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("search");

        MenuItemCompat.OnActionExpandListener actionExpandListener=
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        Toast.makeText(MainActivity.this,"expand",Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        Toast.makeText(MainActivity.this,"collapse",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                };
        MenuItemCompat.setOnActionExpandListener(searchItem,actionExpandListener);

        //set share action view
        MenuItem shareItem=menu.findItem(R.id.action_share);
        shareActionProvider=
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"hahaha");
        intent.setType("text/plain");
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }
}
