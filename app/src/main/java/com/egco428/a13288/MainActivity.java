package com.egco428.a13288;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FortuneDataSource ds;
    ListView cookieListview;
    ArrayAdapter<Fortune> CookieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookieListview = (ListView) findViewById(R.id.cookieListview);

        ds = new FortuneDataSource(this);
        ds.open();
        final List<Fortune> values = ds.getAllFortune();
        CookieArrayAdapter = new CookieArrayAdapter(this, 0, values);
        cookieListview.setAdapter(CookieArrayAdapter);

        cookieListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                if (CookieArrayAdapter.getCount()>0) {
                    final Fortune fortune = values.get(i);
                    ds.deleteFortune(fortune);
                    view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            CookieArrayAdapter.remove(fortune);
                            view.setAlpha(1);
                        }
                    });
                }
            }
        });
        CookieArrayAdapter.notifyDataSetChanged();



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar);

        ImageButton addBtn = (ImageButton)findViewById(R.id.addImgBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentadd = new Intent(MainActivity.this, ShakeActivity.class);
                startActivityForResult(intentadd , 1);
            }
        });
    }


    class CookieArrayAdapter extends ArrayAdapter<Fortune>{
        Context context;
        List<Fortune> objects;
        public CookieArrayAdapter(Context context, int resource, List<Fortune> objects){
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }
        @Override public View getView(int position, View convertView, ViewGroup parent){
            Fortune fortune = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.result_layout, null);

            TextView ffCookiesTxt = (TextView)view.findViewById(R.id.textView6);
            ffCookiesTxt.setText(fortune.getFortune());
            if((fortune.getFortune().equals("Work Harder")) || (fortune.getFortune().equals("Don't Panic")) ){
                ffCookiesTxt.setTextColor(Color.RED);
            }
            else {
                ffCookiesTxt.setTextColor(Color.BLUE);
            }

            TextView ffTimeTxt = (TextView)view.findViewById(R.id.textView4);
            ffTimeTxt.setText("Date: " + fortune.getTime());

            ImageView image = (ImageView)view.findViewById(R.id.imageView7);
            int res = context.getResources().getIdentifier("image"+fortune.getPrime(), "drawable", context.getPackageName());
            image.setImageResource(res);
            return view;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fortune fortune = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == ShakeActivity.RESULT_OK){
                String inputData1 = data.getStringExtra(ShakeActivity.tag1);
                String inputData2 = data.getStringExtra(ShakeActivity.tag2);
                String inputData3 = data.getStringExtra(ShakeActivity.tag3);
                ds.open();
                fortune = ds.createFortune(inputData1,inputData2,inputData3);//add to database
                CookieArrayAdapter.add(fortune);
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        cookieListview = (ListView) findViewById(R.id.cookieListview);

        ds.open();
        List<Fortune> values = ds.getAllFortune();
        cookieListview.setAdapter(CookieArrayAdapter);
    }

    @Override
    protected void onPause() {
        ds.close();
        super.onPause();
    }
}


