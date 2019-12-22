package com.thinkis;

import com.thinkis.AppTest;

import com.thinkis.common.utils.JedisUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }


    public static void main(String[] args) {
//        JedisUtils.del("iotToken");
//        System.out.println(JedisUtils.get("iotToken"));
        System.out.println(JedisUtils.get("6a378a0c-ff79-4c58-aa6e-343d237b285f"));
//        JedisUtils.set("text","123456",0);
    }
}
