  package com.example.myapplication;

  import android.graphics.drawable.Drawable;
  import android.os.Bundle;
  import android.text.SpannableString;
  import android.text.Spanned;
  import android.text.style.ImageSpan;
  import android.widget.ImageSwitcher;
  import android.widget.LinearLayout;
  import android.widget.TextView;
  import android.widget.Toolbar;

  import androidx.appcompat.app.AppCompatActivity;
  import androidx.core.content.ContextCompat;
  import androidx.fragment.app.Fragment;
  import androidx.fragment.app.FragmentManager;
  import androidx.fragment.app.FragmentPagerAdapter;
  import androidx.viewpager.widget.ViewPager;

  import com.google.android.material.tabs.TabLayout;

  import java.util.ArrayList;
  import java.util.List;

  public class branches extends AppCompatActivity {

      private Toolbar toolbar;
      private TabLayout tabLayout;
      private ViewPager viewPager;
      private int[] tabIcons = {
              R.drawable.ic_baseline_assignment_turned_in_24,
              R.drawable.ic_baseline_assignment_turned_in_24,
              R.drawable.ic_baseline_assignment_turned_in_24
      };

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_branches);



          viewPager = (ViewPager) findViewById(R.id.viewpager);
          setupViewPager(viewPager);

          tabLayout = (TabLayout) findViewById(R.id.tabs);
          tabLayout.setupWithViewPager(viewPager);

      }


      private void setupViewPager(ViewPager viewPager) {
          ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
          adapter.addFrag(new branches_list(), "ONE");
          adapter.addFrag(new branches_list(), "TWO");
          adapter.addFrag(new branches_list(), "THREE");
          viewPager.setAdapter(adapter);
      }

      class ViewPagerAdapter extends FragmentPagerAdapter {
          private final List<Fragment> mFragmentList = new ArrayList<>();
          private final List<String> mFragmentTitleList = new ArrayList<>();

          public ViewPagerAdapter(FragmentManager manager) {
              super(manager);
          }

          @Override
          public Fragment getItem(int position) {
              return mFragmentList.get(position);
          }

          @Override
          public int getCount() {
              return mFragmentList.size();
          }

          public void addFrag(Fragment fragment, String title) {
              mFragmentList.add(fragment);
              mFragmentTitleList.add(title);
          }

          @Override
          public CharSequence getPageTitle(int position) {
              Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),tabIcons[position]);
              drawable.setBounds(0,0,drawable.getIntrinsicHeight(),drawable.getIntrinsicWidth());
              SpannableString spannableString=new SpannableString(" "+ "onE");
              ImageSpan imageSpan=new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
              spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
              return spannableString;
          }
      }
  }