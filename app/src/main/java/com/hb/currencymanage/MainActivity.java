package com.hb.currencymanage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.TabEntity;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.ui.activity.BaseActivity;
import com.hb.currencymanage.ui.activity.PersonalActivity;
import com.hb.currencymanage.ui.fragment.DealFragment;
import com.hb.currencymanage.ui.fragment.MainNewFragment;
import com.hb.currencymanage.ui.fragment.PersonFragment;
import com.hb.currencymanage.ui.fragment.QuotesFragment;
import com.hb.currencymanage.util.BitmapUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp)
    public ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;

    @BindView(R.id.status_view)
    View mStatusView;
    //测试提交
    private String[] mTitles = {"首页", "行情", "交易", "个人"};

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnSelectIds = {R.mipmap.icon_sy,
            R.mipmap.icon_hq_default, R.mipmap.icon_jy_default,
            R.mipmap.icon_gr_default};

    private int[] mIconSelectIds = {R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected, R.mipmap.icon_jy_selected,
            R.mipmap.icon_gr_selected};

    private DealFragment mDealFragment;

    private QuotesFragment mQuotesFragment;

    private PersonFragment mPersonFragment;
    public int selectPosition;

    private CircleImageView headCircleImageView;

    private RxPermissions rxPermissions;

    public static int REQUEST_LIBRARY_PHOTO = 1;

    public static int REQUEST_TAKE_PHOTO = 2;

    private Bitmap headImg = null;

    private String headImgOriFileName;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rxPermissions=new RxPermissions(MainActivity.this);

        userBean=new AccountDB(context).getAccount();
        initViewPager();
        mPersonFragment.callBack=new PersonFragment.MyCallBack() {
            @Override
            public void uploadImage(CircleImageView circleImageView) {
                headCircleImageView=circleImageView;
                showDialog();
            }
        };

        Beta.checkUpgrade(false,false);

    }

    private void showDialog(){
        NormalSelectionDialog dialog = new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(true)   //设置是否显示标题
                .setTitleHeight(65)   //设置标题高度
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
                .setItemHeight(40)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(14)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                        dialog.dismiss();
                        if(position==0){
                            capture();
                        }else if (position==1){
                            MainActivity.this.startActivityForResult(new Intent(
                                            "android.intent.action.PICK",
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                                    MainActivity.this.REQUEST_LIBRARY_PHOTO);
                        }


                    }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build();

        ArrayList<String> s = new ArrayList<>();
        s.add("拍照");
        s.add("图库");
        dialog.setDatas(s);
        dialog.show();

    }

    private void capture(){
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            MainActivity.this.startActivityForResult(intent,
                                    MainActivity.this.REQUEST_TAKE_PHOTO);
                        }
                    }
                });

    }


    public static String savePicToSdcard(Bitmap bitmap, String fileName) {
        if (bitmap == null||bitmap.isRecycled()) {
            return "";
        } else {
            // File destFile = new
            // File(Environment.getExternalStorageDirectory()+"/dqysh",fileName);
            File appDir=new File(Environment.getExternalStorageDirectory(),"lovefamily");
            if(!appDir.exists()){
                appDir.mkdir();
            }
            File destFile = new File(appDir,fileName);
            fileName=destFile.getAbsolutePath();
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);// 100代表不压缩
                os.flush();
                os.close();
            } catch (IOException e) {

                fileName = "";
            }
        }
        return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri mImageCaptureUri;
            if (requestCode == 2 || requestCode == 1)// 原图
            {
                if (headImg != null)
                    headImg.recycle();
                mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        headImg = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(), mImageCaptureUri);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        //Log.e(TAG, e.getMessage());
                    }
                } else {
                    Bundle extras = data.getExtras();
                    headImg = extras.getParcelable("data");
                    mImageCaptureUri = Uri.parse(MediaStore.Images.Media
                            .insertImage(getContentResolver(), headImg, null,
                                    null));
                }
                Intent intent = new Intent();
                intent.setAction("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");// mUri是已经选择的图片Uri
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);// 裁剪框比例
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 120);// 输出图片大小
                intent.putExtra("outputY", 120);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 200);
            } else if (requestCode == 200)// 剪裁完成的图
            {
                //headImgOriFileName = Environment.getExternalStorageDirectory()+ File.separator+"dqysh"+random.nextInt(10000) + ".jpg";



                headImgOriFileName=System.currentTimeMillis() + ".jpg";
                mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        String[] proj = { MediaStore.Images.Media.DATA };
                        Cursor actualimagecursor = managedQuery(mImageCaptureUri,proj,null,null,null);
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(actual_image_column_index);
//			    	File file = new File(img_path);
                        BitmapUtil.compress2(MainActivity.this,img_path);
                        headImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri);
//    			headImg = MediaStore.Images.Media.getBitmap(
//    				this.getContentResolver(), mImageCaptureUri);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        //Log.e(TAG, e.getMessage());
                    }
                } else {
                    headImg = data.getExtras().getParcelable("data");
                }
                headImgOriFileName=savePicToSdcard(headImg, headImgOriFileName);
                //headCircleImageView.setImageBitmap(headImg);
                //headCircleImageView.setImageBitmap(BitmapFactory.decodeFile(headImgOriFileName));
                mPersonFragment.setHeadImageBitmap(headImgOriFileName);
                uploadHeadImage();


            }

        }
    }

    private void uploadHeadImage(){
        File file = new File(headImgOriFileName);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                //.addFormDataPart("sessiontoken", memberBean.session_token)
                .addFormDataPart("headPortraitImageFile  ", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("userId ",userBean.getId())
                .build();
        RetrofitUtils
                .getInstance(context)
                .api
                .uploadImage(userBean.getId(),requestBody)
                .compose(RxSchedulers.<ResultData<UserBean>>compose())
                .subscribe(new BaseObserver<UserBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<UserBean> resultData) {


                        if (resultData.result == 200) {
                            UserBean userBean=new AccountDB(context).getAccount();
                            userBean.headPortrait=resultData.user.headPortrait;
                            new AccountDB(context).clear();
                            new AccountDB(context).saveAccount(userBean);
                        }

                    }
                });
    }


    public void swTab(int page) {
        mViewPager.setCurrentItem(2);
        mDealFragment.setPage(page);
    }

    private void initViewPager() {
        mDealFragment = DealFragment.getInstance();
        mQuotesFragment = QuotesFragment.getInstance();
        mPersonFragment = PersonFragment.getInstance();
        mFragments.add(MainNewFragment.getInstance());
        mFragments.add(mQuotesFragment);
        mFragments.add(mDealFragment);
        mFragments.add(mPersonFragment);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                mViewPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    // mTabLayout.showMsg(0, new Random().nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition = position;
                mTabLayout.setCurrentTab(position);
                if (position == 0 || position == 3) {
                    mStatusView.setVisibility(View.GONE);
                } else {
                    mStatusView.setVisibility(View.VISIBLE);
                }
                mStatusView.setBackgroundColor(position == 0
                        ? context.getResources()
                        .getColor(R.color.device_bar_color_50_alpha)
                        : position == 1
                        ? context.getResources()
                        .getColor(R.color.color_head)
                        : context.getResources()
                        .getColor(R.color.color_head));
                if (position == 0) {
                    mStatusView.setVisibility(View.GONE);
                }
                if (position == 1) {
                    if (null != mQuotesFragment) {
                        mQuotesFragment.initNetWork();
                    }
                }
                if (position == mFragments.size() - 1) {
//                    if (null != mPersonFragment) {
//                        mPersonFragment.initWorkNet();
//                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mStatusView.setVisibility(View.GONE);
        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


}
