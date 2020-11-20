package com.example.mangaapp.bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mangaapp.R;
import com.example.mangaapp.detailtruyen.Viewpagerdetail;
import com.example.mangaapp.detailtruyen.fragment_Chapter;
import com.example.mangaapp.detailtruyen.fragment_Chitiet;
import com.example.mangaapp.phanloaifragment.fragment_bachhop;
import com.example.mangaapp.phanloaifragment.fragment_dammy;
import com.example.mangaapp.phanloaifragment.fragment_hanhdong;
import com.example.mangaapp.phanloaifragment.fragment_huyenhuyen;
import com.example.mangaapp.phanloaifragment.fragment_kinhdi;
import com.example.mangaapp.phanloaifragment.fragment_maohiem;
import com.example.mangaapp.phanloaifragment.fragment_sieunangluc;
import com.example.mangaapp.phanloaifragment.fragment_tinhyeu;
import com.example.mangaapp.phanloaifragment.fragment_tongtai;
import com.example.mangaapp.phanloaifragment.fragment_truonghoc;
import com.example.mangaapp.phanloaifragment.fragment_vientuong;
import com.example.mangaapp.phanloaifragment.fragment_xuyenkhong;
import com.google.android.material.tabs.TabLayout;

public class phanloaifragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phanloai_fragment, container, false);
        viewPager =view.findViewById(R.id.vpg_phanloai);
        tabLayout =view.findViewById(R.id.tbl_phanloai);
        tabLayout.setupWithViewPager(viewPager);
        setViewpager();
        return view;
    }
    private void setViewpager(){
        Viewpagerdetail viewpagerdetail = new Viewpagerdetail(getFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpagerdetail.add(new fragment_hanhdong(),"Hành động");
        viewpagerdetail.add(new fragment_huyenhuyen(), "Huyền huyễn");
        viewpagerdetail.add(new fragment_maohiem(), "Mạo hiểm");
        viewpagerdetail.add(new fragment_sieunangluc(), "Siêu năng lực");
        viewpagerdetail.add(new fragment_vientuong(), "Viễn tưởng");
        viewpagerdetail.add(new fragment_xuyenkhong(), "Xuyên không");
        viewpagerdetail.add(new fragment_tongtai(),"Tổng tài");
        viewpagerdetail.add(new fragment_tinhyeu(), "Tình yêu");
        viewpagerdetail.add(new fragment_dammy(), "Đam mỹ");
        viewpagerdetail.add(new fragment_bachhop(), "Bách hợp");
        viewpagerdetail.add(new fragment_kinhdi(), "Kinh dị");
        viewpagerdetail.add(new fragment_truonghoc(), "Trường học");
        viewPager.setAdapter(viewpagerdetail);

    }
}
