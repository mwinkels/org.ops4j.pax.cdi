/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.cdi.extension;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

import org.ops4j.pax.cdi.api.Component;
import org.ops4j.pax.cdi.api.Immediate;
import org.ops4j.pax.cdi.api.Service;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;

public class MandatoryNonGreedyReferenceTest extends AbstractTest {

    @Test
    public void test() throws Exception {
        createCdi(Hello.class);

        Assert.assertEquals(0, Hello.created.get());
        Assert.assertEquals(0, Hello.destroyed.get());

        ServiceRegistration<MyService> registration1 = register(MyService.class, new MyService() {
            @Override
            public String hello() {
                return "Hello world !!";
            }
        });

        Assert.assertEquals(1, Hello.created.get());
        Assert.assertEquals(0, Hello.destroyed.get());

        ServiceRegistration<MyService> registration2 = register(MyService.class, new MyService() {
            @Override
            public String hello() {
                return "Hello world !!";
            }
        }, -1);

        Assert.assertEquals(1, Hello.created.get());
        Assert.assertEquals(0, Hello.destroyed.get());

        registration1.unregister();

        Assert.assertEquals(2, Hello.created.get());
        Assert.assertEquals(1, Hello.destroyed.get());

        registration2.unregister();

        Assert.assertEquals(2, Hello.created.get());
        Assert.assertEquals(2, Hello.destroyed.get());
    }

    public interface MyService {

        String hello();

    }

    @Immediate @Component
    public static class Hello {

        static final AtomicInteger created = new AtomicInteger();
        static final AtomicInteger destroyed = new AtomicInteger();

        @Inject @Service
        MyService service;

        @PostConstruct
        public void init() {
            created.incrementAndGet();
            System.err.println("Creating Hello instance");
        }

        @PreDestroy
        public void destroy() {
            destroyed.incrementAndGet();
            System.err.println("Destroying Hello instance");
        }

        public String sayHelloWorld() {
            return service.hello();
        }
    }

}
