package yaqub.tesapp001.activity.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Yaqub on 12/13/2017.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    String[] title=new String[]{
            "Anggota","Group"
    };

    public TabFragmentAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new AnggotaFragment();
                break;
            case 1:
                fragment=new GroupFragment();
                break;
            default:
                fragment=null;
                break;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
