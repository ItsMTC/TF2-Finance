package net.treehousetech.tf2finance;

import java.util.ArrayList; 
import java.util.List; 
import java.util.Locale; 

import android.app.ActionBar; 
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode; 
import android.support.v4.app.Fragment; 
import android.support.v4.app.FragmentActivity; 
import android.support.v4.app.FragmentManager; 
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.anjlab.android.iab.v3.BillingProcessor;
  
public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener { 
    SectionsPagerAdapter mSectionsPagerAdapter; 
    ViewPager mViewPager; 
    
    private BillingProcessor bp;
    private boolean readyToPurchase = false;

    // PRODUCT & SUBSCRIPTION IDS
    private static final String PRODUCT_ID = "tt_tf2finance_premium";
    static String piece1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjN8pdEn9HfTiPVOdCuTCtA5yHTscOXCcJsG2";
    static String piece2 = "tUM0ugISQld8LizxA+UcUSmG9UIoBxdlGvhF6dv1hAtdjU8cY3ONHf0kqVBvZQc";
    static String piece3 = "5j0wBzQln+0u++kWJlAhSImOG5w1UvWtUk/V1dUbVNSIEtKWSdL7xu8e3aD2hkBBzr7eeCndl977djHFsjfqRbh+a7TNLcHEyjA4kQ";
    static String piece4 = "lH4Om/8/9r8xm4dFc6Ta4h8u350dw8XtZnsWypixSQvL/61E4kbmTrzKgsJv94bUZfdQE6Sn2OpLkmU2a/eXjW1L2d6jcK9A8kXnYUkJG34MpZYLHZhWIJny28ljTr6zQbnF17p1BTR2QIDAQAB";
    private static final String LICENSE_KEY = piece1 + piece2 + piece3 + piece4;
    
    Global global;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
        final ActionBar actionBar = getActionBar(); 
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
        mSectionsPagerAdapter = new SectionsPagerAdapter( 
                getSupportFragmentManager()); 
        mViewPager = (ViewPager) findViewById(R.id.pager); 
        mViewPager.setAdapter(mSectionsPagerAdapter); 
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { 
                    @Override
                    public void onPageSelected(int position) { 
                        actionBar.setSelectedNavigationItem(position); 
                    } 
                }); 
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) { 
            actionBar.addTab(actionBar.newTab() 
                    .setText(mSectionsPagerAdapter.getPageTitle(i)) 
                    .setTabListener(this)); 
        } 
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
  
        StrictMode.setThreadPolicy(policy);
        
        global = (Global)getApplicationContext();
        
        bp = new BillingProcessor(this, LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId) {
                showAlert("Purchased", productId + " Purchased");
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
            	showAlert("Billing Error", "Error Code: " + Integer.toString(errorCode));
            }
            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;
            }
            @Override
            public void onPurchaseHistoryRestored() {
            	didPurchasePremium();
            }
        });
    } 
    
    public void didPurchasePremium(){
    	if(bp.isPurchased(PRODUCT_ID)){
    		global.showAds = false;
    	} else {
    		global.showAds = true;
    	}
    }
    
    public void doPurchasePremium(){
    	if (!readyToPurchase) {
            showAlert("Billing Error", "Billing not initialized.");
            return;
        }
    	bp.purchase(PRODUCT_ID);
    	if (bp.loadOwnedPurchasesFromGoogle()) {
            didPurchasePremium();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { 
        // Inflate the menu; this adds items to the action bar if it is present. 
        getMenuInflater().inflate(R.menu.main, menu); 
          
        return true; 
    } 
  
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.purchase:
        	doPurchasePremium();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, 
            FragmentTransaction fragmentTransaction) { 
        // When the given tab is selected, switch to the corresponding page in 
        // the ViewPager. 
        mViewPager.setCurrentItem(tab.getPosition()); 
    } 
  
    @Override
    public void onTabUnselected(ActionBar.Tab tab, 
            FragmentTransaction fragmentTransaction) { 
    } 
  
    @Override
    public void onTabReselected(ActionBar.Tab tab, 
            FragmentTransaction fragmentTransaction) { 
    } 
    
    public void showAlert(String title, String body){
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

    	dlgAlert.setMessage(body);
    	dlgAlert.setTitle(title);
    	dlgAlert.setPositiveButton("OK", null);
    	dlgAlert.setCancelable(true);
    	dlgAlert.create().show();
    }
  
    /** 
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to 
     * one of the sections/tabs/pages. 
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter { 
  
        private List<Fragment> mFragments = new ArrayList<Fragment>(); 
          
        public SectionsPagerAdapter(FragmentManager fm) { 
            super(fm); 
            mFragments.add(new BudFragment()); 
            mFragments.add(new KeyFragment());
            mFragments.add(new SearchFragment());
        } 
  
        @Override
        public Fragment getItem(int position) { 
            // getItem is called to instantiate the fragment for the given page. 
            // Return a DummySectionFragment (defined as a static inner class 
            // below) with the page number as its lone argument. 
            Fragment fragment = mFragments.get(position); 
              
            return fragment; 
        } 
  
        @Override
        public int getCount() { 
            // Show 3 total pages. 
            return 3; 
        } 
  
        @Override
        public CharSequence getPageTitle(int position) { 
            Locale l = Locale.getDefault(); 
            switch (position) { 
            case 0: 
                return getString(R.string.title_section1).toUpperCase(l); 
            case 1: 
                return getString(R.string.title_section2).toUpperCase(l);
            case 2: 
                return getString(R.string.title_section3).toUpperCase(l);
            } 
            return null; 
        } 
    }
    
    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }
  
      
  
} 
