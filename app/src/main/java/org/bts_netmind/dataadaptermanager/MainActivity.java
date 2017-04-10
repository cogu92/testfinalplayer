package org.bts_netmind.dataadaptermanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static org.bts_netmind.dataadaptermanager.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private MediaPlayer mPlayer;
    private int current_Song = 1;
     public static final int NOTIFICATION_ID = 100;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifManager;

    private   String[]  mListValues = {"bensoundbrazilsamba", "bensoundcountryboy", "bensoundindia",
            "bensoundlittleplanet", "bensoundpsychedelic", "bensoundrelaxing", "bensoundtheelevatorbossanova"};

    private  String[] mListDescrptions = {"Samba is a Brazilian musical genre and dance style, with its roots in Africa via the West African slave trade and African religious traditions, particularly of Angola ",
            "Country music is a genre of American popular music that originated in the Southern United States in the 1920s ",
            "The music of India includes multiple varieties of folk music, pop, and Indian classical music. India's classical music tradition, including Hindustani music and Carnatic, has a history spanning millennia and developed over several eras",
            "The music of Iceland includes vibrant folk and pop traditions. Well-known artists from Iceland include medieval music group Voces Thules, alternative rock band The Sugarcubes, singers Björk and Emiliana Torrini, post-rock band Sigur Rós and indie folk/indie pop band Of Monsters and Men ",
            "The Music of South Korea has evolved over the course of the decades since the end of the Korean War, and has its roots in the music of the Korean people, who have inhabited the Korean peninsula for over a millennium. Contemporary South Korean music can be divided into three different main categories: Traditional Korean folk music, popular music, or K-pop, and Western-influenced non-popular music ",
            "The music of Indonesia demonstrates its cultural diversity, the local musical creativity, as well as subsequent foreign musical influences that shaped contemporary music scenes of Indonesia. Nearly thousands of Indonesian islands having its own cultural and artistic history and character",
            "Samba is a Brazilian musical genre and dance style, with its roots in Africa via the West African slave trade and African religious traditions, particularly of Angola "};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity);

        ImageButton btn_pause = (ImageButton) findViewById(R.id.Main_pause_Song);
        btn_pause.setOnClickListener(this);

        ImageButton btn_paly = (ImageButton) findViewById(R.id.Main_Play_Song);
        btn_paly.setOnClickListener(this);

        ImageButton btn_next = (ImageButton) findViewById(R.id.Main_Net_Song);
        btn_next.setOnClickListener(this);


        ImageButton btn_preview = (ImageButton) findViewById(R.id.Main_Preview_Song);
        btn_preview.setOnClickListener(this);
        final ListView mListView = (ListView) this.findViewById(R.id.listViewMain);
        mListView.setOnItemClickListener(this);
        ArrayList<Item> mListArray = new ArrayList<>();
        for (int idx = 0; idx < mListValues.length; idx++)
           mListArray.add(new Item(ic_launcher, mListValues[idx], ""));

        mListView.setAdapter(new MyListAdapter(this, 0, mListArray));
        this.mNotifManager = (NotificationManager) this.getSystemService(Service.NOTIFICATION_SERVICE);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (mPlayer != null)
            mPlayer.stop();
        ParametrincNotifications(position);
        this.mNotifManager.notify(MainActivity.NOTIFICATION_ID, this.mBuilder.build());
        mPlayer =   StarMusic(position);
        mPlayer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.Main_pause_Song:
                mPlayer.pause();
                break;
            case R.id.Main_Play_Song:
                if (mPlayer == null){
                    ParametrincNotifications(1);

              }else
                mPlayer.start();

                this.mNotifManager.notify(MainActivity.NOTIFICATION_ID, this.mBuilder.build());
                break;
            case R.id.Main_Net_Song:
                if (mPlayer != null)
                    mPlayer.stop();
                mPlayer = StarMusic(current_Song+1);
                mPlayer.start();
                ParametrincNotifications(current_Song+1);
                this.mNotifManager.notify(MainActivity.NOTIFICATION_ID, this.mBuilder.build());

                break;
            case R.id.Main_Preview_Song:
                if (mPlayer != null)
                    mPlayer.stop();
                mPlayer = StarMusic(current_Song-1);
                mPlayer.start();

                ParametrincNotifications(current_Song-1);
                this.mNotifManager.notify(MainActivity.NOTIFICATION_ID, this.mBuilder.build());

                  break;
        }

    }



    public MediaPlayer StarMusic(int currentSong) {

        MediaPlayer msongo = null;
        if (currentSong < 1)
            currentSong = 7;
        else if (currentSong > 7)
            currentSong = 1;
        switch (currentSong) {
            case 1:
                msongo = MediaPlayer.create(this, R.raw.bensoundbrazilsamba);
                current_Song = 1;
                break;
            case 2:
                msongo = MediaPlayer.create(this, R.raw.bensoundcountryboy);
                current_Song = 2;
                break;
            case 3:
                msongo = MediaPlayer.create(this, R.raw.bensoundindia);
                current_Song = 3;
                break;
            case 4:
                msongo = MediaPlayer.create(this, R.raw.bensoundlittleplanet);
                current_Song = 4;
                break;
            case 5:
                msongo = MediaPlayer.create(this, R.raw.bensoundpsychedelic);
                current_Song = 5;
                break;
            case 6:
                msongo = MediaPlayer.create(this, R.raw.bensoundrelaxing);
                current_Song = 6;
                break;
            case 7:
                msongo = MediaPlayer.create(this, R.raw.bensoundtheelevatorbossanova);
                current_Song = 7;
                break;
        }

        return msongo;
    }

    public void ParametrincNotifications(int currentSongNotif) {

        this.mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(ic_launcher)
                .setContentTitle("Francisco3")
                .setContentText("Francisco1")
                .setTicker("Francisco2")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mListDescrptions[currentSongNotif]));
        Intent notificationIntent = new Intent(this, Notifications.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.mBuilder.setContentIntent(mPendingIntent);
    }

    private class MyListAdapter extends ArrayAdapter<Item> {
        private class ViewHolder {
            public ImageView icon_ImgView;
            public TextView title_TxtView;
            public TextView body_TxtView;
        }

        Context mContext;
        ArrayList<Item> itemList;

        public MyListAdapter(Context context, int resource, ArrayList<Item> objects) {
            super(context, resource, objects);

            this.mContext = context;
            this.itemList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_view, null);
                mViewHolder = new ViewHolder();
                mViewHolder.icon_ImgView = (ImageView) convertView.findViewById(R.id.imageViewList);
                mViewHolder.title_TxtView = (TextView) convertView.findViewById(R.id.textViewTiltle);
                mViewHolder.body_TxtView = (TextView) convertView.findViewById(R.id.textViewBody);
                convertView.setTag(mViewHolder);
            } else
                mViewHolder = (ViewHolder) convertView.getTag();
            mViewHolder.icon_ImgView.setImageResource(this.mContext.getResources().getIdentifier(String.valueOf(this.itemList.get(position).getmImageRef()), "drawable", this.mContext.getPackageName()));
            mViewHolder.title_TxtView.setText(this.itemList.get(position).getmTitle());
            mViewHolder.body_TxtView.setText(this.itemList.get(position).getmBody());
            return convertView;
        }
    }
}
