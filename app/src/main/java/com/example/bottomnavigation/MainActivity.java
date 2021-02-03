package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;

import com.example.bottomnavigation.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    Stack<Integer> pageHistory=new Stack<>();
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        //getSupportFragmentManager().beginTransaction().add(R.id.frame,new BlankFragment()).commit();
        flag=true;
        pageHistory.push(0);
        activityMainBinding.bot.setOnNavigationItemSelectedListener(listener);
        activityMainBinding.frame.addOnPageChangeListener(listener1);

        setAdapter();

    }



    private void setAdapter() {
        List<Fragment> list=new ArrayList<>();
        list.add(new BlankFragment());
        list.add(new BlankFragment2());
        list.add(new BlankFragment3());
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),list);
        activityMainBinding.frame.setAdapter(adapter);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener listener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.profile:
                    activityMainBinding.frame.setCurrentItem(0);
                    break;
                case R.id.favourite:
                    activityMainBinding.frame.setCurrentItem(1);
                    break;
                case R.id.home:
                    activityMainBinding.frame.setCurrentItem(2);
                    break;
            }

            return true;

        }
};

    private ViewPager.OnPageChangeListener listener1=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(flag) pageHistory.push(position);
            switch (position) {
                case 0:
                    activityMainBinding.bot.getMenu().findItem(R.id.profile).setChecked(true);
                    break;
                case 1:
                    activityMainBinding.bot.getMenu().findItem(R.id.favourite).setChecked(true);
                    break;
                case 2:
                    activityMainBinding.bot.getMenu().findItem(R.id.home).setChecked(true);
                    break;
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    public void onBackPressed() {
        Log.d("abc", "onBackPressed: "+pageHistory.size());
        pageHistory.pop();
        if(pageHistory.empty())
            super.onBackPressed();
        else {

            flag=false;

            activityMainBinding.frame.setCurrentItem(pageHistory.lastElement());
            flag=true;
        }
    }


}