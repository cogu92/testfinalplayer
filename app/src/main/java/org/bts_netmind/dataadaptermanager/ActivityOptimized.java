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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityOptimized extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG_ACTIVITY_OPTIMIZED = "In-MainActivity";
    private MediaPlayer mPlayer;
    private int current_Song = 1;
    public static final String TAG_MAIN_ACTIVITY = "In-MainActivity";
    public static final int NOTIFICATION_ID = 100;   // Sets an ID for the notification, so it can be updated (or at least not duplicated)
    public static final int CUSTOM_NOTIFICATION_ID = 200;

    // These fields are declared so that they can be used within the whole 'MainActivity' class
    private NotificationCompat.Builder mBuilder;
    private NotificationCompat.Builder mCustomBuilder;
    private NotificationManager mNotifManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        ImageButton btn_pause = (ImageButton) findViewById(R.id.Main_pause_Song);
        btn_pause.setOnClickListener(this);

        ImageButton btn_paly = (ImageButton) findViewById(R.id.Main_Play_Song);
        btn_paly.setOnClickListener(this);

        ImageButton btn_next = (ImageButton) findViewById(R.id.Main_Net_Song);
        btn_next.setOnClickListener(this);

        ImageButton btn_Stop = (ImageButton) findViewById(R.id.Main_Stop_Song);
        btn_Stop.setOnClickListener(this);

        ImageButton btn_preview = (ImageButton) findViewById(R.id.Main_Preview_Song);
        btn_preview.setOnClickListener(this);
       final ListView mListView = (ListView) this.findViewById(R.id.listViewMain);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        String[] mListValues = {"bensoundbrazilsamba", "bensoundcountryboy", "bensoundindia",
                "bensoundlittleplanet", "bensoundpsychedelic", "bensoundrelaxing", "bensoundtheelevatorbossanova"};

        String[] mListImages = {"brazil", "usa", "india",
                "iceland", "southcorea", "indonecia", "brazil"};

        ArrayList<Item> mListArray = new ArrayList<>();
        for (int idx = 0; idx < mListValues.length; idx++)
            mListArray.add(new Item(mListImages[idx], mListValues[idx], mListImages[idx]));
      View listHeader = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_view_header, mListView, false);
        mListView.addHeaderView(listHeader, null, true);
        mListView.setAdapter(new MyListAdapter(this, 0, mListArray));
        View listbotom = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.botom, mListView, false);
        mListView.addFooterView(listbotom, null, true);


        this.mNotifManager = (NotificationManager) this.getSystemService(Service.NOTIFICATION_SERVICE);
        // Builds a notification using the 'NotificationCompat.Builder' subclass
        this.mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("This is a simple text for my notification")
                .setTicker("A Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(this.getResources().getString(R.string.notification_long_text)));
        //.setAutoCancel(true);
        // Creates an explicit intent for an Activity in the app
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        // This 'PendingIntent' wraps the previous 'Intent', so that it can be used later
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        this.mBuilder.setContentIntent(mPendingIntent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (mPlayer != null)
            mPlayer.stop();

        this.mNotifManager.notify(ActivityOptimized.NOTIFICATION_ID, this.mBuilder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPlayer = changesong(position);
                        mPlayer.start();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Main_Stop_Song:
                mPlayer.stop();
                break;
            case R.id.Main_pause_Song:
                mPlayer.pause();
                break;
            case R.id.Main_Play_Song:
                this.mNotifManager.notify(ActivityOptimized.NOTIFICATION_ID, this.mBuilder.build());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (mPlayer == null)
                                    mPlayer = changesong(current_Song);
                                mPlayer.start();
                            }
                        });

                    }
                }).start();

                break;
            case R.id.Main_Net_Song:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mPlayer != null)
                                    mPlayer.stop();
                                mPlayer = changesong(current_Song + 1);
                                mPlayer.start();
                            }
                        });

                    }
                }).start();
                break;
            case R.id.Main_Preview_Song:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                           @Override
                            public void run() {
                                if (mPlayer != null)
                                    mPlayer.stop();
                                mPlayer = changesong(current_Song - 1);
                                mPlayer.start();
                            }
                        });
                    }
                }).start();
                break;
        }

    }

    public MediaPlayer changesong(int currentSong) {
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

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        String SongTime = "";
        switch (position) {
            case 1:
                SongTime = "04:00";
                break;
            case 2:
                SongTime = "03:27";
                break;
            case 3:
                SongTime = "04:13";
                break;
            case 4:
                SongTime = "06:36";
                break;
            case 5:
                SongTime = "03:56";
                break;
            case 6:
                SongTime = "04:48";
                break;
            case 7:
                SongTime = "04:14";
                break;
        }

        Toast.makeText(this, "Duration of this Song " + SongTime + " min", Toast.LENGTH_SHORT).show();

        return true;
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
                convertView = mInflater.inflate(R.layout.list_view_custom_layout, null);
                mViewHolder = new ViewHolder();
                mViewHolder.icon_ImgView = (ImageView) convertView.findViewById(R.id.imageViewList);
                mViewHolder.title_TxtView = (TextView) convertView.findViewById(R.id.textViewTiltle);
                mViewHolder.body_TxtView = (TextView) convertView.findViewById(R.id.textViewBody);
                convertView.setTag(mViewHolder);
            } else
                mViewHolder = (ViewHolder) convertView.getTag();
            mViewHolder.icon_ImgView.setImageResource(this.mContext.getResources().getIdentifier(this.itemList.get(position).getmImageRef(), "drawable", this.mContext.getPackageName()));
            mViewHolder.title_TxtView.setText(this.itemList.get(position).getmTitle());
            mViewHolder.body_TxtView.setText(this.itemList.get(position).getmBody());
            return convertView;
        }
    }
}
