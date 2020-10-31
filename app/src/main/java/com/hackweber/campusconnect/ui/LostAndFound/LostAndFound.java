package com.hackweber.campusconnect.ui.LostAndFound;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hackweber.campusconnect.R;
public class LostAndFound extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private TabsAdapter tabsAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText(R.string.tab_text_1);
        tabLayout.getTabAt(1).setText(R.string.tab_text_2);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.lost_and_found_add_items);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostAndFound.this,AddItem.class);
                intent.putExtra("type",tabLayout.getSelectedTabPosition());
                startActivity(intent);

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new LostItemsTab());
        adapter.addFragment(new FoundItemsTab());
        viewPager.setAdapter(adapter);
    }
}