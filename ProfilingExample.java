package com.sun.btrace.samples;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.Profiler;
import com.sun.btrace.annotations.*;

/**
 * Simple example on how to do profiling with btrace.
 *
 * Constrained to a single package.
 */
@BTrace class SproutProfiling {
    @Property
    Profiler profiler = BTraceUtils.Profiling.newProfiler();
    
    @OnMethod(clazz="/org\\.apache\\.cassandra\\.db\\.Column.*/", method="/.*/")
    void entry(@ProbeMethodName(fqn=true) String probeMethod) { 
        BTraceUtils.Profiling.recordEntry(profiler, probeMethod);
    }

    @OnMethod(clazz="/org\\.apache\\.cassandra\\.db\\.Column.*/", method="/.*/", location=@Location(value=Kind.RETURN))
    void exit(@ProbeMethodName(fqn=true) String probeMethod, @Duration long duration) { 
        BTraceUtils.Profiling.recordExit(profiler, probeMethod, duration);
    }

    @OnTimer(5000)
    void timer() {

/*
 *  1 - name of method
 *  2 - invocation
 *  3 - self.total
 *  4 - self.avg
 *  5 - self.min
 *  6 - self.max
 *  7 - wall.total
 *  8 - wall.avg
 *  9 - wall.min
 *  10 - wall.max
*/
        BTraceUtils.Profiling.printSnapshot(
                "",
                profiler,
                "%2$-10s %7$-15s %8$-15s %3$-15s %4$-15s %1$s");
    }
}
