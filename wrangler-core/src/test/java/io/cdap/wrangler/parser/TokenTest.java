package io.cdap.wrangler.parser;

import org.junit.Test;

import io.cdap.wrangler.utils.ByteSize;
import io.cdap.wrangler.utils.TimeDuration;

import static org.junit.Assert.*;

public class TokenTest {
    @Test
    public void testByteSizeParsing() {
        // Test cases for byte size parsing
        assertEquals(1024, new ByteSize("1KB").getBytes());
        assertEquals(1048576, new ByteSize("1MB").getBytes());
        assertEquals(1073741824, new ByteSize("1GB").getBytes());
        assertEquals(1099511627776L, new ByteSize("1TB").getBytes());
    }

    @Test
    public void testTimeDurationParsing() {
        // Test cases for time duration parsing
        assertEquals(1000, new TimeDuration("1s").getMilliseconds());
        assertEquals(60000, new TimeDuration("1m").getMilliseconds());
        assertEquals(3600000, new TimeDuration("1h").getMilliseconds());
        assertEquals(86400000, new TimeDuration("1d").getMilliseconds());
    }
}
