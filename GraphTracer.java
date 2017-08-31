package com.sun.btrace.samples;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * Trace all requests to the facebook graph API.
 */
@BTrace public class GraphTracer {
    @OnMethod(
        clazz="/com.sproutsocial.facebook.commons.client.RobustWebRequestor.*/",
        method="/executeGet/"
    )
    public static void executeGet(String url) {
        println("executeGet: " + url);
    }
}

