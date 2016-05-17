/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.http.volley;

import com.android.volley.Cache;

/**
 * Created by huangxiang on 15/12/15.
 */
public interface ResponseCacheListener {
    void onResponse(Cache.Entry cache);
}
