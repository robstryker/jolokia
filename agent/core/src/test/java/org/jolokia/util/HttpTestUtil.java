package org.jolokia.util;

/*
 * Copyright 2009-2011 Roland Huss
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletInputStream;

import static org.easymock.EasyMock.expect;

/**
 * @author roland
 * @since 31.08.11
 */
public class HttpTestUtil {

    public static final String HEAP_MEMORY_POST =
            "{ \"type\": \"read\",\"mbean\": \"java.lang:type=Memory\", \"attribute\": \"HeapMemoryUsage\"}";
    public static final String HEAP_MEMORY_GET = "/read/java.lang:type=Memory/HeapMemoryUsage";

    public static ServletInputStream createServletInputStream(String pReq) {
        final ByteArrayInputStream bis =
                new ByteArrayInputStream(pReq.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    public static void prepareServletConfigMock(ServletConfig config,String ... pInitParams) {
        Map<String,String> configParams = new HashMap<String, String>();
        if (pInitParams != null) {
            for (int i = 0; i < pInitParams.length; i += 2) {
                configParams.put(pInitParams[i],pInitParams[i+1]);
            }
            for (String key : configParams.keySet()) {
                expect(config.getInitParameter(key)).andReturn(configParams.get(key)).anyTimes();
            }
        }

        Vector paramNames = new Vector(configParams.keySet());
        expect(config.getInitParameterNames()).andReturn(paramNames.elements());
    }
}
