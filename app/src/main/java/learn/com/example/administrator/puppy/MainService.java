package learn.com.example.administrator.puppy;

import android.accessibilityservice.AccessibilityService;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.os.Environment;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;


import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainService extends AccessibilityService implements FloatView.OnMenuItemClickListener{
    /** Called when the activity is first created. */
    private WindowManager wm;
   public static WindowManager.LayoutParams wmlp= new WindowManager.LayoutParams();
    private FloatView fl;

    private String vibratorString = Service.VIBRATOR_SERVICE;
    private Vibrator mVibrator01; // 初始化振动; //震动

    ImageView imageViewMain;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    @Override
    public void onCreate() {
        wm = (WindowManager) getApplicationContext().getSystemService(
                WINDOW_SERVICE);

        getScreeVal();
        for(int i=0;i<4;i++){

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    Constant.GROUP_IMG[i]);
            Constant.PACKGEDRAWABLE[i]=zoomDrawable(bitmap, Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
           /* Constant.IMGDRAWABLE[i]=
                    Constant.PACKGEDRAWABLE[i];
            */
            Constant.IMGDRAWABLE[i]=Constant.PACKGEDRAWABLE[i].getConstantState().newDrawable();

            Constant.CHILDBUTTONDRAWABLE[i]=Constant.PACKGEDRAWABLE[i].getConstantState().newDrawable();
        }
    }

    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap oldbmp = drawableToBitmap(drawable);
        oldbmp=getRCB(oldbmp,oldbmp.getHeight()/2);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w*3 / width);
        float scaleHeight = ((float) h*3 / height);
        matrix.postScale(scaleWidth, scaleHeight);
        oldbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);




        Drawable drawableTemp=new BitmapDrawable(null, oldbmp);
        //  Log.i("learn.com.example.administrator.puppy", drawableTemp.getMinimumWidth()+"  temp");


        return drawableTemp;
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

    private Drawable zoomDrawable(Bitmap drawable, int w, int h) {
        int width = drawable.getWidth();
        int height = drawable.getHeight();
        Bitmap oldbmp = drawable;
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w *3/ width);
        float scaleHeight = ((float) h *3/ height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        Drawable drawableTemp=new BitmapDrawable(null, newbmp);
     //   Log.i("learn.com.example.administrator.puppy", drawableTemp.getMinimumWidth()+"  temp");

        return drawableTemp;
    }

    private Drawable zoomRealDrawable(Drawable d, int w, int h) {
        Bitmap drawable=drawableToBitmap(d);
        int width = drawable.getWidth();
        int height = drawable.getHeight();
        Bitmap oldbmp = drawable;
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w *3/ width);
        float scaleHeight = ((float) h *3/ height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        Drawable drawableTemp=new BitmapDrawable(null, newbmp);
        //   Log.i("learn.com.example.administrator.puppy", drawableTemp.getMinimumWidth()+"  temp");

        return drawableTemp;
    }


    private void createFloatView() {


        mVibrator01 = (Vibrator) getApplicationContext().getSystemService(vibratorString);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_puppy_main);

        Drawable drawable=zoomDrawable(bitmap, Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);

        Constant.PACKGEDRAWABLE[0]=zoomDrawable(drawableToBitmap(Constant.PACKGEDRAWABLE[0]), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
        Constant.PACKGEDRAWABLE[1]=zoomDrawable(drawableToBitmap(Constant.PACKGEDRAWABLE[1]), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
        Constant.PACKGEDRAWABLE[2]=zoomDrawable(drawableToBitmap(Constant.PACKGEDRAWABLE[2]), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
        Constant.PACKGEDRAWABLE[3]=zoomDrawable(drawableToBitmap(Constant.PACKGEDRAWABLE[3]), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);

    //    Bitmap bitmapMain= BitmapFactory.decodeResource(getResources(),R.drawable.img_puppy_main);
      //  Bitmap bitmapChild=BitmapFactory.decodeResource(getResources(),R.drawable.img_puppy_main);

     //   Log.i("learn.com.example.administrator.puppy", Constant.SCREE_MAX_X+"   ");


        Constant.MAIN_BUTTON_X=(Constant.SCREE_MAX_X/8*(25+Constant.CBUTTON_SIZE)/100);
        Constant.MAIN_BUTTON_Y= (Constant.SCREE_MAX_X/8*(25+Constant.CBUTTON_SIZE)/100);
        Constant.CHILD_BUTTON_Y=(Constant.SCREE_MAX_X/8);
        Constant.CHILD_BUTTON_X=(Constant.SCREE_MAX_X/8);

        if(Constant.RADIUS==0)
            Constant.RADIUS=Constant.SCREE_MAX_X/4+10;

        if(Constant.MAIN_BUTTON_X/2<Constant.CHILD_BUTTON_X/2){
            Constant.WMLPX=Constant.RADIUS+Constant.CHILD_BUTTON_X;
            Constant.WMLPY=Constant.RADIUS+Constant.CHILD_BUTTON_Y;
        }else {

            Constant.WMLPX=Constant.RADIUS+Constant.CHILD_BUTTON_X/2+Constant.MAIN_BUTTON_X/2;
            Constant.WMLPY=Constant.RADIUS+Constant.CHILD_BUTTON_Y/2+Constant.MAIN_BUTTON_Y/2;

        }
        wmlp.type = 2002;//2002应用程序窗口的子面板。显示于所有面板窗口的上层

        wmlp.format = 1;

        wmlp.flags |= 8;//让window不能获得焦点，这样用户快就不能向该window发送按键事件
        wmlp.gravity = Gravity.LEFT | Gravity.TOP; // let the float view to the right top

        wmlp.x = 0;
        wmlp.y = 0;

        fl=new FloatView(this);

        //      fl=(FloatView)findViewById(R.id.id_menu);

        imageViewMain=new ImageView(this);
        imageView1=new ImageView(this);
        imageView2=new ImageView(this);
        imageView3=new ImageView(this);
        imageView4=new ImageView(this);


        imageViewMain.setBackground(drawable);

      //  Constant.PACKGEDRAWABLE[0]=zoomRealDrawable(Constant.PACKGEDRAWABLE[0], Constant.SCREE_MAX_X / 8, Constant.SCREE_MAX_X / 8);

        imageView1.setBackground(Constant.PACKGEDRAWABLE[0]);
        imageView2.setBackground(Constant.PACKGEDRAWABLE[1]);
        imageView3.setBackground(Constant.PACKGEDRAWABLE[2]);
        imageView4.setBackground(Constant.PACKGEDRAWABLE[3]);


        /*int alpha=150;
        Constant.CBUTTON_TM_VALUE=alpha*100/255;*/
        int alpha=Constant.CBUTTON_TM_VALUE;
        imageViewMain.getBackground().setAlpha(alpha);
        alpha=Constant.CHILEBUTTON_TM_VALUE;
        //Constant.CHILEBUTTON_TM_VALUE=alpha*100/255;
        imageView1.getBackground().setAlpha(alpha);
        imageView2.getBackground().setAlpha(alpha);
        imageView3.getBackground().setAlpha(alpha);
        imageView4.getBackground().setAlpha(alpha);

        fl.addView(imageViewMain, 0);
        fl.addView(imageView1, 1);
        fl.addView(imageView2, 2);
        fl.addView(imageView3, 3);
        fl.addView(imageView4, 4);

        wmlp.width=Constant.MAIN_BUTTON_X;
        wmlp.height= Constant.MAIN_BUTTON_Y;

        wm.addView(fl,wmlp);

    }



    public void setView(int num){
        fl.serviceSetChildView(num);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        wm.removeView(fl);

        Constant.IS_SERVICE_RUN=false;

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }



    public void getScreeVal() {
        DisplayMetrics dm =getResources().getDisplayMetrics();
        float width = dm.widthPixels;
        float height =dm.heightPixels;
            Constant.SCREE_MAX_Y=(int)height;
            Constant.SCREE_MAX_X=(int)width;


    }


    class CmdReceiver extends BroadcastReceiver {

        private static final String TAG = "NormalBroadcast";


        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle is=intent.getExtras();

            int value=is.getInt("VALUE");
            int num=is.getInt("NUM");
            switch (is.getInt("CMD")){
                case 0:
                    closeView();
                    break;
                case 1:
                    openView();
                    break;
                case 2:
                    setView(num);
                    break;
                case 3://设置主按键透明度

                    fl.setCButtonAlpha(0,value );
                    break;
                case 4://设置分按键透明度


                    for(int i=1;i<5;i++){
                        fl.setChildButtonAlpha(i,Constant.CHILEBUTTON_TM_VALUE);
                    }
                    break;
                case 5://设置大小
                    fl.setButtonSize(value);
                    break;
                case 6:
                    fl.setRidaus(value);
                    break;
            }

        }




    }
    public void closeView(){
        if(Constant.IS_FLOATVIEW_SHOW==true){
            wm.removeView(fl);
            Constant.IS_FLOATVIEW_SHOW=false;
        }

    }
    public void openView(){

        mVibrator01.vibrate(new long[] { 1, 20 }, -1); // 震动

        if(Constant.IS_FLOATVIEW_SHOW==false){
            wm.addView(fl, wmlp);
            Constant.IS_FLOATVIEW_SHOW=true;
        }
    }


    public static String PUPPY_PREFERENCES_NAME="PUPPY_PREFERENCES_NAME";





        public void getAllData(){
            SharedPreferences sharedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0); // 保存值到系统

            Constant.IS_CBUTTON_ZD=sharedata.getBoolean("IS_CBUTTON_ZD", false);
            Constant.IS_CHILD_BUTTON_ZD=sharedata.getBoolean("IS_CHILD_BUTTON_ZD", false);
            Constant.DOUBLE_CLICK=sharedata.getInt("DOUBLE_CLICK", 2);
            Constant.LONG_CLICK=sharedata.getInt("LONG_CLICK", 1);

            Constant.CBUTTON_TM_VALUE=sharedata.getInt("CBUTTON_TM_VALUE", 255);
            Constant.CHILEBUTTON_TM_VALUE=sharedata.getInt("CHILEBUTTON_TM_VALUE", 255);
            Constant.CBUTTON_SIZE=sharedata.getInt("CBUTTON_SIZE", 75);
            Constant.CHILDBUTTON_SET_SPEED=sharedata.getInt("CHILDBUTTON_SET_SPEED", 100);
            Constant.RADIUS=sharedata.getInt("RADIUS", 0);

            Constant.PUPPY_SAVE_MODE_ONE=sharedata.getInt("PUPPY_SAVE_MODE_ONE",0);
            Constant.PUPPY_SAVE_MODE_TWO=sharedata.getInt("PUPPY_SAVE_MODE_TWO",0);
            Constant.PUPPY_SAVE_MODE_THREE=sharedata.getInt("PUPPY_SAVE_MODE_THREE",0);
            Constant.PUPPY_SAVE_MODE_FORE=sharedata.getInt("PUPPY_SAVE_MODE_FORE",0);
            Constant.PUPPY_SAVE_ONE=sharedata.getString("PUPPY_SAVE_ONE", "0");
            Constant.PUPPY_SAVE_TWO=sharedata.getString("PUPPY_SAVE_TWO", "1");
            Constant.PUPPY_SAVE_THREE=sharedata.getString("PUPPY_SAVE_THREE", "2");
            Constant.PUPPY_SAVE_FORE=sharedata.getString("PUPPY_SAVE_FORE", "3");



            if(Constant.PUPPY_SAVE_MODE_ONE==0){
                Constant.PACKGEDRAWABLE[0]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_ONE)].getConstantState().newDrawable();
                Constant.CHILDBUTTONDRAWABLE[0]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_ONE)].getConstantState().newDrawable();
                Constant.PACKGENAME[0]= Constant.PUPPY_SAVE_ONE;
                Constant.PACKGELABLE[0]=Constant.PACKGELABLESAVE[Integer.valueOf(Constant.PUPPY_SAVE_ONE)];
            }else {
                String s[]=Constant.PUPPY_SAVE_ONE.split("-");
                Constant.PACKGENAME[0]=s[1];
                Constant.PACKGELABLE[0]=s[0];
                Constant.PACKGEDRAWABLE[0]=zoomDrawable(getLocalBitmap(Constant.PUPPY_SAVE_ONE), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
                Constant.CHILDBUTTONDRAWABLE[0]=Constant.PACKGEDRAWABLE[0].getConstantState().newDrawable();
            }

            if(Constant.PUPPY_SAVE_MODE_TWO==0){
                Constant.PACKGEDRAWABLE[1]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_TWO)].getConstantState().newDrawable();
                Constant.CHILDBUTTONDRAWABLE[1]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_TWO)].getConstantState().newDrawable();
                Constant.PACKGENAME[1]= Constant.PUPPY_SAVE_TWO;
                Constant.PACKGELABLE[1]=Constant.PACKGELABLESAVE[Integer.valueOf(Constant.PUPPY_SAVE_TWO)];
            }else {
                String s[]=Constant.PUPPY_SAVE_TWO.split("-");
                Constant.PACKGENAME[1]=s[1];
                Constant.PACKGELABLE[1]=s[0];
                Constant.PACKGEDRAWABLE[1]=zoomDrawable(getLocalBitmap(Constant.PUPPY_SAVE_TWO), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
                Constant.CHILDBUTTONDRAWABLE[1]=Constant.PACKGEDRAWABLE[1].getConstantState().newDrawable();
            }

            if(Constant.PUPPY_SAVE_MODE_THREE==0){
                Constant.PACKGENAME[2]= Constant.PUPPY_SAVE_THREE;
                Constant.PACKGELABLE[2]=Constant.PACKGELABLESAVE[Integer.valueOf(Constant.PUPPY_SAVE_THREE)];
                Constant.PACKGEDRAWABLE[2]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_THREE)].getConstantState().newDrawable();
                Constant.CHILDBUTTONDRAWABLE[2]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_THREE)].getConstantState().newDrawable();
            }else {
                String s[]=Constant.PUPPY_SAVE_THREE.split("-");
                Constant.PACKGENAME[2]=s[1];
                Constant.PACKGELABLE[2]=s[0];
                Constant.PACKGEDRAWABLE[2]=zoomDrawable(getLocalBitmap(Constant.PUPPY_SAVE_THREE), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
                Constant.CHILDBUTTONDRAWABLE[2]=Constant.PACKGEDRAWABLE[2].getConstantState().newDrawable();
            }

            if(Constant.PUPPY_SAVE_MODE_FORE==0){
                Constant.PACKGENAME[3]= Constant.PUPPY_SAVE_FORE;
                Constant.PACKGELABLE[3]=Constant.PACKGELABLESAVE[Integer.valueOf(Constant.PUPPY_SAVE_FORE)];
                Constant.PACKGEDRAWABLE[3]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_FORE)].getConstantState().newDrawable();
                Constant.CHILDBUTTONDRAWABLE[3]=Constant.IMGDRAWABLE[Integer.valueOf(Constant.PUPPY_SAVE_FORE)].getConstantState().newDrawable();
            }else {
                String s[]=Constant.PUPPY_SAVE_FORE.split("-");
                Constant.PACKGENAME[3]=s[1];
                Constant.PACKGELABLE[3]=s[0];
                Constant.PACKGEDRAWABLE[3]=zoomDrawable(getLocalBitmap(Constant.PUPPY_SAVE_FORE), Constant.SCREE_MAX_X/8, Constant.SCREE_MAX_X/8);
                Constant.CHILDBUTTONDRAWABLE[3]=Constant.PACKGEDRAWABLE[3].getConstantState().newDrawable();
            }
        }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);


        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();

    }

    public Bitmap getLocalBitmap(String url){

        File filePath=new File(getSDPath()+"/Puppy",url+".png");
        FileInputStream inputStream=null;
        try {
           inputStream=new FileInputStream(filePath.getPath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }



    @Override
    protected void onServiceConnected() {

        super.onServiceConnected();
        getAllData();

        CmdReceiver receiver = new CmdReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_BROADCAST");
        registerReceiver(receiver, filter);

        Constant.IS_SERVICE_RUN=true;


            createFloatView();

            fl.setOnMenuItemClickListener(this);


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }





    @Override
    public void onInterrupt() {

    }

    @Override
    public void onClick(View view, int pos){

        if(Constant.IS_CHILD_BUTTON_ZD){
            mVibrator01.vibrate(new long[] { 1, 16 }, -1); // 震动
        }
        Intent intent;


        switch (Constant.PACKGENAME[pos-1]){
            case "0":
                this.performGlobalAction(this.GLOBAL_ACTION_BACK);
                break;
            case "1":
                this.performGlobalAction(this.GLOBAL_ACTION_HOME);
                break;
            case "2":
                this.performGlobalAction(this.GLOBAL_ACTION_RECENTS);
                break;
            case "3":
             /*   try {
                    Runtime.getRuntime().exec("su -c input keyevent " + KeyEvent.KEYCODE_POWER);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //

                DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName componentName = new ComponentName(this, AdminReceiver.class);
                boolean active = policyManager.isAdminActive(componentName);
                if(active){
                    policyManager.lockNow();
                }else{
                    Toast.makeText(this,"第一次使用锁屏请开启设备管理器权限",Toast.LENGTH_LONG).show();
                    intent = new Intent(this,ChildButtonActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                    startActivity(intent);
                }
                //
                break;

            default:
          //      for(int i=0;i<SetAppsActivity.appsItemInfos.size();i++){
                        intent= this.getPackageManager().getLaunchIntentForPackage(Constant.PACKGENAME[pos-1]);
                        startActivity(intent);
                        break;

            //    }
            /*case 4://联系人
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.setClassName("com.android.contacts", "com.android.contacts.activities.PeopleActivity");
                startActivity(intent);
                break;
            case 5://通话记录
                intent = new Intent(Intent.ACTION_CALL_BUTTON );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                break;
            case 6://拨号
                intent= new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.setClassName("com.android.contacts", "com.android.contacts.DialtactsActivity");
                startActivity(intent);
                break;
            case 7://短信   xx
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.setType("vnd.android-dir/mms-sms"); //
                // intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
                startActivity(intent);
                break;
            case 8://相机
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "camera.jpg")));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivity(intent);
                break;
            case 9://"相册",
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image*//*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("outputX", 80);
                intent.putExtra("outputY", 80);
                intent.putExtra("return-data", true);
                startActivity(intent);
                break;
            case 10://"闹钟",
                Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(alarms);
                break;
            case 11://  "日历",

                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                ComponentName  cn = null;

                if(Integer.parseInt (Build.VERSION.SDK ) >=8){
                    cn = new ComponentName("com.android.calendar","com.android.calendar.LaunchActivity");
                }
                else{
                    cn = new ComponentName("com.google.android.calendar","com.android.calendar.LaunchActivity");
                }

                intent.setComponent(cn);

                startActivity(intent);
                break;*/
        }

    }

    @Override
    protected boolean onKeyEvent (KeyEvent event){


        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.orientation获得当前屏幕状态是横向或者竖向
        //Configuration.ORIENTATION_PORTRAIT 表示竖向
        //Configuration.ORIENTATION_LANDSCAPE 表示横屏

        DisplayMetrics dm =getResources().getDisplayMetrics();
        float width = dm.widthPixels;
        float height =dm.heightPixels;
      /*  final float scale = getResources().getDisplayMetrics().density;
        width=width * scale + 0.5f;
        height=height * scale + 0.5f;*/
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Constant.SCREE_MAX_Y=(int)height;
            Constant.SCREE_MAX_X=(int)width;
            fl.stayY();
        }
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Constant.SCREE_MAX_Y=(int)height;
            Constant.SCREE_MAX_X=(int)width;
            fl.stayY();
        }
    }
}
