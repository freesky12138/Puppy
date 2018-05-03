package learn.com.example.administrator.puppy;


import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetFuncsActivity extends ListActivity {

    private int SET_NUM=1;
    public static final  String[] functionString={"返回",
    "home",
    "近期任务",
    "锁屏"
  };





    private List<HashMap<String,Object>> getData() {
        List<HashMap<String,Object>> list = new ArrayList<>();

        Drawable[] drawableTemp=new Drawable[4];

        for(int i=0;i<4;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    Constant.GROUP_IMG[i]);
            drawableTemp[i]=zoomDrawable(bitmap,144,144);

            HashMap<String ,Object> map=new HashMap<>();
            map.put("name",functionString[i]);
            map.put("img",drawableTemp[i]);

            list.add(map);
        }
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SimpleAdapter adapter = new SimpleAdapter(this,getData(), R.layout.choose_view,
                new String[]{"img","name"},
                new int[]{R.id.item_img,R.id.item_name});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
            public boolean setViewValue(View view,Object data,String textRepresentation)
            {
                if(view instanceof ImageView && data instanceof Drawable)
                {
                    ImageView iv=(ImageView)view; iv.setImageDrawable((Drawable)data);
                    return true;
                }
                else return false; }
        });

        setListAdapter(adapter);
        SET_NUM=getIntent().getIntExtra(ChildButtonActivity.INTENT_EXT,1);

        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        this.getListView().setLayoutAnimation(lac);
        this.getListView().startLayoutAnimation();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

      /*  int tempChangeNum=0;
        switch (position){
            case 0:
                tempChangeNum=0;
                break;
            case 1:
                tempChangeNum=1;
                break;
            case 2:
                tempChangeNum=2;
                break;
            case 3:
                tempChangeNum=3;
                break;
            case 4://联系人
                tempChangeNum=4;
                break;
            case 5://通话记录
                tempChangeNum=5;
                break;
            case 6://拨号
                tempChangeNum=6;
                break;
            case 7://短信   xx
                tempChangeNum=7;
                break;
            case 8://相机
                tempChangeNum=8;
                break;
            case 9://"相册",
                tempChangeNum=9;
                break;
            case 10://"闹钟",
                tempChangeNum=10;
                break;
            case 11://  "日历",
                tempChangeNum=11;
                break;
        }*/
        Constant.PACKGENAME[SET_NUM-1]=String.valueOf(position);
       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                Constant.GROUP_IMG[position]);*/
        Constant.PACKGEDRAWABLE[SET_NUM-1]=Constant.IMGDRAWABLE[position];//zoomDrawable(bitmap,144,144);

        Constant.PACKGELABLE[SET_NUM-1]=functionString[position];

        Constant.CHILDBUTTONDRAWABLE[SET_NUM-1]=Constant.IMGDRAWABLE[position].getConstantState().newDrawable();

        switch (SET_NUM-1){
            case 0:
                Constant.PUPPY_SAVE_MODE_ONE=0;
                Constant.PUPPY_SAVE_ONE="0";
                saveData("PUPPY_SAVE_MODE_ONE",0);
                saveStringData("PUPPY_SAVE_ONE", String.valueOf(position));
                break;
            case 1:
                Constant.PUPPY_SAVE_MODE_TWO=0;
                Constant.PUPPY_SAVE_TWO="1";
                saveData("PUPPY_SAVE_MODE_TWO",0);
                saveStringData("PUPPY_SAVE_TWO", String.valueOf(position));
                break;
            case 2:
                Constant.PUPPY_SAVE_MODE_THREE=0;
                Constant.PUPPY_SAVE_THREE="2";
                saveData("PUPPY_SAVE_MODE_THREE",0);
                saveStringData("PUPPY_SAVE_THREE", String.valueOf(position));
                break;
            case 3:
                Constant.PUPPY_SAVE_MODE_FORE=0;
                Constant.PUPPY_SAVE_FORE="3";
                saveData("PUPPY_SAVE_MODE_FORE",0);
                saveStringData("PUPPY_SAVE_FORE", String.valueOf(position));
                break;
        }

        Intent intent = new Intent("android.intent.action.MY_BROADCAST");
        intent.putExtra("NUM",SET_NUM-1);
        intent.putExtra("CMD", 2);
        sendBroadcast(intent);
        finish();
    }



    private Drawable zoomDrawable(Bitmap drawable, int w, int h) {
        int width = drawable.getWidth();
        int height = drawable.getHeight();
        Bitmap oldbmp = drawable;
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w*3 / width);
        float scaleHeight = ((float) h*3 / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
           //     matrix, true);

        Drawable drawableTemp=new BitmapDrawable(null, newbmp);


        return drawableTemp;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static String PUPPY_PREFERENCES_NAME="PUPPY_PREFERENCES_NAME";



    public void saveData(String name,int value){

        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putInt(name, value);
        savedata.commit();
    }

    private void saveStringData(String puppy_save_one, String puppySaveOne) {
        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putString(puppy_save_one, puppySaveOne);
        savedata.commit();
    }

}
