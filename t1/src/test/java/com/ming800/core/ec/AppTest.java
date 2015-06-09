package com.ming800.core.ec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for simple App2.
 */
public class AppTest {

    private App2 a;

    @Before
    public void setUp(){
        a = new App2();
    }

    @Test
    public void testJian(){
        Assert.assertEquals("wrong",1,a.jian(3,2));
    }

    @Test
    public void testChu(){
        Assert.assertEquals("wrong",2,a.chu(4,2));
    }
}
