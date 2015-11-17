/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tiles.request.velocity.render;

import java.io.IOException;
import java.io.InputStream;


import org.apache.commons.collections.ExtendedProperties;
import org.apache.tiles.request.ApplicationAccess;
import org.apache.tiles.request.ApplicationResource;
import org.apache.tiles.request.ApplicationResourceLocator;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 *
 * @version $Rev$ $Date$
 * @since 3.0.0
 */

public class ApplicationResourceLoader extends ResourceLoader {

    private ApplicationResourceLocator applicationContext;

    /** {@inheritDoc} */
    @Override
    public void init(ExtendedProperties configuration) {
        Object obj = rsvc.getApplicationAttribute(ApplicationAccess.APPLICATION_CONTEXT_ATTRIBUTE);
        if (obj instanceof ApplicationResourceLocator) {
            applicationContext = (ApplicationResourceLocator) obj;
        } else {
            log.error("ApplicationResourceLoader: unable to retrieve ApplicationContext");
        }
    }

    /** {@inheritDoc} */
    @Override
    public InputStream getResourceStream(String source) throws ResourceNotFoundException {
        ApplicationResource resource = applicationContext.getResource(source);
        if (resource == null) {
            throw new ResourceNotFoundException(source);
        }
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new ResourceNotFoundException(source, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSourceModified(Resource resource) {
        return getLastModified(resource) != resource.getLastModified();
    }

    /** {@inheritDoc} */
    @Override
    public long getLastModified(Resource resource) {
        ApplicationResource r = applicationContext.getResource(resource.getName());
        try {
            return r == null ? 0l : r.getLastModified();
        } catch (IOException e) {
            return 0l;
        }
    }

    @Override
    public boolean resourceExists(String resourceName) {
        ApplicationResource resource = applicationContext.getResource(resourceName);
        return resource != null;
    }

}
