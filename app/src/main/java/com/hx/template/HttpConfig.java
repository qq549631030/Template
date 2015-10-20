/**
 * Copyright &amp;copy; 2014-2016  All rights reserved.
 * <p/>
 * Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 */
package com.hx.template;

/**
 * @author huangxiang
 */
public class HttpConfig {

    public static String HOME = "http://192.168.1.250";
//    public static String HOME = "http://wo510751575.vicp.net:56619";

    public static String PATH = "/admin/api/";

    public static String BASE_URL = HOME + PATH;
    /**
     * 短信验证码(验)
     */
    public static String MEMBER_SENDAUTHCODE_URL = BASE_URL + "member/sendAuthCode";
    /**
     * 校验验证码(验)
     */
    public static String MEMBER_VALIDAUTHCODE = BASE_URL + "member/validAuthCode";

    /**
     * 登录(验)
     */
    public static String LOGIN_URL = BASE_URL + "member/login";

    /**
     * 注册(验)
     */
    public static String REGISTER_URL = BASE_URL + "member/register";

    /**
     * 修改密码(验)
     */
    public static String MODIFY_PASSWORD_URL = BASE_URL + "member/modifyPwd";

    /**
     * 重置密码(验)
     */
    public static String RESET_PASSWORD_URL = BASE_URL + "member/resetPwd";

    /**
     * 修改手机号码(验)
     */
    public static String MODIFY_MOBILE_URL = BASE_URL + "member/modifyMobile";

    /**
     * 修改头像(验)
     */
    public static String MEMBER_UPLOADHEAD_URL = BASE_URL + "member/uploadHead";

    //设置支付密码(验)
    public static String SET_PAY_PASSWORD_URL = BASE_URL + "payPassword/seting";

    //修改支付密码(验)
    public static String MODIFY_PAY_PASSWORD_URL = BASE_URL + "payPassword/modifyPwd";

    /**
     * 头部轮播(验)
     */
    public static String AD_HEADLIST_URL = BASE_URL + "ad/headList";

    /**
     * 其它广告(验)
     */
    public static String AD_OTHERLIST_URL = BASE_URL + "ad/otherList";

    /**
     * 热门推荐(验)
     */
    public static String AD_HOTLIST_URL = BASE_URL + "ad/hotList";

    /**
     * 新发现(验)
     */
    public static String AD_NEWLIST_URL = BASE_URL + "ad/newList";

    /**
     * 精选商家(验)
     */
    public static String AD_BRANDLIST_URL = BASE_URL + "ad/brandList";
    /**
     * 特色市场(验)
     */
    public static String AD_FEATURELIST_URL = BASE_URL + "ad/featureList";


    /**
     * 获取一级商品分类列表(验)
     */
    public static String PRODUCTCATEGORY_FINDROOTS_URL = BASE_URL + "productCategory/findRoots";

    /**
     * 获取子级商品分类列表(验)
     */
    public static String PRODUCTCATEGORY_FINDCHILDREN_URL = BASE_URL + "productCategory/findChildren";

    /**
     * 商品搜索提示(验)
     */
    public static String SEARCH_SUGGEST_URL = BASE_URL + "product/searchSuggest";

    /**
     * 筛选列表(验)
     */
    public static String GET_SCREENLIST_URL = BASE_URL + "product/screenList";

    /**
     * 商品列表(验)
     */
    public static String GET_PRODUCT_LIST_URL = BASE_URL + "product/list";

    /**
     * 搜索商品列表(验)
     */
    public static String SEARCH_PRODUCT_LIST_URL = BASE_URL + "product/search";

    /**
     * 商品详情(验)
     */
    public static String GET_PRODUCT_DETAIL_URL = BASE_URL + "product/detail";

    /**
     * 根据规格获取商品详情(验)
     */
    public static String GET_PRODUCT_WITH_SPECIFICATION_URL = BASE_URL + "product/getIdBySpecificationValues";

    /**
     * 猜你喜欢(验)
     */
    public static String GUESSYOULIKE_URL = BASE_URL + "product/guessYouLike";

    /**
     * 获取省份列表(验)
     */
    public static String GET_PROVINCE_LIST_URL = BASE_URL + "area/provinceList";

    /**
     * 获取市、区列表(验)
     */
    public static String GET_CHILD_AREA_LIST_URL = BASE_URL + "area/findChilds";

    /**
     * 收货地址列表(验)
     */
    public static String RECEIVER_LIST_URL = BASE_URL + "receiver/list";

    /**
     * 添加收货地址(验)
     */
    public static String RECEIVER_ADD_URL = BASE_URL + "receiver/add";

    /**
     * 修改收货地址(验)
     */
    public static String RECEIVER_UPDATE_URL = BASE_URL + "receiver/update";

    /**
     * 删除收货地址(验)
     */
    public static String RECEIVER_DELETE_URL = BASE_URL + "receiver/delete";

    /**
     * 购物车列表(验)
     */
    public static String GET_CART_LIST_URL = BASE_URL + "cart/list";

    /**
     * 获取购物车项数(验)
     */
    public static String CART_QUANTITY_URL = BASE_URL + "cart/getCartQuantity";
    /**
     * 添加购物车(验)
     */
    public static String CART_ADD_URL = BASE_URL + "cart/add";

    /**
     * 修改购物车(验)
     */
    public static String CART_MODIFYQUANTITY_URL = BASE_URL + "cart/modifyQuantity";

    /**
     * 删除购物车项(验)
     */
    public static String CART_DELETE_URL = BASE_URL + "cart/delete";

    /**
     * 配送方式列表(验)
     */
    public static String SHIPPINGMETHOD_LIST_URL = BASE_URL + "shippingMethod/list";
    /**
     * 支付方式列表(验)
     */
    public static String PAYMENTMETHOD_LIST_URL = BASE_URL + "paymentMethod/list";
    /**
     * 创建订单(验)
     */
    public static String ORDER_CREATE_URL = BASE_URL + "order/create";

    /**
     * 从购物车创建订单(验)
     */
    public static String ORDER_CARTCREATE_URL = BASE_URL + "order/cartCreate";

    /**
     * 生成交易定单号(验)
     */
    public static String CREATE_TRADE_NO_URL = BASE_URL + "order/payment";
    /**
     * 抢购(验)
     */
    public static String GET_PROMOTION_SNAPUP_URL = BASE_URL + "promotion/snapUp";

    /**
     * 获取商家列表(验)
     */
    public static String GET_MERCHANT_LIST_URL = BASE_URL + "merchant/list";

    /**
     * 获取店铺列表(验)
     */
    public static String GET_STORE_LIST_URL = BASE_URL + "store/list";

    /**
     * 添加收藏(验)
     */
    public static String FAVORITE_ADD_URL = BASE_URL + "favorite/add";

    /**
     * 是否收藏指定商品(验)
     */
    public static String FAVORITE_EXIST_URL = BASE_URL + "favorite/isExist";
    /**
     * 收藏列表(验)
     */
    public static String FAVORITE_LIST_URL = BASE_URL + "favorite/list";

    /**
     * 删除收藏(验)
     */
    public static String FAVORITE_DELETE_URL = BASE_URL + "favorite/delete";

    /**
     * 获取评论列表(验)
     */
    public static String GET_COMMENT_LIST_URL = BASE_URL + "review/list";

    /**
     * 评论统计(验)
     */
    public static String GET_COMMENT_COUNT_URL = BASE_URL + "review/count";

    /**
     * 评论(验)
     */
    public static String COMMENT_URL = BASE_URL + "review/save";

    /**
     * 订单列表(验)
     */
    public static String GET_ORDER_LIST_URL = BASE_URL + "order/list";
    /**
     * 取消订单(验)
     */
    public static String ORDER_CANCEL_URL = BASE_URL + "order/cancel";
    /**
     * 交易历史记录(验)
     */
    public static String GET_TRANSACTION_RECORD_URL = BASE_URL + "deposit/list";
    /**
     * 免费券列表(验)
     */
    public static String GET_FREE_LIST_URL = BASE_URL + "deposit/freeList";
    /**
     * 消费券列表(验)
     */
    public static String GET_CONSUME_LIST_URL = BASE_URL + "deposit/consumeList";

    /**
     * 代理商库存(验)
     */
    public static String GET_AGENCY_PRODUCT_LIST_URL = BASE_URL + "agency/product";

    /**
     * 代理商商家(验)
     */
    public static String GET_AGENCY_MERCHANT_LIST_URL = BASE_URL + "agency/myMerchant";

    /**
     * 获取消息列表(验)
     */
    public static String GET_MSG_LIST_URL = BASE_URL + "message/list";
    /*
     *获取未读消息条数(验)
     */
    public static String GET_UNREAD_MSG_COUNT_URL = BASE_URL + "message/unreadCount";

    /**
     * 设置消息为已读(验)
     */
    public static String MSG_SET_READ_URL = BASE_URL + "message/setRead";
    /*
     *意见反馈(验)
     */
    public static String ADD_FEEDBACK_URL = BASE_URL + "feedback/add";

}
