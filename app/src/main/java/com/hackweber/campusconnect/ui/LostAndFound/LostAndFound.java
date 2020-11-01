package com.hackweber.campusconnect.ui.LostAndFound;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.ui.CleanlinessPackage.Cleanliness;
import com.hackweber.campusconnect.ui.FoodOrderActivity;
import com.hackweber.campusconnect.ui.MainActivity;
import com.hackweber.campusconnect.ui.UserProfilePackage.UserProfile;

public class LostAndFound extends AppCompatActivity {

    private FloatingActionButton floatingActionButton,fab;
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
        fab=findViewById(R.id.order_food);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(LostAndFound.this, FoodOrderActivity.class);
                startActivity(i5);
                finish();
            }
        });

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent i=new Intent(LostAndFound.this,MainActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.lost_found:
//                        Intent i2=new Intent(LostAndFound.this, LostAndFound.class);
//                        startActivity(i2);
                        break;
                    case R.id.report:
                        Intent i3=new Intent(LostAndFound.this, Cleanliness.class);
                        startActivity(i3);
                        finish();
                        break;
                    case R.id.profile:
                        Intent i4=new Intent(LostAndFound.this, UserProfile.class);
                        startActivity(i4);
                        finish();
                        break;

                }
                return false;
            }
        });

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