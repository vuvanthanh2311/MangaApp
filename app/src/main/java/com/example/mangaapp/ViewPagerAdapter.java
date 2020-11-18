package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mangaapp.bottomnav.Explorefragment;
import com.example.mangaapp.bottomnav.Homefragment;
import com.example.mangaapp.bottomnav.favoritefragment;
import com.example.mangaapp.bottomnav.personfragment;
import com.example.mangaapp.bottomnav.phanloaifragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Homefragment();
            case 1:
                return new phanloaifragment();
            case 2:
                return new Explorefragment();
            case 3:
                return new favoritefragment();
            default:
                return new personfragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
