package com.levko.roma.levkohomework7;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private String[] itemTitles, itemText;
    private int width, height;
    private ArrayList<View> itemList;
    private SharedPreferences sPref;
    private int index;
    private MenuItem[] items = new MenuItem[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        getWindowSize();
        addItems();
        addSupportActionBar();
        loadIndexFromSP();
    }

    private void addSupportActionBar() {
        final android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(R.string.title_action_bar);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.items[0] = menu.findItem(R.id.item1_main);
        this.items[1] = menu.findItem(R.id.item2_main);
        this.items[2] = menu.findItem(R.id.item3_main);
        updateIconInMenu();
        return super.onCreateOptionsMenu(menu);
    }

    private void addItems() {
        itemList = new ArrayList<>();
        container.removeAllViewsInLayout();
        itemTitles = getResources().getStringArray(R.array.TitleList);
        itemText = getResources().getStringArray(R.array.TextList);

        for (int i = 0; i < 3; i++) {
            View item = View.inflate(this, R.layout.item, null);
            item.setLayoutParams(new ActionBar.LayoutParams(width, height));
            TextView tvTitle = (TextView) item.findViewById(R.id.tvTitle);
            TextView tvText = (TextView) item.findViewById(R.id.tvText);
            final String sTitle = itemTitles[i];
            final String sText = itemText[i];
            tvTitle.setText(sTitle);
            tvText.setText(sText);
            item.findViewById(R.id.iBtnItem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, sTitle, sText);
                }
            });
            itemList.add(i, item);
            container.addView(item);
        }
    }

    private void showPopupMenu(View v, final String sTitle, final String sText) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_item);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.item1_item:
                        openNewActivity(sTitle, sText);
                        return true;
                    case R.id.item2_item:
                        showToast(sTitle, sText);
                        return true;
                    case R.id.item3_item:
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void showToast(String sTitle, String sText) {
        Toast.makeText(this, sTitle + "\n" + sText, Toast.LENGTH_LONG).show();
    }

    private void findViews() {
        container = (LinearLayout) findViewById(R.id.ll_container);
    }

//    @Override
//    public void onClick(View v) {
//        final PopupMenu menu = new PopupMenu(this, v);
//        menu.inflate(R.menu.menu_item);
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });
//        menu.show();
//
//    }

    private void openNewActivity(String title, String text) {
        Intent intent = new Intent(MainActivity.this, SelectedView.class);
        intent.putExtra(Constants.PUT_EXTRA_TITLE, title);
        intent.putExtra(Constants.PUT_EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item1_main:
                setEnabledItem(Constants.FIRST_ITEM);
                break;
            case R.id.item2_main:
                setEnabledItem(Constants.SECOND_ITEM);
                break;
            case R.id.item3_main:
                setEnabledItem(Constants.THIRD_ITEM);
                break;

        }
        updateIconInMenu();
        return super.onOptionsItemSelected(item);
    }

    private void updateIconInMenu() {
        for (MenuItem item : this.items) {
            item.setIcon(R.drawable.ic_unchecked);
        }
        this.items[index].setIcon(R.drawable.ic_checked);
    }


    public void getWindowSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        if (getResources().getConfiguration().orientation == 1) {
            TypedValue tv = new TypedValue();
            this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
            height = (height - (int) (actionBarHeight * 1.7));
            height = height / 3;
            width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            width = width / 3;
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }

    }

    private void setEnabledItem(int enabledItem) {
        for (View v : itemList) {
            v.findViewById(R.id.iBtnItem).setVisibility(View.INVISIBLE);
            TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            TextView tvText = (TextView) v.findViewById(R.id.tvText);
            CardView cv = (CardView) v.findViewById(R.id.card_view);
            tvTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            tvText.setTextColor(getResources().getColor(R.color.colorContentDisable));
            cv.setCardBackgroundColor(getResources().getColor(R.color.colorContentDisable));
        }
        View v = itemList.get(enabledItem);
        v.findViewById(R.id.iBtnItem).setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        TextView tvText = (TextView) v.findViewById(R.id.tvText);
        CardView cv = (CardView) v.findViewById(R.id.card_view);
        tvTitle.setTextColor(getResources().getColor(R.color.colorAccentLight));
        tvText.setTextColor(getResources().getColor(R.color.colorWhite));
        cv.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        saveIndexInSP(enabledItem);
    }

    void saveIndexInSP(int i) {
        index = i;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(Constants.SAVED_TEXT, i);
        ed.commit();
    }

    void loadIndexFromSP() {
        sPref = getPreferences(MODE_PRIVATE);
        index = sPref.getInt(Constants.SAVED_TEXT, 0);
        setEnabledItem(index);
    }
}

