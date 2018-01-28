package com.cll.images;

import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ImagesApplicationTests {

    @Test
    public void contextLoads() {
        boolean b = "http://tool.oschina.net/regex/#".matches("(https?)+://[^\\s]*");
        System.out.println(b);
    }

}
