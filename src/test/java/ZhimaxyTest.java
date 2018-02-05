import com.google.gson.Gson;
import com.gs.jf.mapper.OrderMapper;
import com.gs.jf.model.Order;
import com.gs.jf.service.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 
 * @ClassName: ZMXYTest
 * @Description: 芝麻信用测试类
 * @author wanghai
 * @date 2017年3月9日 下午4:02:49
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/*.xml"})
public class ZhimaxyTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CacheService cacheServic;
    
    @Test
    public void TestZhimaAuthorize() throws IOException {
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(new Gson().toJson(order));
    }

    @Test
    public void testCache() {
        cacheServic.putData("wanghai","11111");
        System.out.println(cacheServic.getData("wanghai"));
    }


    @Test
    public void testTime() {
        System.out.println(new Date().getTime());
    }

}
