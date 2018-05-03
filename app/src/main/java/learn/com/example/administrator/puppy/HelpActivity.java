package learn.com.example.administrator.puppy;

import android.app.ExpandableListActivity;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class HelpActivity extends ExpandableListActivity {
    private String[] Grade=new String[]{
            "如何卸载Puppy？",
            "小米等如何显示悬浮按钮？",
            "华为等如何显示悬浮按钮？",
            "oppo等不显示悬浮按钮？",
            "Android 5.0以上无法显示怎么办",
            "Android 5.0以上悬浮按钮显示了一段时间自动隐藏或不显示怎么办？","其他问题"};

    String[] Lesson=new String[]{"因系统限制，开启锁屏功能之后无法卸载应用，如需卸载，请在虚拟面板设置下关闭设备管理器权限",
            "MIUI v6之前版本：手机设置-应用-Puppy-开启显示悬浮窗  \nMIUI v6之后版本：安全中心-授权管理-应用权限管理-Pyppy-开启显示悬浮窗" ,
            "系统设置-悬浮窗管理-勾选Puppy",
            "在安全中心可开启悬浮按钮",
            " 部分Android 5.0.1如果默认选择未选择覆盖应用程序界面则会导致无法显示，（三星为例）请打开设置-安全-应用程序许可，然后找到Puppy,开启覆盖应用程序权限。" ,
            " 部分Android 5.0.1以上机型会对长期未使用的服务进行关闭，（三星为例）请打开设置-安全-应用程序许可，然后找到Puppy,开启自动运行权限。" ,
            "如遇到其他问题不能解决，请发送问题详情到freesky12138@google.com，我们会在一个工作日内回复"
    };


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        ExpandableListAdapter ExpandAdapter = new ExpandableListAdapter() {

            public TextView getTextParentView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams
                        (ViewGroup.LayoutParams.FILL_PARENT, Constant.SCREE_MAX_Y/10);
                TextView textview = new TextView(HelpActivity.this);
                textview.setLayoutParams(lp);
                textview.setGravity(Gravity.CENTER_VERTICAL |Gravity.LEFT);
                textview.setPadding(100, 0, 0, 0);
                textview.setTextSize(20);

                textview.setTextColor(Color.BLACK);
                return textview;
            }
            public TextView getTextChileView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams
                        (ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textview = new TextView(HelpActivity.this);
                textview.setLayoutParams(lp);
                textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textview.setPadding(35, 35, 35, 35);
                textview.setTextSize(17);

                return textview;
            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {
                // TODO Auto-generated method stub

            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGroupExpanded(int groupPosition) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub'

                return true;
            }

            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return true;
            }



            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                TextView GroupView=getTextParentView();
                GroupView.setText(getGroup(groupPosition).toString());
                return GroupView;
            }

            @Override
            public long getGroupId(int groupPosition) {
                // TODO Auto-generated method stub
                return groupPosition;
            }

            @Override
            public int getGroupCount() {
                // TODO Auto-generated method stub
                return Grade.length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                // TODO Auto-generated method stub
                return Grade[groupPosition];
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                // TODO Auto-generated method stub
                return 1;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                TextView ChildTextView = getTextChileView();
                ChildTextView.setText(getChild(groupPosition, childPosition).toString());
                return ChildTextView;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return childPosition;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return Lesson[groupPosition];
            }

            @Override
            public boolean areAllItemsEnabled() {
                // TODO Auto-generated method stub
                return false;
            }
        };

        setListAdapter(ExpandAdapter);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        return false;
    }
    //1  不能卸载
    //2  自动关闭
    //3  小米等无法显示
    //4
}
