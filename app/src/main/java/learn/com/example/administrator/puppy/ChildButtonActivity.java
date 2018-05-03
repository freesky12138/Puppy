package learn.com.example.administrator.puppy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.spot.SpotManager;

public class ChildButtonActivity extends Activity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,CompoundButton.OnCheckedChangeListener{

    DevicePolicyManager policyManager;
    ComponentName componentName;//设备管理器

    Switch mChildButtonSwitch;
    SeekBar mChildButtonSeekBar;
    SeekBar mChildButtonSpeed;



    ImageButton mImgSetone;
    ImageButton mImgSettwo;
    ImageButton mImgSetthree;
    ImageButton mImgSetfour;

    Button mButtonSetone;
    Button mButtonSettwo;
    Button mButtonSetthree;
    Button mButtonSetfour;

    Switch mSetDrive;

    TextView SetButtonTextView;

    public static String INTENT_EXT="SET";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_button);
      //  SpotManager.getInstance(this).showSpotAds(this);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.child_layout);
        linearLayout.setBackground(new BitmapDrawable(null, Constant.BG));

        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);//设备管理器
        InitView();

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void InitView() {
        mChildButtonSeekBar=(SeekBar)findViewById(R.id.child_button_touming_seekBar);
        mChildButtonSwitch=(Switch)findViewById(R.id.is_child_button);
        mChildButtonSpeed=(SeekBar)findViewById(R.id.child_button_set_speed);

        mChildButtonSwitch.setChecked(Constant.IS_CHILD_BUTTON_ZD);

        mChildButtonSpeed.setMax(300);
        mChildButtonSpeed.setProgress(Constant.CHILDBUTTON_SET_SPEED);

        mChildButtonSeekBar.setMax(255);
        mChildButtonSeekBar.setProgress(Constant.CHILEBUTTON_TM_VALUE);

        SetButtonTextView=(TextView)findViewById(R.id.set_button_textview);

        mChildButtonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Constant.IS_CHILD_BUTTON_ZD = isChecked;
                saveData("IS_CHILD_BUTTON_ZD", Constant.IS_CHILD_BUTTON_ZD);
            }

        });


        mImgSetone=(ImageButton) findViewById(R.id.img_set_one);
        mImgSettwo=(ImageButton) findViewById(R.id.img_set_two);
        mImgSetthree=(ImageButton) findViewById(R.id.img_set_three);
        mImgSetfour=(ImageButton) findViewById(R.id.img_set_four);

        mButtonSetone=(Button) findViewById(R.id.set_one);
        mButtonSettwo=(Button) findViewById(R.id.set_two);
        mButtonSetthree=(Button) findViewById(R.id.set_three);
        mButtonSetfour=(Button) findViewById(R.id.set_four);
     /*   mImgSetfour=new ImageButton(this);
        mImgSetthree=new ImageButton(this);
        mImgSettwo=new ImageButton(this);
        mImgSetone=new ImageButton(this);
*/

        mSetDrive=(Switch) findViewById(R.id.set_drive);


        mSetDrive.setOnCheckedChangeListener(this);
        boolean active = policyManager.isAdminActive(componentName);
        mSetDrive.setChecked(active);

        mImgSetone.setOnClickListener(this);
        mImgSettwo.setOnClickListener(this);
        mImgSetthree.setOnClickListener(this);
        mImgSetfour.setOnClickListener(this);

        mButtonSetone.setOnClickListener(this);
        mButtonSettwo.setOnClickListener(this);
        mButtonSetthree.setOnClickListener(this);
        mButtonSetfour.setOnClickListener(this);


        mImgSetone.setBackground(Constant.CHILDBUTTONDRAWABLE[0]);
        mImgSettwo.setBackground(Constant.CHILDBUTTONDRAWABLE[1]);
        mImgSetthree.setBackground(Constant.CHILDBUTTONDRAWABLE[2]);
        mImgSetfour.setBackground(Constant.CHILDBUTTONDRAWABLE[3]);

        mButtonSetone.setText(Constant.PACKGELABLE[0]);
                mButtonSettwo.setText(Constant.PACKGELABLE[1]);
        mButtonSetthree.setText(Constant.PACKGELABLE[2]);
                mButtonSetfour.setText(Constant.PACKGELABLE[3]);


        mChildButtonSeekBar.setOnSeekBarChangeListener(this);
        mChildButtonSpeed.setOnSeekBarChangeListener(this);
    //    toggleMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();


        mImgSetone.setBackground(Constant.CHILDBUTTONDRAWABLE[0]);
        mImgSettwo.setBackground(Constant.CHILDBUTTONDRAWABLE[1]);
        mImgSetthree.setBackground(Constant.CHILDBUTTONDRAWABLE[2]);
        mImgSetfour.setBackground(Constant.CHILDBUTTONDRAWABLE[3]);

        mImgSetfour.getBackground().setAlpha(Constant.CHILEBUTTON_TM_VALUE);
        mImgSettwo.getBackground().setAlpha( Constant.CHILEBUTTON_TM_VALUE );
        mImgSetthree.getBackground().setAlpha( Constant.CHILEBUTTON_TM_VALUE );
        mImgSetone.getBackground().setAlpha( Constant.CHILEBUTTON_TM_VALUE );

        mButtonSetone.setText(Constant.PACKGELABLE[0]);
        mButtonSettwo.setText(Constant.PACKGELABLE[1]);
        mButtonSetthree.setText(Constant.PACKGELABLE[2]);
        mButtonSetfour.setText(Constant.PACKGELABLE[3]);
        MobclickAgent.onResume(this);
    }

    static int flagIntentExt=1;
    @Override
    public void onClick(View v) {


        if( v.equals(mImgSetfour)||v.equals(mButtonSetfour)){

          flagIntentExt=4;
        }else if(v.equals(mImgSetone)||v.equals(mButtonSetone)){
            flagIntentExt=1;
        }else if( v.equals(mImgSettwo)||v.equals(mButtonSettwo)){
            flagIntentExt=2;
        }else if(v.equals(mImgSetthree)||v.equals(mButtonSetthree)){
            flagIntentExt=3;
        }

        CharSequence[] chars={"选择应用","选择按键","返回"};
        new AlertDialog.Builder(this).setTitle("请选择方式")
                .setItems(chars, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent intent=new Intent(ChildButtonActivity.this,SetAppsActivity.class);
                            intent.putExtra(INTENT_EXT,flagIntentExt);
                            startActivity(intent);
                        }else if(which==1){
                            Intent intent=new Intent(ChildButtonActivity.this,SetFuncsActivity.class);
                            intent.putExtra(INTENT_EXT,flagIntentExt);
                            startActivity(intent);
                        }
                    }
                })
                .show();
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(mChildButtonSeekBar))
        {


            mImgSetfour.getBackground().setAlpha( progress);
            mImgSettwo.getBackground().setAlpha( progress);
            mImgSetthree.getBackground().setAlpha( progress);
            mImgSetone.getBackground().setAlpha(progress );

            Constant.CHILEBUTTON_TM_VALUE=progress;
            saveData("CHILEBUTTON_TM_VALUE",Constant.CHILEBUTTON_TM_VALUE);
            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("CMD", 4);
            intent.putExtra("VALUE", progress);
            sendBroadcast(intent);


        }else if(seekBar.equals(mChildButtonSpeed)){
            Constant.CHILDBUTTON_SET_SPEED=progress;
            saveData("CHILDBUTTON_SET_SPEED",Constant.CHILDBUTTON_SET_SPEED);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



        if(isChecked){
                policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                componentName = new ComponentName(this, AdminReceiver.class);
                Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "------ 其他描述 ------\n使用锁屏需要获取设备管理器权值");
                startActivity(i);

        }else {

                policyManager.removeActiveAdmin(componentName);

        }
    }

   /* public void toggleMenu()
    {
        // 为menuItem添加平移动画和旋转动画
        View view[]={mImgSetone,mImgSettwo,mImgSetthree,mImgSetfour};

        RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.child_button_layout);

        float mRadius=Constant.SCREE_MAX_Y/2-Constant.CHILD_BUTTON_X*2;

        int count=5;
        for (int i = 0; i < 4; i++) {


            // end 0 , 0
            // start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int cWidth = Constant.CHILD_BUTTON_X;
            int cHeight = Constant.CHILD_BUTTON_Y;


            ct =Constant.SCREE_MAX_Y/2 -cHeight - ct;


           cl =  Constant.SCREE_MAX_Y/2- cWidth - cl;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(cl, ct,0,0);

            view[i].setLayoutParams(params);
        }

    }*/

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
}
