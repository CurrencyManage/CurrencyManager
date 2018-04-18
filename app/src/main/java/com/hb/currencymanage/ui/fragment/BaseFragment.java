package com.hb.currencymanage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class BaseFragment extends Fragment
{


    private Context context;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        context=getActivity();
    }


    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz) {
        Intent intent = new Intent(context, clz);
        startActivity(intent);

    }
}
