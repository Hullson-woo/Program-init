package com.cn.example.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Jedis操作redis工具类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@Slf4j
public class JedisUtils {
    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);


    /**
     * INCR: 为key存储的数值加1
     * @param key 键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static Long incr(String key, int cacheSeconds) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.incr(key);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("INCR {}", key);
        } catch (Exception e) {
            logger.warn("INCR {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * INCRBY: 为key存储的数值加上增量increment
     * @param key 键
     * @param value 增量值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static Long incrBy(String key, long value, int cacheSeconds) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.incrBy(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("INCRBY {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("INCRBY {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * DECR: 为key存储的数值减1
     * @param key 键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static Long decr(String key, int cacheSeconds) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.decr(key);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("DECR {}", key);
        } catch (Exception e) {
            logger.warn("DECR {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * DECRBY: 为key存储的数值减去减量decrement
     * @param key 键
     * @param value 减量值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static Long decrBy(String key, long value, int cacheSeconds) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.decr(key);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("DECRBY {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("DECRBY {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                logger.debug("get {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("get {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = toObject(jedis.get(getBytesKey(key)));
                logger.debug("getObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("set {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("set {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置的key超时时间(秒)
     * cacheSeconds为负数时会立即删除该key，为0时会删除过期时间（永不过期）
     */
    public static void setExpire(String key, int cacheSeconds) {
        Jedis jedis = getResource();
        jedis.expire(key, cacheSeconds);
        returnResource(jedis);
    }

    /**
     * 获取key的剩余存活时间(毫秒)
     */
    public static Long getTtl(String key) {
        Jedis jedis = getResource();
        Long ttl = jedis.ttl(key);
        returnResource(jedis);
        return ttl;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(getBytesKey(key), toBytes(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObject {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public static List<String> getList(String key) {
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
                logger.debug("getList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String key) {
        List<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list){
                    value.add(toObject(bs));
                }
                logger.debug("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setList {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }

//			List<byte[]> list = Lists.newArrayList();
//			for (Object o : value){
//				list.add(toBytes(o));
//			}
//			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());

            byte[][] arr = new byte[value.size()][];
            for (int i = 0; i < value.size(); i++) {
                arr[i] = toBytes(value.get(i));
            }

            result = jedis.rpush(getBytesKey(key), arr);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObjectList {} = {}", key, value);
        } catch (Exception e) {
            logger.error("setObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long listAdd(String key, int cacheSeconds, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.rpush(key, value);
            if (cacheSeconds > 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("listAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("listAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 从List缓存中删除值：移除列表中与参数 VALUE 相等的 COUNT 个元素。
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     * count = 0 : 移除表中所有与 VALUE 相等的值。
     * @param key 键
     * @param value 值
     * @param count 超时时间，0为不超时
     * @return 被移除的元素数量
     */
    public static long listDel(String key, long count, String value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.lrem(key,  count, value);
            logger.debug("listDel {} = {}", key, value);
        } catch (Exception e) {
            logger.error("listDel {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listAdd(String key, String... value) {
        return listAdd(key, 0, value);
    }

    /**
     * 截取list子集(对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除)
     * @param key 键
     * @param start 起始下标
     * @param end 结束下标
     *            下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     *            你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * @return
     */
    public static String listLtrim(String key, long start, long end) {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.ltrim(key, start, end);
            logger.debug("listTrim {} = {}-{}", key, start, end);
        } catch (Exception e) {
            logger.error("listTrim {} = {}-{}", key, start, end, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除并返回列表的第一个元素。
     */
    public static String listLpop(String key) {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.lpop(key);
            logger.debug("listLpop {}", key);
        } catch (Exception e) {
            logger.warn("listLpop {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public static List<String> listBlpop(int timeout, String... keys) {
        List<String> result = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.blpop(timeout, keys);
            logger.debug("listBlpop {}", keys);
        } catch (Exception e) {
            logger.warn("listBlpop {}", keys, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除并返回列表的最后一个元素。
     */
    public static String listRpop(String key) {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.rpop(key);
            logger.debug("listRpop {}", key);
        } catch (Exception e) {
            logger.warn("listRpop {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
//			List<byte[]> list = Lists.newArrayList();
//			for (Object o : value){
//				list.add(toBytes(o));
//			}
//			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
            byte[][] arr = new byte[value.length][];
            for (int i = 0; i < value.length; i++) {
                arr[i] = toBytes(value[i]);
            }
            result = jedis.rpush(getBytesKey(key), arr);
            logger.debug("listObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.error("listObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Set<String> getSet(String key) {
        Set<String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.smembers(key);
                logger.debug("getSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Sets.newHashSet();
                Set<byte[]> set = jedis.smembers(getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs));
                }
                logger.debug("getObjectSet {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.sadd(key, value.toArray(new String[value.size()]));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setSet {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = jedis.sadd(getBytesKey(key), (byte[][])set.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObjectSet {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectSet {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSetAdd(String key, int cacheSeconds, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.sadd(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setSetAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetAdd(String key, String... value) {
        return setSetAdd(key, 0 , value);
    }

    /**
     * 判断Set缓存成员是否包含value
     * @param key 键
     * @param value 值
     * @return
     */
    public static Boolean setSetHas(String key, String value) {
        Boolean flag = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            flag = jedis.sismember(key, value);
            logger.debug("setSetHas {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetHas {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return flag;
    }

    /**
     * 获取Set缓存成员个数
     * @param key 键
     * @return
     */
    public static Long setSetLength(String key) {
        Long len = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            len = jedis.scard(key);
            logger.debug("setSetLength {} = {}", key);
        } catch (Exception e) {
            logger.warn("setSetLength {} = {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return len;
    }

    /**
     * 获取List缓存成员个数
     * @param key 键
     * @return
     */
    public static Long listLength(String key) {
        Long len = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            len = jedis.llen(key);
            logger.debug("listLength {} = {}", key);
        } catch (Exception e) {
            logger.warn("listLength {} = {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return len;
    }

    /**
     * 向Set缓存中删除值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetDel(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.srem(key, value);
            logger.debug("setSetAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])set.toArray());
            logger.debug("setSetObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setSetObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.hgetAll(key);
                logger.debug("getMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Maps.newHashMap();
                Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()){
//					value.put(StringUtils.toString(e.getKey()), toObject(e.getValue()));
                    value.put(String.valueOf(e.getKey()), e.getValue());
                }
                logger.debug("getObjectMap {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObjectMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectMap {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapPut(String key, Map<String, String> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hmset(key, value);
            logger.debug("mapPut {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("mapPut {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
            logger.debug("mapObjectPut {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("mapObjectPut {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    public static long mapRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(key, mapKey);
            logger.debug("mapRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    public static long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
            logger.debug("mapObjectRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    public static boolean mapExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(key, mapKey);
            logger.debug("mapExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @param mapKey 值
     * @return
     */
    public static boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
            logger.debug("mapObjectExists {}  {}", key, mapKey);
        } catch (Exception e) {
            logger.warn("mapObjectExists {}  {}", key, mapKey, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static long del(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)){
                result = jedis.del(key);
                logger.debug("del {}", key);
            }else{
                logger.debug("del {} not exists", key);
            }
        } catch (Exception e) {
            logger.warn("del {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public static long delObject(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))){
                result = jedis.del(getBytesKey(key));
                logger.debug("delObject {}", key);
            }else{
                logger.debug("delObject {} not exists", key);
            }
        } catch (Exception e) {
            logger.warn("delObject {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(key);
            logger.debug("exists {}", key);
        } catch (Exception e) {
            logger.warn("exists {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(getBytesKey(key));
            logger.debug("existsObject {}", key);
        } catch (Exception e) {
            logger.warn("existsObject {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取资源
     * @return
     * @throws JedisException
     */
    public static Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
//			logger.debug("getResource.", jedis);
        } catch (JedisException e) {
            logger.warn("getResource.", e);
            returnBrokenResource(jedis);
            throw e;
        }
        return jedis;
    }

    /**
     * 归还资源
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 获取byte[]类型Key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) throws UnsupportedEncodingException {
        if(object instanceof String){
            return StringUtils.getBytes((String) object, "UTF-8");
        }else{
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object){
        return ObjectUtils.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes){
        return ObjectUtils.unserialize(bytes);
    }

}
