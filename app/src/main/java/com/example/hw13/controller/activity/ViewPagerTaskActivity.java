package com.example.hw13.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hw13.R;
import com.example.hw13.controller.fragment.ListTaskFragment;

import java.nio.channels.InterruptedByTimeoutException;

public class ViewPagerTaskActivity extends AppCompatActivity {
    //region defind variable
    ViewPager2 mViewPager2;
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
        mViewPager2.setOffscreenPageLimit(1);
    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.activityViewPagerTask_tasksFragment);
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
        public int getItemCount() {
            return 3;
        }
    }
}