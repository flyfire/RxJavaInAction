package com.solarexsoft.rxjavainaction;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Solarex on 2020/4/23/9:59 PM
 * Desc:
 */
public class TestCompletableFutureTest {
    @Test
    public void completableFutureTest() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
    }
}