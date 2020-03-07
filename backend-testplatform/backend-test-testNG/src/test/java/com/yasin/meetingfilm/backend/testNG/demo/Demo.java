package com.yasin.meetingfilm.backend.testNG.demo;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

/**
 * @author Yasin Zhang
 */
@Slf4j
public class Demo {

    @Test
    public void test1() {
        log.error("test1()");
    }

    @Test
    public void test2() {
        log.error("test2()");
    }

    @BeforeMethod
    public void beforeMethod() {
        log.info("beforeMethod()");
    }

    @AfterMethod
    public void afterMethod() {
        log.info("afterMethod()");
    }

    @BeforeClass
    public void beforeClass() {
        log.info("beforeClass()");
    }

    @AfterClass
    public void afterClass() {
        log.info("afterClass()");
    }

    @BeforeSuite
    public void beforeSuit() {
        log.info("beforeSuit()");
    }

    @AfterSuite
    public void afterSuit() {
        log.info("afterSuit()");
    }

}
