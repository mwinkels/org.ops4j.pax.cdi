/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.cdi.sample9.impl;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.sample9.SampleServiceWithGenericTypeParameter;

/**
 * A sample implementation of {@link SampleServiceWithGenericTypeParameter}, which gets registered as an OSGi
 * service.
 *
 * @author Florian Brunner (puce77)
 */
@OsgiServiceProvider
public class SampleServiceImpl implements SampleServiceWithGenericTypeParameter<Object> {

    @Override
    public Object foo() {
        return "bar";
    }

}
