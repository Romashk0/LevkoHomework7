package com.levko.roma.levkohomework7;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.LoggingPermission;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout container;
    String[] titles, itemsText;
    int width, height;
    ArrayList<View> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getWindowSize();
        addItems();
        actionBar();
    }

    private void actionBar() {

        final android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("Levko HW #7 ");
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addItems() {
        itemList = new ArrayList<>();
        container.removeAllViewsInLayout();
        titles = getResources().getStringArray(R.array.TitleList);
        itemsText = getResources().getStringArray(R.array.TextList);

        for (int i = 0; i < 3; i++) {
            View item = View.inflate(this, R.layout.item, null);
            item.setLayoutParams(new ActionBar.LayoutParams(width, height));
            ImageButton iBtn = (ImageButton) item.findViewById(R.id.iBtn1);
            TextView tvTitle = (TextView) item.findViewById(R.id.tvTitle);
            TextView tvText = (TextView) item.findViewById(R.id.tvText);
            tvTitle.setText(titles[i]);
            tvText.setText(itemsText[i]);
//            iBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, SelectedView.class);
//                    startActivity(intent);
//                }
//            });
            itemList.add(item);
            container.addView(item);
        }
    }

    private void findViews() {
        container = (LinearLayout) findViewById(R.id.ll_container);
    }

    @Override
    public void onClick(View v) {
        final PopupMenu menu = new PopupMenu(this, v);
        menu.inflate(R.menu.menu_item);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        menu.show();

//        switch (v.getId()) {
//            case R.id.btnMenuDown_AM:
//                break;
//            case R.id.btnMenuUp_AM:
//                openEmailIntent();
//                break;
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_0:
//
//                break;
//            case R.id.action_1:
//                int b = 5;
//                break;
        }
        return true;
    }

    public void getWindowSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        Log.d("qqqqqqqqqqqqq1", "width " + width + "  height " + height);
        if (getResources().getConfiguration().orientation == 1) {
            TypedValue tv = new TypedValue();
            this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
            height = (height - (int) (actionBarHeight * 1.7));
            height = height / 3;
            Log.d("heightAB", "ddd  " + actionBarHeight);
            width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            width = width / 3;
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        Log.d("qqqqqqqqqqqqq2", "width " + width + "  height " + height);
    }
}
