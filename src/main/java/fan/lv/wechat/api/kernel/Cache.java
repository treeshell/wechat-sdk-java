package fan.lv.wechat.api.kernel;

/**
 * 缓存接口，用于缓存微信接口凭证等
 *
 * @author lv_fan2008
 */
public interface Cache {

    /**
     * 设置key缓存
     *
     * @param key   关键字
     * @param value 缓存值
     * @param ttl   缓存时间，单位s
     */
    void put(String key, String value, int ttl);

    /**
     * 获取key对应值
     *
     * @param key 关键字
     * @return 缓存值
     */
    String get(String key);

    /**
     * 移除键值
     *
     * @param key 关键字
     */
    void remove(String key);

}
