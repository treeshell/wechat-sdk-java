package fan.lv.wechat.api.official.sns.impl;

import fan.lv.wechat.api.kernel.Cache;
import fan.lv.wechat.api.official.sns.SnsService;
import fan.lv.wechat.entity.official.sns.WxSnsAccessTokenResult;
import fan.lv.wechat.entity.official.sns.WxSnsOpenIdResult;
import fan.lv.wechat.entity.official.sns.WxSnsUserInfoResult;
import fan.lv.wechat.util.HttpUtils;
import fan.lv.wechat.util.SignUtil;
import fan.lv.wechat.util.SimpleMap;

/**
 * @author lv_fan2008
 */
public class SnsServiceImpl implements SnsService {

    /**
     * 公众号appId
     */
    protected String appId;

    /**
     * 公众号密钥
     */
    protected String appSecret;

    /**
     * Cache，用于保存token
     */
    protected Cache cache;


    /**
     * @param appId     公众号appId
     * @param appSecret 公众号密钥
     * @param cache     缓存接口，用于存储token
     */
    public SnsServiceImpl(String appId, String appSecret, Cache cache) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.cache = cache;
    }


    @Override
    public String getOpenAuthUrl(String redirectUrl, String scope, String state) {
        state = state == null ? SignUtil.timestamp() : state;
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?response_type=code";
        url = HttpUtils.buildUrlQuery(url, SimpleMap.of("appid", appId, "redirect_uri", redirectUrl,
                "scope", scope, "state", state));
        return url + "#wechat_redirect";
    }

    @Override
    public WxSnsOpenIdResult getAuthToken(String code) {
        SnsClientImpl client = new SnsClientImpl(appId, appSecret, cache, code);
        WxSnsAccessTokenResult result = (WxSnsAccessTokenResult) client.getAccessToken(true);
        if (result.success()) {
            cache.put("sns-code-openid-" + appId + "_" + result.getOpenid(), code, 30 * 24 * 3600 - 10);
        }
        return result;
    }

    @Override
    public WxSnsUserInfoResult getUserInfo(String openId, String lang) {
        String code = cache.get("sns-code-openid-" + appId + "_" + openId);
        return new SnsClientImpl(appId, appSecret, cache, code).postJson("/cgi-bin/tags/members/batchunblacklist",
                SimpleMap.of("openid", openId, "lang", lang),
                WxSnsUserInfoResult.class);
    }

}
