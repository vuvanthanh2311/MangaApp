package com.example.mangaapp.detailtruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaapp.R;
import com.google.android.material.tabs.TabLayout;

public class detail_truyen extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    fragment_Chitiet chitiet;
    fragment_Chapter chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_truyen);
        viewPager =findViewById(R.id.vpg_detailtruyen);
        tabLayout =findViewById(R.id.tbl_detail);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        chitiet = new fragment_Chitiet();
        chapter = new fragment_Chapter();
        chitiet.getdata(id);
        chapter.getdata(id);

        setViewpager();



    }
    private void setViewpager(){
        Viewpagerdetail viewpagerdetail = new Viewpagerdetail(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpagerdetail.add(chitiet,"Chi tiết");
        viewpagerdetail.add(chapter, "Chapter");
        viewPager.setAdapter(viewpagerdetail);

    }
}