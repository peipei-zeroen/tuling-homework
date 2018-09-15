package org.zeroen.tuling.homework.lock;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 规定一些与业务不太相关的行为
 * @Author
 * @Description
 * @Date Created in 1:32 2018/9/14
 * @Modified By：
 */
public abstract class AbstractSeckill implements ISeckill {

    protected static int DEFAULT_ITEM_AMOUNT = 100;

    protected AtomicInteger buycount = new AtomicInteger(0);
    protected AtomicInteger showcount = new AtomicInteger(0);

    protected List<String> items = new ArrayList<>();
    protected final int itemLength;

    protected final Map<String, Integer> cache = new HashMap<>();

    public AbstractSeckill(int itemLength) {
        this.itemLength = itemLength;
        init();
    }

    protected void init() {
        //final Random random = new Random();
        IntStream.range(0, itemLength).forEach(i -> {
            String item = randomString(16);
            items.add(item);
            //cache.put(item, 20 + random.nextInt(81));
            cache.put(item, DEFAULT_ITEM_AMOUNT);
        });
    }

    public String randomGetItemKey() {
        final Random random = new Random();
        return items.get(random.nextInt(itemLength));
    }

    public int getBuycount() {
        return buycount.intValue();
    }

    public int getShowcount() {
        return showcount.intValue();
    }

    public int getTotalCount() {
        return getBuycount() + getShowcount();
    }

    protected static String randomString(int len) {
        StringBuilder builder = new StringBuilder(len);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            char c = (char) (33 + random.nextInt(94));
            builder.append(c);
        }
        return builder.toString();
    }

}
