package learn.com.example.administrator.puppy;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 06/09/2015.
 */
public class AppsItemInfo {
    private Drawable icon; // 存放图片
    private String label; // 存放应用程序名
    private String packageName; // 存放应用程序包名

    public Drawable getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }



}
