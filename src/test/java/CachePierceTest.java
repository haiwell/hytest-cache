import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.gson.Gson;
import com.gs.jf.mapper.OrderMapper;
import com.gs.jf.model.Order;
import com.gs.jf.service.CacheService;
import com.gs.jf.service.OrderService;
import com.gs.jf.util.CashCommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 *
 * 缓存击穿测试
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/*.xml"})
public class CachePierceTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CacheService cacheServic;

    private final static String userCode = "UC00000003";

    private final static int threadNum = 2000;

    private CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    @Autowired
    private OrderService orderService;

    private final static int count = 1000000;



    @Test
    public void testBloomFilter() {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),count,0.01);
        Set<String> sets = new HashSet<>();
        List<String> lists = new ArrayList<>();
        for (int i=0 ; i < count ; i++) {
            String uuid = UUID.randomUUID().toString();
            bloomFilter.put(uuid);
            lists.add(uuid);
            sets.add(uuid);
        }

        int right = 0;
        int wrong = 0;
        for (int i=0 ; i < 10000 ; i++) {
            String str = i % 100 == 0 ? lists.get(i) : UUID.randomUUID().toString();
            if (bloomFilter.mightContain(str)) {
                if (sets.contains(str)) {
                    right ++;
                } else {
                    wrong ++;
                }
            }
        }
        System.out.println("right = " + right);
        System.out.println("wrong = " + wrong);
    }

    @Test
    public void TestZhimaAuthorize() throws IOException {
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(new Gson().toJson(order));
    }

    @Test
    public void testCache() {
        //cacheServic.putData("wanghai","11111");
        //System.out.println(cacheServic.getData("wanghai"));
        BigDecimal sumPrice = orderService.selectOrderPriceByUserCode(userCode);
        System.out.println(Thread.currentThread().getName() + " 结果：" + sumPrice);
    }

    public void testCache(String userCode) {
        BigDecimal sumPrice = orderService.selectOrderPriceByUserCode(userCode);
        System.out.println(Thread.currentThread().getName() + " 结果：" + sumPrice);
    }

    @Test
    public void testMultiThreadRequest() {
        for (int i=0; i < threadNum ; i++) {
            new Thread(new UserRequest()).start();
            countDownLatch.countDown();
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTime() {
        System.out.println(new Date().getTime());
    }


    private class UserRequest implements Runnable {
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String uuid = UUID.randomUUID().toString();
            testCache(uuid);
        }
    }


    public void testBank() {
        long start = System.currentTimeMillis();
        String ret = CashCommonService.post("backactivity/info/ActivityReportController/ptData",new HashMap<String,Object>());
        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " 结果 : " + ret );
        System.out.println("用时 ：" + (end  - start)/1000);
    }


    @Test
    public void testMapper() {
        List<String> list = orderMapper.selectDistinctUserCode();
        System.out.println(new Gson().toJson(list));
    }


}
