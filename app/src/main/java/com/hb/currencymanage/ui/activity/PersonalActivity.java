package com.hb.currencymanage.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.BitmapUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalActivity extends BaseActivity {

    private RxPermissions rxPermissions;

    public static int REQUEST_LIBRARY_PHOTO = 1;

    public static int REQUEST_TAKE_PHOTO = 2;

    private Bitmap headImg = null;

    private String headImgOriFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);

        rxPermissions=new RxPermissions(PersonalActivity.this);

        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){

                        }else{
                            finish();
                        }
                    }
                });


    }


    @OnClick(R.id.bank_layout)
    void bank_layout()
    {
        changeActivity(BankInformationActivity.class);

    }


    @OnClick(R.id.recommend_layout)
    void recommend_layout()
    {

        changeActivity(RecommendActivity.class);

    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }


    @OnClick(R.id.choose_layout)
    void choose_layout()
    {
        showDialog();
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
                            PersonalActivity.this.startActivityForResult(new Intent(
                                            "android.intent.action.PICK",
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                                    PersonalActivity.this.REQUEST_LIBRARY_PHOTO);
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
                            PersonalActivity.this.startActivityForResult(intent,
                                    PersonalActivity.this.REQUEST_TAKE_PHOTO);
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
                        BitmapUtil.compress2(PersonalActivity.this,img_path);
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
                .build();

        RetrofitUtils
                .getInstance(context)
                .api
                .uploadImage(14+"",requestBody)
                .compose(RxSchedulers.<ResultData<UserBean>>compose())
                .subscribe(new BaseObserver<UserBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<UserBean> resultData) {



                    }
                });
    }






}
