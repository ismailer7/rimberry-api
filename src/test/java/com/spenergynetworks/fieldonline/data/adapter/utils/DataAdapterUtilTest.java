package com.spenergynetworks.fieldonline.data.adapter.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataAdapterUtilTest {

    @Test
    void alarmsMapping() {
        assertEquals("ON", DataAdapterUtil.alarmsMapping("UNRESOLVED"));
        assertEquals("OFF", DataAdapterUtil.alarmsMapping("RESOLVED"));
        assertEquals("RESOLVING", DataAdapterUtil.alarmsMapping("RESOLVING"));
    }
}