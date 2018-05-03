package learn.com.example.administrator.puppy;


import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

public class SetAppsActivity extends ListActivity {

    int SET_NUM=1;

    private  List<HashMap<String,Object>> getData() {

        return FirstActivity.list;
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
        Constant.PACKGENAME[SET_NUM-1]=FirstActivity.appsItemInfos.get(position).getPackageName();
        Constant.PACKGELABLE[SET_NUM-1]=FirstActivity.appsItemInfos.get(position).getLabel();
        Constant.PACKGEDRAWABLE[SET_NUM-1]= FirstActivity.appsItemInfos.get(position).getIcon();

        Constant.CHILDBUTTONDRAWABLE[SET_NUM-1]=Constant.PACKGEDRAWABLE[SET_NUM-1].getConstantState().newDrawable();

        switch (SET_NUM-1){
            case 0:
                Constant.PUPPY_SAVE_MODE_ONE=1;
                Constant.PUPPY_SAVE_ONE=Constant.PACKGELABLE[SET_NUM-1]+"-"+Constant.PACKGENAME[SET_NUM-1];
                saveData("PUPPY_SAVE_MODE_ONE",1);
                saveStringData("PUPPY_SAVE_ONE", Constant.PUPPY_SAVE_ONE);
                break;
            case 1:
                Constant.PUPPY_SAVE_MODE_TWO=1;
                Constant.PUPPY_SAVE_TWO=Constant.PACKGELABLE[SET_NUM-1]+"-"+Constant.PACKGENAME[SET_NUM-1];;
                saveData("PUPPY_SAVE_MODE_TWO", 1);
                saveStringData("PUPPY_SAVE_TWO", Constant.PUPPY_SAVE_TWO);
                break;
            case 2:
                Constant.PUPPY_SAVE_MODE_THREE=1;
                Constant.PUPPY_SAVE_THREE=Constant.PACKGELABLE[SET_NUM-1]+"-"+Constant.PACKGENAME[SET_NUM-1];;
                saveData("PUPPY_SAVE_MODE_THREE",1);
                saveStringData("PUPPY_SAVE_THREE", Constant.PUPPY_SAVE_THREE);
                break;
            case 3:
                Constant.PUPPY_SAVE_MODE_FORE=1;
                Constant.PUPPY_SAVE_FORE=Constant.PACKGELABLE[SET_NUM-1]+"-"+Constant.PACKGENAME[SET_NUM-1];;
                saveData("PUPPY_SAVE_MODE_FORE",1);
                saveStringData("PUPPY_SAVE_FORE", Constant.PUPPY_SAVE_FORE);
                break;
        }



        Intent intent = new Intent("android.intent.action.MY_BROADCAST");
        intent.putExtra("NUM",SET_NUM-1);
        intent.putExtra("CMD", 2);
        sendBroadcast(intent);
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void saveStringData(String puppy_save_one, String puppySaveOne) {
        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putString(puppy_save_one, puppySaveOne);
        savedata.commit();
    }

    public static String PUPPY_PREFERENCES_NAME="PUPPY_PREFERENCES_NAME";



    public void saveData(String name,int value){

        SharedPreferences.Editor savedata = getSharedPreferences(PUPPY_PREFERENCES_NAME, 0)
                .edit();
        savedata.putInt(name, value);
        savedata.commit();
    }
}
