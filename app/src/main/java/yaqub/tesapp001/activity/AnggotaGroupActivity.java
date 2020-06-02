package yaqub.tesapp001.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import yaqub.tesapp001.R;
import yaqub.tesapp001.activity.Fragment.TabFragmentAdapter;
import yaqub.tesapp001.api.APIRetrofitAnggota;

public class AnggotaGroupActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    public static AnggotaGroupActivity anggotaGroupActivity;
    public RecyclerView recyclerView;

//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_group);
//        getSupportActionBar().setTitle(MainActivity.mainActivity.session.getSPnamaGroup());
//        getSupportActionBar().setSubtitle(MainActivity.mainActivity.session.getSPGroupDesc());

        anggotaGroupActivity=this;
        viewPager= findViewById(R.id.vpPager);
        tabLayout= findViewById(R.id.tlTabsAG);

        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager()));

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorTab),getResources().getColor(android.R.color.white));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }

}
