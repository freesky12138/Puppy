package learn.com.example.administrator.puppy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.spot.SpotManager;

public class CbuttonActivity extends Activity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,CompoundButton.OnCheckedChangeListener{


    private  Switch mCbuttonIsZd;
    private SeekBar mCbuttonSize;
    private SeekBar mCbuttonTm;

    private SeekBar mCbuttonRidaus;

    Button mButtonLong;
    Button mButtonDoubleClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbutton);
      //  SpotManager.getInstance(this).showSpotAds(this);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.cbutton_layout);
        linearLayout.setBackground(new BitmapDrawable(null,Constant.BG));

        mCbuttonIsZd=(Switch)findViewById(R.id.is_cbutton_zd);
        mCbuttonSize=(SeekBar)findViewById(R.id.cbutton_size_seekBar);
        mCbuttonTm=(SeekBar)findViewById(R.id.cbutton_touming_seekBar);
        mCbuttonRidaus=(SeekBar)findViewById(R.id.cbutton_radius_seekBar);


        mCbuttonRidaus.setMax(Constant.SCREE_MAX_X/2-Constant.SCREE_MAX_X/8);

        mCbuttonRidaus.setProgress(Constant.RADIUS-Constant.SCREE_MAX_X/8);

        mCbuttonTm.setMax(255);
        mCbuttonTm.setProgress(Constant.CBUTTON_TM_VALUE);
        mCbuttonSize.setProgress(Constant.CBUTTON_SIZE);
        mCbuttonIsZd.setChecked(Constant.IS_CBUTTON_ZD);

        mCbuttonIsZd.setOnCheckedChangeListener(this);
        mCbuttonSize.setOnSeekBarChangeListener(this);
        mCbuttonTm.setOnSeekBarChangeListener(this);
        mCbuttonRidaus.setOnSeekBarChangeListener(this);

        mButtonLong=(Button)findViewById(R.id.set_long_click);
        mButtonDoubleClick=(Button)findViewById(R.id.set_double_click);
        mButtonLong.setOnClickListener(this);
        mButtonDoubleClick.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

       if(v.equals(mButtonLong)){
            CharSequence[] chars={"打开应用Puppy","隐藏主按钮","不进行如何操作"};
            new AlertDialog.Builder(this).setTitle("请选择方式")
                    .setItems(chars, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Constant.LONG_CLICK=which;
                            saveData("LONG_CLICK",which);
                        }
                    })
                    .show();
        }else if(v.equals(mButtonDoubleClick)){
            CharSequence[] chars={"打开应用Puppy","隐藏主按钮","不进行如何操作"};
            new AlertDialog.Builder(this).setTitle("请选择方式")
                    .setItems(chars, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Constant.DOUBLE_CLICK=which;
                            saveData("DOUBLE_CLICK",which);
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        if(seekBar.equals(mCbuttonTm))
        {
            Constant.CBUTTON_TM_VALUE=progress;

            saveData("CBUTTON_TM_VALUE",progress);
            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("CMD",3);
            intent.putExtra("VALUE",progress);
            sendBroadcast(intent);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.equals(mCbuttonSize)){
            Constant.CBUTTON_SIZE=seekBar.getProgress();
            saveData("CBUTTON_SIZE",Constant.CBUTTON_SIZE);
            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("CMD",5);
            intent.putExtra("VALUE",seekBar.getProgress());
            sendBroadcast(intent);
        }if(seekBar.equals(mCbuttonRidaus)){
            Constant.RADIUS=seekBar.getProgress()+Constant.SCREE_MAX_X/8;
            saveData("RADIUS",Constant.RADIUS);
            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("CMD",6);
            intent.putExtra("VALUE",Constant.RADIUS);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                Constant.IS_CBUTTON_ZD=isChecked;

            saveData("IS_CBUTTON_ZD",isChecked);

    }
    public static String PUPPY_PREFERENCES_NAME="PUPPY_PREFERENCES_NAME";



    public void saveData(String name,int value){

        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putInt(name, value);
        savedata.commit();
    }
    public  void saveData(String name,boolean value){

        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putBoolean(name, value);
        savedata.commit();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
