package learn.com.example.administrator.puppy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class FloatView extends ViewGroup {
    private float x;// the coordinate of X
    private float y;// the coordinate of Y

    private String vibratorString = Service.VIBRATOR_SERVICE;
    private Vibrator mVibrator01 = (Vibrator) getContext()
            .getApplicationContext().getSystemService(vibratorString); // 初始化振动; //震动


    //获取4个位置
    public void getPos(int nowX, int nowY) {
        /*if(isScreenChange()){
            if(nowX<(Constant.SCREE_MAX_Y/2-Constant.MAIN_BUTTON_Y/2)){
				if(nowY<Constant.SCREE_MAX_X/2-Constant.MAIN_BUTTON_X/2){
					mPosition=Position.LEFT_TOP;
				}else{
					mPosition=Position.LEFT_BOTTOM;
				}
			}else{
				if(nowY<Constant.SCREE_MAX_X/2-Constant.MAIN_BUTTON_X/2){
					mPosition=Position.RIGHT_TOP;
				}else{
					mPosition=Position.RIGHT_BOTTOM;
				}
			}
		}else{*/
        if (nowX < (Constant.SCREE_MAX_X / 2 - Constant.MAIN_BUTTON_X / 2)) {
            if (nowY < Constant.SCREE_MAX_Y / 2 - Constant.MAIN_BUTTON_Y / 2) {
                mPosition = Position.LEFT_TOP;
            } else {
                mPosition = Position.LEFT_BOTTOM;
            }
        } else {
            if (nowY < Constant.SCREE_MAX_Y / 2 - Constant.MAIN_BUTTON_Y / 2) {
                mPosition = Position.RIGHT_TOP;
            } else {
                mPosition = Position.RIGHT_BOTTOM;
            }
        }
        //}

    }

    private WindowManager wm = (WindowManager) getContext()
            .getApplicationContext().getSystemService(
                    getContext().WINDOW_SERVICE);
    private WindowManager.LayoutParams wmlp = MainService.wmlp;


    //更新坐标
    private void updateViewPosition(float absX, float absY) {
        // To change the position of the float view

        int tX = (int) absX;
        int tY = (int) absY;
        wmlp.x = wmlp.x + tX;
        wmlp.y = wmlp.y + tY;
        //	Log.i("learn.com.example.administrator.puppy",wmlp.x+"  wmlp.x    "+wmlp.y+"   wmlp.y");

        //	Log.i("learn.com.example.administrator.puppy",String.valueOf(tX)+"  absxy  "+String.valueOf((tX)));

        wm.updateViewLayout(this, wmlp);
    }

	/*//判断横竖屏
	public boolean isScreenChange() {

		Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
		int ori = mConfiguration.orientation ; //获取屏幕方向

		if(ori == mConfiguration.ORIENTATION_LANDSCAPE){
		//横屏
			return true;
		}else if(ori == mConfiguration.ORIENTATION_PORTRAIT){
		//竖屏
			return false;
		}
		return false;
	}*/

    //根据横竖屏让puppy 呆在竖屏边上
    public void stayY() {
/*		if(isScreenChange()){//横屏

			if(wmlp.y>Constant.SCREE_MAX_X/2-Constant.MAIN_BUTTON_X/2){
				wmlp.y=Constant.SCREE_MAX_X-Constant.MAIN_BUTTON_X;
			}else{
				wmlp.y=0;
			}

		}else{//竖屏
			*/
/*

		int beforeX=wmlp.x;
		int beforeY=wmlp.y;
*/

        if (wmlp.x > Constant.SCREE_MAX_X / 2 - Constant.MAIN_BUTTON_X / 2) {
            wmlp.x = Constant.SCREE_MAX_X - Constant.MAIN_BUTTON_X;
        } else {
            wmlp.x = 0;
        }
/*
		TranslateAnimation tranAnim = new TranslateAnimation(beforeX,  wmlp.x,beforeY, wmlp.y);

		tranAnim.setFillAfter(true);
		tranAnim.setDuration(500);

		AnimationSet animset = new AnimationSet(true);
		animset.addAnimation(tranAnim);
		animset.setRepeatCount(20);
		this.setAnimation(animset);*/
        //	}

        if (Constant.IS_FLOATVIEW_SHOW)
            wm.updateViewLayout(this, wmlp);


    }


    private Position mPosition = Position.LEFT_TOP;
    private int mRadius = Constant.RADIUS;
    /**
     * 菜单的状态
     */
    private Status mCurrentStatus = Status.CLOSE;
    /**
     * 菜单的主按钮
     */
    private View mCButton;

    private OnMenuItemClickListener mMenuItemClickListener;

    public void setButtonSize(float size) {

        if (mCurrentStatus == Status.OPEN) {
            changeScree();
            changeStatus();
        }
		/*Matrix matrix = new Matrix();
		matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例


		Bitmap resizeBmp = Bitmap.createBitmap(bitmapChild, 0, 0, bitmapChild.getWidth(), bitmapChild.getHeight(), matrix, true);

		Log.i("learn.com.example.administrator.puppy",bitmapChild.getHeight()+" ttt  "+resizeBmp.getHeight());
		BitmapDrawable iconDrawable = new BitmapDrawable(resizeBmp);*/
        //	Bitmap bitmapChild= BitmapFactory.decodeResource(getResources(),R.drawable.img_puppy_main);
        Constant.MAIN_BUTTON_X = (int) (Constant.SCREE_MAX_X / 8 * (25 + size) / 100);
        Constant.MAIN_BUTTON_Y = (int) (Constant.SCREE_MAX_X / 8 * (25 + size) / 100);
        wmlp.width = Constant.MAIN_BUTTON_X;
        wmlp.height = Constant.MAIN_BUTTON_Y;


        wm.updateViewLayout(this, wmlp);
        mCButton.layout(0, 0, Constant.MAIN_BUTTON_X, Constant.MAIN_BUTTON_Y);
        stayY();
    }

    public void setRidaus(int ridaus) {
        if (mCurrentStatus == Status.OPEN) {
            changeScree();
            changeStatus();
        }

        this.mRadius = ridaus;
        if (Constant.MAIN_BUTTON_X < Constant.CHILD_BUTTON_X) {//用最大的圆点半径
            Constant.WMLPX = Constant.RADIUS + Constant.CHILD_BUTTON_X / 2 + Constant.CHILD_BUTTON_X / 2;
            Constant.WMLPY = Constant.RADIUS + Constant.CHILD_BUTTON_Y / 2 + Constant.CHILD_BUTTON_Y / 2;
        } else {

            Constant.WMLPX = Constant.RADIUS + Constant.CHILD_BUTTON_X / 2 + Constant.MAIN_BUTTON_X / 2;
            Constant.WMLPY = Constant.RADIUS + Constant.CHILD_BUTTON_Y / 2 + Constant.MAIN_BUTTON_Y / 2;
        }
    }

	/*public void closeChild() {
		if(mCurrentStatus==Status.OPEN){
			changeScree();
			rotateCButton(mCButton, 0f, 360f, mRadius);
			toggleMenu(mRadius);
		}
	}*/

    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单的位置枚举类
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 点击子菜单项的回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Context FloatContext;

    public FloatView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FloatContext = context;
	/*	mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				100, getResources().getDisplayMetrics());
*/
        // 获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ArcMenu, defStyle, 0);

        getPos(wmlp.x, wmlp.y);

		/*mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
*/
        //Log.e("TAG", "position = " + mPosition + " , radius =  " + mRadius);

        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            // 测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isFirstCButton = true;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            if (isFirst) {
                layoutCButton();
                isFirstCButton = false;
            }


            int count = getChildCount();

            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);

                child.setVisibility(View.GONE);

                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                        * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                        * i));

                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                // 如果菜单位置在底部 左下，右下
                if (mPosition == Position.LEFT_BOTTOM
                        || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                // 右上，右下
                if (mPosition == Position.RIGHT_TOP
                        || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl, ct, cl + cWidth, ct + cHeight);
                toggleMenu(Constant.CHILDBUTTON_SET_SPEED);
                changeStatus();
            }

        }

    }


    long firstDown = 0;
    long up = 0;
    long secondDown = 0;
    private float firstTouchX;
    private float firstTouchY;

    public static boolean isLeave = false;

    private Handler handler;
    private int isFirstHandle = 0;

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);


        mCButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float tempX = event.getRawX();
                float tempY = event.getRawY();

                switch (event.getAction()) {
                    // begin to move the view
                    case MotionEvent.ACTION_DOWN:
                        isLeave = true;
                        secondDown = System.currentTimeMillis();
                        if (secondDown - firstDown < 400) {
                            switch (Constant.DOUBLE_CLICK) {
                                case 0:
                                    Intent intentF = new Intent(getContext(), FirstActivity.class);
                                    intentF.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    getContext().startActivity(intentF);
                                    break;
                                case 1:
                                    mVibrator01.vibrate(new long[]{1, 14}, -1); // 震动

                                    Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                                    intent.putExtra("CMD", 0);
                                    FloatContext.sendBroadcast(intent);
                                    break;
                                default:
                                    break;
                            }

                        }

                        firstDown = System.currentTimeMillis();

                        firstTouchX = tempX;
                        firstTouchY = tempY;
                        x = tempX;
                        y = tempY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (tempX != firstTouchX || tempY != firstTouchY) {
                            if (mCurrentStatus == Status.CLOSE) {
                                float absX = tempX - x;
                                float absY = tempY - y;
                                x = tempX;
                                y = tempY;
                                updateViewPosition(absX, absY);
                            } else {
                                float absX = tempX - x;
                                float absY = tempY - y;
                                x = tempX;
                                y = tempY;
                                updateViewPosition(absX, absY);
                            }

                        }
                        if (Math.abs(tempX - firstTouchX) > 30 || Math.abs(tempY - firstTouchY) > 30)
                            isLeave = false;

                        up = System.currentTimeMillis();

                        if (isLeave && (up - firstDown) >= 1000
                                && mCurrentStatus == Status.CLOSE) {

                            switch (Constant.LONG_CLICK) {
                                case 0:
                                    Intent intentF = new Intent(getContext(), FirstActivity.class);
                                    intentF.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                                    getContext().startActivity(intentF);
                                    break;
                                case 1:
                                    mVibrator01.vibrate(new long[]{1, 14}, -1); // 震动

                                    Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                                    intent.putExtra("CMD", 0);
                                    FloatContext.sendBroadcast(intent);
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //	Log.i("Puppy","OnTouckUp"+String.valueOf(tempX)+"  "+String.valueOf(tempY));

                        mCButton.setFocusable(false);
                        mCButton.setClickable(false);
                        if (Math.abs(tempX - firstTouchX) < 10 && Math.abs(tempY - firstTouchY) < 10) {

                            if (Constant.IS_CBUTTON_ZD) {
                                mVibrator01.vibrate(new long[]{1, 14}, -1); // 震动
                            }


                            if (mCurrentStatus == Status.OPEN) {//关
                                toggleMenu(Constant.CHILDBUTTON_SET_SPEED);

                                changeStatus();
                                isFirstHandle = 0;
                                handler = new Handler() {
                                    @Override
                                    public void handleMessage(Message msg) {
                                        closeScreen();
                                    }
                                };

                            } else {
                                openScreen();
                                rotateCButton(v, 0f, 360f, mRadius);
                                toggleMenu(Constant.CHILDBUTTON_SET_SPEED);
                                changeStatus();
                            }


                        } else {
                            float absX = tempX - x;
                            float absY = tempY - y;
                            x = tempX;
                            y = tempY;
                            stayY();
                        }
                        break;
                }
                return true;
            }
        });

        int width = mCButton.getMeasuredWidth();
        getPos(wmlp.x + (int) mCButton.getX(), wmlp.y + (int) mCButton.getY());
        //Log.i("Puppy", "execting mCButton");

        if (isFirst) {
            mCButton.layout((int) x, (int) y, (int) x + width, (int) y + width);
            isFirst = false;
        }

    }

    boolean isFirst = true;


    /**
     * 切换菜单
     */
    public void toggleMenu(int duration) {
        // 为menuItem添加平移动画和旋转动画
        int count = getChildCount();

        for (int i = 0; i < count - 1; i++) {

            int temp = i + 1;
            final View childView = getChildAt(temp);

            childView.setVisibility(View.VISIBLE);

            // end 0 , 0
            // start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int cWidth = Constant.CHILD_BUTTON_X;
            int cHeight = Constant.CHILD_BUTTON_X;

            // 如果菜单位置在底部 左下，右下
            if (mPosition == Position.LEFT_BOTTOM
                    || mPosition == Position.RIGHT_BOTTOM) {
                ct = getMeasuredHeight() - cHeight - ct;
            }
            // 右上，右下
            if (mPosition == Position.RIGHT_TOP
                    || mPosition == Position.RIGHT_BOTTOM) {
                cl = getMeasuredWidth() - cWidth - cl;
            }


            childView.layout(cl, ct, cl + cWidth, ct + cHeight);

            int xflag = 1;
            int yflag = 1;

            if (mPosition == Position.LEFT_BOTTOM) {
                ct = mRadius - ct;
            }
            if (mPosition == Position.RIGHT_TOP) {
                cl = mRadius - cl;
            }

            if (mPosition == Position.RIGHT_BOTTOM) {
                ct = mRadius - ct;
                cl = mRadius - cl;
                //		Log.e("duration",duration+"");
                //		Log.e("r",mRadius+"---"+cHeight);
            }

            if (mPosition == Position.LEFT_TOP
                    || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }

            if (mPosition == Position.LEFT_TOP
                    || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }
            //	Log.i("learn.com.example.administrator.puppy",String.valueOf(xflag)+"  flag  "+String.valueOf(yflag)+mPosition);
            AnimationSet animset = new AnimationSet(true);
            Animation tranAnim = null;
            // to open
            if (mCurrentStatus == Status.CLOSE) {
                tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);

            } else {
                tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i * 100) / count);

            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCButton.setClickable(true);
                    if (mCurrentStatus == Status.CLOSE) {
                        isFirstHandle++;
                        if (handler != null && isFirstHandle >= 4) {
                            handler.sendEmptyMessage(0);
                            isFirstHandle = 0;
                        }

                        childView.setVisibility(View.GONE);
                    }
                }
            });
            // 旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

            animset.addAnimation(rotateAnim);
            animset.addAnimation(tranAnim);
            childView.startAnimation(animset);

            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener != null)
                        mMenuItemClickListener.onClick(childView, pos);

                    if (!Constant.PACKGENAME[pos - 1].equals("0")) {
                        menuItemAnim(pos - 1);
                        changeScree();
                        changeStatus();

                    }

                }
            });
        }
        // 切换菜单状态

    }

    private float heartX;
    private float heartY;

    public void closeScreen() {
        //
        heartX = mCButton.getX();
        heartY = mCButton.getY();
        heartY += wmlp.y;
        heartX += wmlp.x;

        getPos((int) heartX, (int) heartY);


        wm.removeViewImmediate(this);

/*
			if(mPosition==Position.LEFT_TOP){
				wmlp.y=(int)heartY;
				wmlp.x=(int)heartX;
				wmlp.gravity=Gravity.LEFT|Gravity.TOP;
			}else if(mPosition==Position.LEFT_BOTTOM){
				wmlp.y=Constant.SCREE_MAX_Y-(int)heartY;
				wmlp.x=(int)heartX;
				wmlp.gravity=Gravity.LEFT|Gravity.BOTTOM;
			}else if(mPosition==Position.RIGHT_TOP){
				wmlp.y=(int)heartY;
				wmlp.x=Constant.SCREE_MAX_X-(int)heartX;

				wmlp.gravity=Gravity.RIGHT|Gravity.TOP;
			}else if(mPosition==Position.RIGHT_BOTTOM){
				wmlp.y=Constant.SCREE_MAX_Y-(int)heartY;
				wmlp.x=Constant.SCREE_MAX_X-(int)heartX;
				wmlp.gravity=Gravity.RIGHT|Gravity.BOTTOM;
			}*/


        wmlp.width = Constant.MAIN_BUTTON_X;
        wmlp.height = Constant.MAIN_BUTTON_Y;

        wmlp.y = (int) heartY;
        wmlp.x = (int) heartX;

        //	wmlp.gravity=Gravity.LEFT|Gravity.TOP;

        wm.addView(FloatView.this, wmlp);

        stayY();
        mCButton.layout(0, 0, Constant.MAIN_BUTTON_X, Constant.MAIN_BUTTON_Y);


    }

    public void openScreen() {

        //
        heartX = mCButton.getX();
        heartY = mCButton.getY();
        heartY += wmlp.y;
        heartX += wmlp.x;

        getPos((int) heartX, (int) heartY);

        //	Log.i("learn.com.example.administrator.puppy",Constant.WMLPX+" "+Constant.WMLPY+"  "+Constant.RADIUS);
        wmlp.width = Constant.WMLPX;
        wmlp.height = Constant.WMLPY;
        //	Log.i("learn.com.example.administrator.puppy","CLOSE "+wmlp.width);
        reSetCButton();
        wm.updateViewLayout(this, wmlp);


    }


    private void changeScree() {

        //
        heartX = mCButton.getX();
        heartY = mCButton.getY();
        heartY += wmlp.y;
        heartX += wmlp.x;

        getPos((int) heartX, (int) heartY);

        if (mCurrentStatus == Status.OPEN) {//关

            //Log.i("learn.com.example.administrator.puppy","OPEN "+wmlp.width);


            wm.removeViewImmediate(this);

/*
			if(mPosition==Position.LEFT_TOP){
				wmlp.y=(int)heartY;
				wmlp.x=(int)heartX;
				wmlp.gravity=Gravity.LEFT|Gravity.TOP;
			}else if(mPosition==Position.LEFT_BOTTOM){
				wmlp.y=Constant.SCREE_MAX_Y-(int)heartY;
				wmlp.x=(int)heartX;
				wmlp.gravity=Gravity.LEFT|Gravity.BOTTOM;
			}else if(mPosition==Position.RIGHT_TOP){
				wmlp.y=(int)heartY;
				wmlp.x=Constant.SCREE_MAX_X-(int)heartX;

				wmlp.gravity=Gravity.RIGHT|Gravity.TOP;
			}else if(mPosition==Position.RIGHT_BOTTOM){
				wmlp.y=Constant.SCREE_MAX_Y-(int)heartY;
				wmlp.x=Constant.SCREE_MAX_X-(int)heartX;
				wmlp.gravity=Gravity.RIGHT|Gravity.BOTTOM;
			}*/


            android.os.Handler handler = new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    wmlp.width = Constant.MAIN_BUTTON_X;
                    wmlp.height = Constant.MAIN_BUTTON_Y;

                    wmlp.y = (int) heartY;
                    wmlp.x = (int) heartX;

                    //	wmlp.gravity=Gravity.LEFT|Gravity.TOP;

                    wm.addView(FloatView.this, wmlp);

                    stayY();
                    mCButton.layout(0, 0, Constant.MAIN_BUTTON_X, Constant.MAIN_BUTTON_Y);
                    super.handleMessage(msg);
                }
            };


            handler.sendEmptyMessage(0);


        } else {//开
            //	Log.i("learn.com.example.administrator.puppy",Constant.WMLPX+" "+Constant.WMLPY+"  "+Constant.RADIUS);
            wmlp.width = Constant.WMLPX;
            wmlp.height = Constant.WMLPY;
            //	Log.i("learn.com.example.administrator.puppy","CLOSE "+wmlp.width);
            reSetCButton();
            wm.updateViewLayout(this, wmlp);
        }
    }

    public void serviceSetChildView(int num) {
        //Log.i("learn.com.example.administrator.puppy","do serviceSetChildView");

        switch (Constant.PACKGENAME[num]) {
            case "0":
                this.getChildAt(num + 1).setBackground(Constant.IMGDRAWABLE[0]);
                break;
            case "1":
                this.getChildAt(num + 1).setBackground(Constant.IMGDRAWABLE[1]);
                break;
            case "2":
                this.getChildAt(num + 1).setBackground(Constant.IMGDRAWABLE[2]);
                break;
            case "3":
                this.getChildAt(num + 1).setBackground(Constant.IMGDRAWABLE[3]);
                break;
            default:
				/*for(int i=0;i<SetAppsActivity.appsItemInfos.size();i++){
					if(SetAppsActivity.appsItemInfos.get(i).getPackageName().equals(Constant.PACKGENAME[num])){
						this.getChildAt(num+1).setBackground(SetAppsActivity.appsItemInfos.get(i).getIcon());
						break;
					}
				}*/
                this.getChildAt(num + 1).setBackground(Constant.PACKGEDRAWABLE[num]);
                break;
        }

        for (int i = 1; i < 5; i++) {
            this.getChildAt(i).getBackground().setAlpha(Constant.CHILEBUTTON_TM_VALUE);
        }

    }


    public void setChildButtonAlpha(int i, int value) {

        if (mCurrentStatus == Status.OPEN) {
            changeScree();
            changeStatus();
        }

        this.getChildAt(i).getBackground().setAlpha(value);

    }

    public void setCButtonAlpha(int i, int value) {
        this.getChildAt(i).getBackground().setAlpha(value);

    }

    /**
     * 添加menuItem的点击动画
     */
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {

            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(mRadius));//对动画时间影响不大
            } else {
                childView.startAnimation((scaleSmallAnim(mRadius)));
            }
            childView.setClickable(false);
            childView.setFocusable(false);

        }

    }


    private Animation scaleSmallAnim(int duration) {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;

    }

    /**
     * 为当前点击的Item设置变大和透明度降低的动画
     *
     * @param duration
     * @return
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;

    }

    /**
     * 切换菜单状态
     */
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN
                : Status.CLOSE);
    }

    public void reSetCButton() {
        //Log.i("learn.com.example.administrator.puppy",mPosition.toString()+"  v  "+String.valueOf(wmlp.width)+getMeasuredHeight());
        if (mPosition == Position.LEFT_TOP) {
            mCButton.layout(0, 0, Constant.MAIN_BUTTON_X, Constant.MAIN_BUTTON_Y);
        } else if (mPosition == Position.RIGHT_TOP) {
/*
			if(isScreenChange())
				wmlp.x=wmlp.x-wmlp.height+Constant.MAIN_BUTTON_X;
*/
            wmlp.x = wmlp.x - wmlp.height + Constant.MAIN_BUTTON_X;

            mCButton.layout(wmlp.width - Constant.MAIN_BUTTON_X, 0, wmlp.width, Constant.MAIN_BUTTON_Y);
        } else if (mPosition == Position.LEFT_BOTTOM) {

            wmlp.y = wmlp.y - wmlp.height + Constant.MAIN_BUTTON_Y;

            mCButton.layout(0, wmlp.height - Constant.MAIN_BUTTON_Y, Constant.MAIN_BUTTON_X, wmlp.height);
        } else {
		/*	if(isScreenChange()){
				wmlp.x=wmlp.x-wmlp.height+Constant.MAIN_BUTTON_X;
			}else{*/
            wmlp.y = wmlp.y - wmlp.height + Constant.MAIN_BUTTON_Y;
            wmlp.x = wmlp.x - wmlp.height + Constant.MAIN_BUTTON_X;
            //}
            mCButton.layout(wmlp.width - Constant.MAIN_BUTTON_X, wmlp.height - Constant.MAIN_BUTTON_Y, wmlp.width, wmlp.height);
        }


    }

    public boolean isOpen() {
        return mCurrentStatus == Status.OPEN;
    }


    private void rotateCButton(View v, float start, float end, int duration) {

        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //	Log.i("learn.com.example.administrator.puppy",event.getX()+"  "+event.getY());
        return super.onTouchEvent(event);
    }
}
