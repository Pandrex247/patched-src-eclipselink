/*******************************************************************************
 * Copyright (c) 1998, 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/
package org.eclipse.persistence.testing.tests.security;

import org.eclipse.persistence.exceptions.DescriptorException;
import org.eclipse.persistence.exceptions.EclipseLinkException;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;

//Created by Ian Reid
//Date: April 25, 2k3
public class SecurityWhileInitializingAttributesInMethodAccessorTest extends ExceptionTestSaveSecurityManager {

    private AttributeAccessor accessor;

    public SecurityWhileInitializingAttributesInMethodAccessorTest() {
        super("This tests Security While Initializing Attributes In Method Accessor (TL-ERROR 87)", MethodAccessor.class);
    }

    protected void setup() {
        super.setup();
        expectedException = DescriptorException.securityWhileInitializingAttributesInMethodAccessor("dummy_Method", "dummy_Method", "SecurityWhileInitializingAttributesInMethodAccessorTest");

        DirectToFieldMapping mapping = new DirectToFieldMapping();
        mapping.setAttributeName("firstName");
        mapping.setFieldName("EMPLOYEE.F_NAME");
        mapping.setSetMethodName("setFirstName");
        mapping.setGetMethodName("getFirstName");
        accessor = mapping.getAttributeAccessor();
    }

    public void test() {
        try {
            accessor.initializeAttributes(getTestClass());
        } catch (EclipseLinkException exception) {
            caughtException = exception;
        }
    }

    static class MethodAccessor {
        public void setFirstName(String x) {
            //do nothing security manager will cause error to occur
        }

        public String getFirstName() {
            //do nothing security manager will cause error to occur
            return "";
        }
    }
}
