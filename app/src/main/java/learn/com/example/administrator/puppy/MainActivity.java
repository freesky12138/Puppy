package learn.com.example.administrator.puppy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    Switch mSetService;
    Button mCbuttonSet;
    Button mChildButtonSet;
    Button mBgButtonSet;
    private RelativeLayout linearLayout;
    Button mToHelp;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager pm=getPackageManager();
            boolean isPermission=(PackageManager.PERMISSION_GRANTED==pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW",getPackageName()));

        if(isPermission==false){

            Toast.makeText(this,"请开启悬浮窗权限",Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
            startActivity(intent);

        }

        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);

// 获取要嵌入广告条的布局
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

// 将广告条加入到布局中
        adLayout.addView(adView);

     //   SpotManager.getInstance(this).showSpotAds(this);
/*
        SpotManager.getInstance(this).setSpotOrientation(
                SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);*/



        mBgButtonSet=(Button)findViewById(R.id.set_bg);
        mToHelp=(Button)findViewById(R.id.to_help_button);
        mSetService= (Switch) findViewById(R.id.set_service);

       //mTestButton=(Button)findViewById(R.id.test);

        mSetService.setChecked(Constant.IS_SERVICE_RUN && Constant.IS_FLOATVIEW_SHOW);


        mBgButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    setWallpaper(Constant.BIG_BG);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "设置背景成功", Toast.LENGTH_SHORT).show();
            }
        });
        mSetService.setOnCheckedChangeListener(this);

        mCbuttonSet=(Button)findViewById(R.id.set_cbutton);


        mChildButtonSet=(Button)findViewById(R.id.set_child_button);

        mCbuttonSet.setOnClickListener(this);
        mChildButtonSet.setOnClickListener(this);
        mToHelp.setOnClickListener(this);

        linearLayout=(RelativeLayout)findViewById(R.id.main_layout);
        linearLayout.setBackground(new BitmapDrawable(getResources(), Constant.BG));


        if(Constant.IS_SERVICE_RUN){
            this.mSetService.setChecked(true);
        }

    //    mTestButton.setOnClickListener(this);
    }



    @Override
    protected void onDestroy() {

  //      SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }

    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap oldbmp = drawableToBitmap(drawable);
        oldbmp=getRCB(oldbmp,oldbmp.getHeight()/2);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        oldbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        Drawable drawableTemp=new BitmapDrawable(null, oldbmp);
        //  Log.i("learn.com.example.administrator.puppy", drawableTemp.getMinimumWidth()+"  temp");


        return drawableTemp;
    }

    public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
        Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }



    @Override
    public void onClick(View v) {

        if(v.equals(mToHelp)){
            Intent intent=new Intent(this,HelpActivity.class);
            startActivity(intent);
        }


        if(Constant.IS_SERVICE_RUN==false){
            Toast.makeText(this,"第一次使用请先启动Puppy助手",Toast.LENGTH_LONG).show();

        }else {
            if(v.equals(mCbuttonSet)){

                Intent intent=new Intent(this,CbuttonActivity.class);
                startActivity(intent);
            }else if(v.equals(mChildButtonSet)){

                Intent intent=new Intent(this,ChildButtonActivity.class);
                startActivity(intent);
            }
        }




        /*else if(v.equals(mTestButton)){

          List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
          for (int i=0;i<packages.size();i++){
              Log.i("learn.com.example.administrator.puppy",packages.get(i).packageName);
              if("com.sec.android.app.camera".equals(packages.get(i).packageName)){
                  Intent intent=new Intent();
                  intent = this.getPackageManager().getLaunchIntentForPackage(packages.get(i).packageName);
                  startActivity(intent);
              }

          }
      }*/

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(Constant.IS_SERVICE_RUN==false){

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog,
                        (ViewGroup)findViewById(R.id.dialog));
                new AlertDialog.Builder(this).setTitle("开启Puppy").setView(layout)
                        .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent killIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(killIntent);
                            }
                        })
                        .show();
            }
            else{
                Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                intent.putExtra("CMD",1);
                sendBroadcast(intent);
            }
        }else {
            if(Constant.IS_SERVICE_RUN==false){
                Intent killIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(killIntent);
            }
            else{
                Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                intent.putExtra("CMD",0);
                sendBroadcast(intent);
            }
        }
    }
}
/*
* 1：返回
* 2：home
* 3：近期任务
* 4：锁屏
* 5：通讯录
* 6：通话记录
* 7：拨号
* 8：短信
* 9：相机
* 10：相册
* 11：闹钟
* 12：日历
* */
