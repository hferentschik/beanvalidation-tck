/*
* JBoss, Home of Professional Open Source
* Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.hibernate.beanvalidation.tck.tests.xmlconfiguration.constraintdeclaration.propertylevel;

import javax.validation.ConstraintDeclarationException;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

import org.hibernate.beanvalidation.tck.util.TestUtil;
import org.hibernate.beanvalidation.tck.util.shrinkwrap.WebArchiveBuilder;

/**
 * @author Hardy Ferentschik
 */
@SpecVersion(spec = "beanvalidation", version = "1.1.0")
public class InvalidGroupConversionInPropertyLevelOverridingTest extends Arquillian {

	@Deployment
	public static WebArchive createTestArchive() {
		return new WebArchiveBuilder()
				.withTestClass( InvalidGroupConversionInPropertyLevelOverridingTest.class )
				.withClasses( User.class, CreditCard.class )
				.withValidationXml( "validation-InvalidGroupConversionInPropertyLevelOverridingTest.xml" )
				.withResource( "user-constraints-InvalidGroupConversionInPropertyLevelOverridingTest.xml" )
				.build();
	}

	@Test(expectedExceptions = ConstraintDeclarationException.class)
	@SpecAssertions({
			@SpecAssertion(section = "8.1.1.3", id = "f")
	})
	public void testGroupConversionsAreAdditiveAndExceptionIsThrownForMultipleConversionWithSameSource() {
		Validator validator = TestUtil.getValidatorUnderTest();
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass( User.class );
		PropertyDescriptor propDescriptor = beanDescriptor.getConstraintsForProperty( "firstCreditCard" );
		propDescriptor.getGroupConversions();
	}
}
