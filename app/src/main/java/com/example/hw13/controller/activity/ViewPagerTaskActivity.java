package com.example.hw13.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hw13.R;
import com.example.hw13.controller.fragment.ListTaskFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.nio.channels.InterruptedByTimeoutException;

public class ViewPagerTaskActivity extends AppCompatActivity {

    //region defind variable
    ViewPager2 mViewPager2;
//    TabLayout mTabLayout;

    ListTaskFragment binding;

    //endregion

    public static Intent newIntent(Context src){
        Intent intent=new Intent(src,ViewPagerTaskActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_task);
        findViews();
        setInitialization();
    }

    private void setInitialization() {
        FragmentStateAdapter fragmentStateAdapter=new fragmentStateAdapter(this);
        mViewPager2.setAdapter(fragmentStateAdapter);

//        new TabLayoutMediator(mTabLayout, mViewPager2,
//                new TabLayoutMediator.TabConfigurationStrategy() {
//                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                        tab.setText("Tab " + (position + 1));
//                    }
//                }).attach();

    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.activityViewPagerTask_tasksFragment);
//        mTabLayout=findViewById(R.id.tabLayout_activityViewPagerTask);
    }

    public class fragmentStateAdapter extends FragmentStateAdapter {

        public fragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);

        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ListTaskFragment.newInstance(position);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}