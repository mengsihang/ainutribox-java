package com.ainutribox.module.member.util;

import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author caohong
 */
@Component
public class RedisBase {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

// =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 集合
     */
    public void del(Collection<String> keys) {
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

// ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量缓存获取
     *
     * @param keys 键
     * @return 值
     */
    public List<Object> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量缓存放入
     *
     * @param maps key value
     */
    public void mSet(Map<String, Object> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 批量写入，管道pipeline
     *
     * @param map     key value
     * @param timeout 时间(秒) timeout要大于0 如果timeout小于等于0 将设置无限期
     * @return
     */
    public void mSet(Map<String, Object> map, long timeout) {
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                map.forEach((key, value) -> {
                    connection.set(key.getBytes(), valueSerializer.serialize(value), Expiration.seconds(timeout), RedisStringCommands.SetOption.UPSERT);
                });
                return null;
            }
        }, valueSerializer);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒) timeout要大于0 如果timeout小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间 timeout要大于0 如果timeout小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public <E> Collection<E> keys(String pattern) {
        return (Collection<E>) redisTemplate.keys(pattern);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

// ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key     键
     * @param item    项
     * @param value   值
     * @param timeout 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long timeout) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

// ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key     键
     * @param timeout 时间(秒)
     * @param values  值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long timeout, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

// ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public <T> List<T> lGet(String key, long start, long end) {
        try {
            return (List<T>) redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return
     */
    public <T> List<T> lGet(String key) {
        try {
            return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一个元素放入list尾部（右侧）
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lPush(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个元素放入list尾部（右侧）
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean lPush(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list尾部（右侧）
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lPush(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list尾部（右侧）
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean lPush(String key, List<Object> value, long timeout) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list尾部（右侧）
     *
     * @param key      键
     * @param value    值
     * @param timeout  时间
     * @param timeUnit 单位
     * @return
     */
    public boolean lPush(String key, List<Object> value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (timeout > 0) {
                expire(key, timeout, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除并返回集合尾部一个元素（右侧）
     *
     * @param key 键
     * @return
     */
    public Object lPop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 移除并返回集合尾部一个元素（右侧）
     *
     * @param key 键
     * @return
     */
    public List<Object> lPop(String key, long count) {
        // 获取当前队列里的值
        long curSize = redisTemplate.opsForList().size(key);
        if (curSize == 0) {
            return Collections.emptyList();
        }
        // 判断操作次数
        return redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public String execute(RedisOperations redisOperations) throws DataAccessException {
                final long finalSize = Math.min(curSize, count);
                for (long i = 0; i < finalSize; i++) {
                    redisOperations.opsForList().rightPop(key);
                }
                return null;
            }
        }).stream().map(obj -> (String) obj).collect(Collectors.toList());
    }

    /**
     * 将一个元素放入list头部（左侧）
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lShift(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个元素放入list头部（左侧）
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean lShift(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list头部（左侧）
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lShift(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list头部（左侧）
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean lShift(String key, List<Object> value, long timeout) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一组元素放入list头部（左侧）
     *
     * @param key      键
     * @param value    值
     * @param timeout  时间
     * @param timeUnit 单位
     * @return
     */
    public boolean lShift(String key, List<Object> value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (timeout > 0) {
                expire(key, timeout, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除并返回集合头部一个元素（左侧）
     *
     * @param key 键
     * @return
     */
    public Object lUnshift(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 移除并返回集合头部一个元素（左侧）
     *
     * @param key 键
     * @return
     */
    public List<Object> lUnshift(String key, long count) {
        // 获取当前队列里的值
        long curSize = redisTemplate.opsForList().size(key);
        if (curSize == 0) {
            return Collections.emptyList();
        }
        // 判断操作次数
        return redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public String execute(RedisOperations redisOperations) throws DataAccessException {
                final long finalSize = Math.min(curSize, count);
                for (long i = 0; i < finalSize; i++) {
                    redisOperations.opsForList().leftPop(key);
                }
                return null;
            }
        }).stream().map(obj -> (String) obj).collect(Collectors.toList());
    }


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 加锁，自旋重试三次，重试时间间隔300ms
     *
     * @param key      键
     * @param value    值
     * @param timeout  key过期时间
     * @param timeUnit 单位
     * @return
     */
    public Boolean tryLock(String key, Object value, long timeout, TimeUnit timeUnit) {
        Boolean locked = false;
        int tryCount = 3;
        while ((locked == null || !locked) && tryCount > 0) {
            locked = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
            tryCount--;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return locked;
    }


    /**
     * 加锁，自旋重试三次，重试时间间隔300ms
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean tryLock(String key, Object value) {
        Boolean locked = false;
        int tryCount = 3;
        while ((locked == null || !locked) && tryCount > 0) {
            locked = redisTemplate.opsForValue().setIfAbsent(key, value);
            tryCount--;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return locked;
    }

    /**
     * redis发布
     * @param channel
     * @param message
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel,message);
    }

    /**
     * 模糊删除key
     * @param likeKey
     */
    public void clearQuestionCache(String likeKey) {
        // 获取所有匹配的键
        Set<String> keys = redisTemplate.keys(likeKey+":*");
        if (keys != null && !keys.isEmpty()) {
            // 删除匹配的键
            redisTemplate.delete(keys);
        }
    }

}