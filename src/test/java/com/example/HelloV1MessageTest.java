package com.example;

import com.example.HelloV1Message;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloV1MessageTest {
    @Test
    public void testConstruction() {
        HelloV1Message msg = new HelloV1Message("some message");
    }

    @Test
    public void testMessageGetter() {
        HelloV1Message msg = new HelloV1Message("some message");
        assertEquals("some message", msg.message);
    }

    @Test
    public void testSmessageSetter() {
        HelloV1Message msg = new HelloV1Message("some message");
        msg.message = "some other message";
        assertEquals("some other message", msg.message);
    }
}