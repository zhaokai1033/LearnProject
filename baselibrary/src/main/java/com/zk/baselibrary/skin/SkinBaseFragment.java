package com.zk.baselibrary.skin;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.skin.attr.base.DynamicAttr;
import com.zk.baselibrary.skin.face.IDynamicNewView;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:35
 * Desc:
 */
public abstract class SkinBaseFragment extends BaseFra implements IDynamicNewView {

    private IDynamicNewView mIDynamicNewView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIDynamicNewView = (IDynamicNewView) context;
        } catch (ClassCastException e) {
            mIDynamicNewView = null;
        }
    }

    @Override
    public final void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if (mIDynamicNewView == null) {
            throw new RuntimeException("IDynamicNewView should be implements !");
        } else {
            mIDynamicNewView.dynamicAddView(view, pDAttrs);
        }
    }

    @Override
    public final void dynamicAddView(View view, String attrName, int attrValueResId) {
        mIDynamicNewView.dynamicAddView(view, attrName, attrValueResId);
    }

    @Override
    public final void dynamicAddFontView(TextView textView) {
        mIDynamicNewView.dynamicAddFontView(textView);
    }

    public final LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater result = getActivity().getLayoutInflater();
        return result;
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
    }

    private void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllView(viewGroup.getChildAt(i));
            }
            removeViewInSkinInflaterFactory(v);
        } else {
            removeViewInSkinInflaterFactory(v);
        }
    }

    private void removeViewInSkinInflaterFactory(View v) {
        if (getContext() instanceof SkinBaseActivity) {
            SkinBaseActivity skinBaseActivity = (SkinBaseActivity) getContext();
            //移除SkinInflaterFactory中的v
            skinBaseActivity.removeSkinView(v);
        }
    }
}
