package com.hx.template.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hx.template.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by huangx on 2016/5/13.
 */
public class BaseStepActivity<T extends BaseStepFragment> extends BaseActivity {

    public final static String EXTRA_INIT_CLASS = "_extra_init_class";
    public final static String EXTRA_INIT_ARGUMENTS = "_extra_init_arguments";

    protected Class<? extends BaseStepFragment> fragmentClass;
    protected BaseStepFragment initFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra(EXTRA_INIT_CLASS)) {
            try {
                Class<?> initClass = (Class<?>) getIntent().getSerializableExtra(EXTRA_INIT_CLASS);
                fragmentClass = (Class<? extends BaseStepFragment>) initClass;
            } catch (ClassCastException e) {
            }
        }
        if (fragmentClass == null) {
            Type genType = getClass().getGenericSuperclass();
            try {
                fragmentClass = (Class<? extends BaseStepFragment>) ((ParameterizedType) genType).getActualTypeArguments()[0];
            } catch (ClassCastException e) {
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        if (preInitStepActivity()) {
            initStepActivity(getIntent().getBundleExtra(EXTRA_INIT_ARGUMENTS));
        }
    }

    protected int getContentLayout() {
        return R.layout.activity_base_step_default;
    }

    protected boolean preInitStepActivity() {
        //TODO 这里判断要不要做默认初始化的处理
        return true;
    }

    protected final void initStepActivity(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.getBackStackEntryCount() > 0) {
            if (fm.getFragments() != null && fm.getFragments().size() > 0) {
                boolean firstSet = false;
                for (int i = fm.getFragments().size() - 1; i >= 0; i--) {
                    Fragment fragment = fm.getFragments().get(i);
                    if (fragment == null) {
                        continue;
                    }
                    if (!firstSet) {
                        firstSet = true;
                        ft.show(fragment);
                    } else {
                        ft.hide(fragment);
                    }
                }
            }
            ft.commitAllowingStateLoss();
        } else {
            try {
                BaseStepFragment fragment = fragmentClass.newInstance();
                fragment.setArguments(args);
                fragment.setFirstStep(true);
                ft.replace(R.id.content_area, fragment);
                ft.commitAllowingStateLoss();
                initFragment = fragment;
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }
    }
}
