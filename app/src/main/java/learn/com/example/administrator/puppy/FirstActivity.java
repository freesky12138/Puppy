package learn.com.example.administrator.puppy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FirstActivity extends Activity {

    WindowManager wm ;



    public static List<HashMap<String,Object>> list= new ArrayList<>();
    public static ArrayList<AppsItemInfo> appsItemInfos=new ArrayList<AppsItemInfo>();

    @Override
    protected void onDestroy() {

        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }


    public Thread DataThread=
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            //  Log.i("learn.com.example.administrator.puppy",appsItemInfos.size()+"  appsInfos");
                            try{

                                PackageManager packageManager=FirstActivity.this.getPackageManager();
                                List<PackageInfo> packages = packageManager.getInstalledPackages(0);
                                for (int i=0;i<packages.size();i++){

                                    AppsItemInfo appsItemInfo=new AppsItemInfo();

                                    PackageInfo packageInfo=packages.get(i);

                                    boolean flag=false;
                                    for(int j=0;j<appsItemInfos.size();j++){
                                        if(appsItemInfos.get(j).getPackageName().equals(packageInfo.packageName)){
                                            flag=true;
                                            break;
                                        }
                                    }
                                    if(flag){
                                        continue;
                                    }

                                    if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM
                                            &&(!(packageInfo.packageName.equals("com.sec.android.gallery3d")||
                                            // packageInfo.packageName.equals("com.android.phone")||
                                            packageInfo.packageName.equals("com.android.mms")||
                                            packageInfo.packageName.equals("com.android.settings")||
                                            packageInfo.packageName.equals("com.sec.android.widgetapp.alarmwidget")||
                                            packageInfo.packageName.equals("com.sec.android.app.popupcalculator")||
                                            packageInfo.packageName.equals("com.android.contacts")||
                                            packageInfo.packageName.equals("com.android.calendar")||
                                            packageInfo.packageName.equals("com.sec.android.app.sbrowser")||
                                            packageInfo.packageName.equals("com.sec.android.app.camera")))){//过滤掉系统程序
                                        continue;
                                    }

                                    Drawable drawable=packageManager.getApplicationIcon(packageInfo.applicationInfo);
                                    //     Log.i("learn.com.example.administrator.puppy",drawable.getIntrinsicHeight()+"  "+drawable.getMinimumWidth());
                                    //Bitmap tempBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.img_puppy_main);
                                    drawable=FirstActivity.this.zoomDrawable(drawable,Constant.SCREE_MAX_X/8,Constant.SCREE_MAX_X/8);

                                    appsItemInfo.setIcon(drawable);
                                    appsItemInfo.setLabel(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                                    appsItemInfo.setPackageName(packageInfo.packageName);

                                    HashMap<String ,Object> map=new HashMap<>();

                                    map.put("name",appsItemInfo.getLabel());
                                    map.put("img", appsItemInfo.getIcon());


                                    //  Log.i("learn.com.example.administrator.puppy", i + "  " + appsItemInfo.getLabel() + "  " + appsItemInfo.getPackageName());

                                    copyDrawToFile(appsItemInfo.getPackageName(),appsItemInfo.getLabel(),appsItemInfo.getIcon());
                                    list.add(map);
                                    appsItemInfos.add(appsItemInfo);
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
            );


    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);

        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();

    }




    public void copyDrawToFile(String PackgeName,String FileName,Drawable drawable){
        //File f = new File(getSDPath()+"/"+path, bitName + ".png");
        String t="sdfdf";
       String temp=getSDPath()+"/"+"/Puppy";
        File filePath=new File(getSDPath()+"/Puppy",FileName+"-"+PackgeName+".png");
        File p= new File(getSDPath()+"/"+"/Puppy");
        if(!p.exists()){
            filePath.mkdirs();
        }

        try {
            filePath.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

     //   Log.i("learn.com.example.administrator.puppy",filePath.getPath());

        try {
            Bitmap bitmap= drawableToBitmap(drawable);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,0,baos);
            InputStream inputStream= new ByteArrayInputStream(baos.toByteArray());
            FileOutputStream outputStream=new FileOutputStream(filePath.getPath());
            byte[] buffer=new byte[8192];
            int count=0;
            while ((count=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,count);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        wm= (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Constant.SCREE_MAX_X=wm.getDefaultDisplay().getWidth();
        Constant.SCREE_MAX_Y=wm.getDefaultDisplay().getHeight();

        AdManager.getInstance(this).init("83aba7200d436042", "6b985d2bf7de1de4", false);//有米广告
   //  SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).loadSpotAds();

//        SpotManager.getInstance(this).showSplashSpotAds(this, MainActivity.class);

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);
                FirstActivity.this.finish();

            }
        };
        handler.sendEmptyMessageDelayed(0, 1900);


        this.DataThread.start();

        DrawView drawView=new DrawView(this);
        this.blur(drawView.getBitmap());
    }



    public void onBackPressed() {

        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }





    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }*/
    //使用Bitmap加Matrix来缩放
    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap oldbmp = drawableToBitmap(drawable);
        oldbmp=getRCB(oldbmp,oldbmp.getHeight());
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width*3);
        float scaleHeight = ((float) h / height*3);
        matrix.postScale(scaleWidth, scaleHeight);
        oldbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        Drawable drawableTemp=new BitmapDrawable(null, oldbmp);
        //  Log.i("learn.com.example.administrator.puppy", drawableTemp.getMinimumWidth()+"  temp");


        return drawableTemp;
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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



    public class DrawView{

        Bitmap bufferBitmap;
        Canvas bufferCanvas;
        Point screenSize;
        Random rand = new Random();
        Canvas canvas=new Canvas();

        public Bitmap getBitmap(){
            return bufferBitmap;
        }

        // TODO Auto-generated constructor stub
        public DrawView(Context context){

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);//获取屏幕的大小

            screenSize = new Point(metrics.widthPixels,metrics.heightPixels);

            bufferBitmap = Bitmap.createBitmap(screenSize.x,screenSize.y,
                    Bitmap.Config.ARGB_8888);//Bitmap.Config.ARGB_8888设置为32位色
            bufferCanvas= new Canvas(bufferBitmap);//绑定 bufferBitmap

            WallpaperManager wallpaperManager = WallpaperManager
                    .getInstance(FirstActivity.this);
            // 获取当前壁纸
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            // 将Drawable,转成Bitmap
            Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();

            onDraw();
        }

        protected void onDraw() {
            // TODO Auto-generated method stub

            drawOnBuffer();//存储bitmap

            canvas.drawBitmap(bufferBitmap,0,0,new Paint());
        }

        private void drawOnBuffer() {
            // TODO Auto-generated method stub
            Paint paint = new Paint();
            paint.setAntiAlias(true); //设置画笔的锯齿效果

            while(true){
                int color1=rand.nextInt(40) + 180;
                int color2=rand.nextInt(40) + 180;
                int color3=rand.nextInt(40) + 180;
                if(Math.abs(color1-color2)<10|| Math.abs(color2-color3)<10 ||Math.abs(color3-color1)<10)
                    continue;
                bufferCanvas.drawColor(Color.rgb(color1,color2,color3));
                break;
            }
         //   bufferCanvas.drawColor(Color.WHITE);

            for(int i =0 ;i< 30;i++){{

                while(true){
                    int color1=rand.nextInt(40) + 180;
                    int color2=rand.nextInt(40) + 180;
                    int color3=rand.nextInt(40) + 180;
                    if(Math.abs(color1-color2)<10|| Math.abs(color2-color3)<10 ||Math.abs(color3-color1)<10)
                        continue;
                    paint.setColor(Color.rgb(color1,color2,color3));
                    break;
                }

                // paint.setColor(Color.parseColor(Constant.COLOR_LESS[rand.nextInt(Constant.COLOR_LESS.length)]));

               /* int x= rand.nextInt(screenSize.x);
                int y= rand.nextInt(screenSize.y);

                int radius = screenSize.x/3;
                bufferCanvas.drawCircle(x, y, radius, paint);*/


                paint.setStyle(Paint.Style.FILL );


                int pointSum=20;
                Path path = new Path(); //定义一条路径
                int sX=rand.nextInt(screenSize.x);
                int sY=rand.nextInt(screenSize.y);
                path.moveTo(sX, sY); //移动到 坐标10,10

                for(int j=0;j<pointSum-1;j++){
                    path.lineTo((sX + rand.nextInt(screenSize.y/2) - screenSize.y/4), sY+rand.nextInt(screenSize.y/2) - screenSize.y/4);
                }
                path.moveTo(sX,sY);


                bufferCanvas.drawPath(path, paint);

      /*          paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(20);
           //     paint.setStyle(Paint.Style.FILL_AND_STROKE);
                Path path2 = new Path();
                path2.moveTo(27, 360);
                path2.lineTo(54, 360);
                path2.lineTo(70, 392);
                path2.lineTo(40, 420);
                path2.lineTo(10, 392);
                path2.close();
                bufferCanvas.drawPath(path2, paint);*/

             /*   paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);

                // 实例化路径
                Path mPath = new Path();
                // 连接路径到点[100,100]
                mPath.lineTo(100, 100);
                // 绘制路径
                bufferCanvas.drawPath(mPath, paint);*/

            }
                /**
                 *   Path path = new Path(); //定义一条路径

                 path.moveTo(10, 10); //移动到 坐标10,10

                 path.lineTo(50, 60);

                 path.lineTo(200,80);

                 path.lineTo(10, 10);



                 canvas.drawPath(path, paint);
                 *
                 */
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg) {
        long startMs = System.currentTimeMillis();
        float radius = 20;

        bkg = small(bkg);
        Bitmap bitmap = bkg.copy(bkg.getConfig(), true);

    final RenderScript rs = RenderScript.create(this);
        final Allocation input = Allocation.createFromBitmap(rs, bkg, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

       // bitmap = big(bitmap);
/*


        Drawable drawable=new BitmapDrawable(null ,Constant.BG);
        drawable=zoomDrawable(drawable,);
        Constant.BG=drawableToBitmap(drawable);
*/
     //   Constant.BG=Bitmap.createBitmap(bitmap, 200, 300, 300, 500);
        Constant.BIG_BG=bitmap;
        Constant.BG=Bitmap.createBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5, bitmap.getWidth() / 5 * 3, bitmap.getHeight() / 5 * 3);
    //    Constant.BG= Bitmap.createScaledBitmap( Constant.BG, 1080, 1920, true);
//270  480
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.first_layout);

        linearLayout.setBackground(new BitmapDrawable(getResources(),  Constant.BG));

        rs.destroy();
        Log.d("zhangle","blur take away:" + (System.currentTimeMillis() - startMs )+ "ms");
    }

    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(4f, 4f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
}
