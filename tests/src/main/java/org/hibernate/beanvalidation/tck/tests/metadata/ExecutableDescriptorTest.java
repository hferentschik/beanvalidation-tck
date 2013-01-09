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
package org.hibernate.beanvalidation.tck.tests.metadata;


import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.ElementDescriptor.Kind;
import javax.validation.metadata.MethodDescriptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

import org.hibernate.beanvalidation.tck.tests.metadata.CustomerService.MyCrossParameterConstraint;
import org.hibernate.beanvalidation.tck.util.shrinkwrap.WebArchiveBuilder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Gunnar Morling
 */
@SpecVersion(spec = "beanvalidation", version = "1.1.0")
public class ExecutableDescriptorTest extends Arquillian {

	@Deployment
	public static WebArchive createTestArchive() {
		return new WebArchiveBuilder()
				.withTestClass( ExecutableDescriptorTest.class )
				.withClasses(
						Account.class,
						Customer.class,
						CustomerService.class,
						Executables.class,
						Person.class
				)
				.build();
	}

	@Test
	@SpecAssertion(section = "6.5", id = "a")
	public void testGetNameForMethod() {
		MethodDescriptor descriptor = Executables.parameterConstrainedMethod();
		assertEquals( descriptor.getName(), "createCustomer" );
	}

	@Test
	@SpecAssertion(section = "6.5", id = "a")
	public void testGetNameForConstructor() {
		ConstructorDescriptor descriptor = Executables.parameterConstrainedConstructor();
		assertEquals( descriptor.getName(), "CustomerService" );
	}

	@Test
	@SpecAssertion(section = "6.5", id = "b")
	public void testGetParameterDescriptorsForMethod() {
		MethodDescriptor descriptor = Executables.parameterConstrainedMethod();
		assertEquals(
				descriptor.getParameterDescriptors().size(),
				2,
				"Size of parameter descriptor list doesn't match method parameter count"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "b")
	public void testGetParameterDescriptorsForParameterlessMethod() {
		MethodDescriptor descriptor = Executables.returnValueConstrainedMethod();
		assertEquals(
				descriptor.getParameterDescriptors().size(),
				0,
				"Size of parameter descriptor list doesn't match method parameter count"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "b")
	public void testGetParameterDescriptorsForConstructor() {
		ConstructorDescriptor descriptor = Executables.parameterConstrainedConstructor();
		assertEquals(
				descriptor.getParameterDescriptors().size(),
				2,
				"Size of parameter descriptor list doesn't match constructor parameter count"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "b")
	public void testGetParameterDescriptorsForParameterlessConstructor() {
		ConstructorDescriptor descriptor = Executables.returnValueConstrainedConstructor();
		assertEquals(
				descriptor.getParameterDescriptors().size(),
				0,
				"Size of parameter descriptor list doesn't match constructor parameter count"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "c")
	public void testGetReturnValueDescriptorForMethod() {
		MethodDescriptor descriptor = Executables.returnValueConstrainedMethod();
		assertNotNull(
				descriptor.getReturnValueDescriptor(),
				"Return value descriptor should not be null"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "c")
	public void testGetUnconstrainedReturnValueDescriptorForMethod() {
		MethodDescriptor descriptor = Executables.cascadedParameterMethod();
		assertNotNull(
				descriptor.getReturnValueDescriptor(),
				"Return value descriptor should not be null"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "c")
	public void testReturnValueDescriptorForVoidMethod() {
		MethodDescriptor descriptor = Executables.parameterConstrainedMethod();
		assertNull(
				descriptor.getReturnValueDescriptor(),
				"Return value descriptor should be null"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "c")
	public void testGetReturnValueDescriptorForConstructor() {
		ConstructorDescriptor descriptor = Executables.returnValueConstrainedConstructor();
		assertNotNull(
				descriptor.getReturnValueDescriptor(),
				"Return value descriptor should not be null"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "c")
	public void testGetUnconstrainedReturnValueDescriptorForConstructor() {
		ConstructorDescriptor descriptor = Executables.cascadedParameterConstructor();
		assertNotNull(
				descriptor.getReturnValueDescriptor(),
				"Return value descriptor should not be null"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForMethod_Constrained() {
		MethodDescriptor descriptor = Executables.parameterConstrainedMethod();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForMethod_Cascaded() {
		MethodDescriptor descriptor = Executables.cascadedParameterMethod();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForMethod_CrossParameterConstrained() {
		MethodDescriptor descriptor = Executables.crossParameterConstrainedMethod();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForMethod_NotConstrained() {
		MethodDescriptor descriptor = Executables.cascadedReturnValueMethod();
		assertFalse(
				descriptor.areParametersConstrained(),
				"Should not be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForMethod_Parameterless() {
		MethodDescriptor descriptor = Executables.returnValueConstrainedMethod();
		assertFalse(
				descriptor.areParametersConstrained(),
				"Should not be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForConstructor_Constrained() {
		ConstructorDescriptor descriptor = Executables.parameterConstrainedConstructor();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForConstructor_Cascaded() {
		ConstructorDescriptor descriptor = Executables.cascadedParameterConstructor();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForConstructor_CrossParameterConstrained() {
		ConstructorDescriptor descriptor = Executables.crossParameterConstrainedConstructor();
		assertTrue(
				descriptor.areParametersConstrained(),
				"Should be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForConstructor_NotConstrained() {
		ConstructorDescriptor descriptor = Executables.cascadedReturnValueConstructor();
		assertFalse(
				descriptor.areParametersConstrained(),
				"Should not be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "d")
	public void testAreParametersConstrainedForConstructor_Parameterless() {
		ConstructorDescriptor descriptor = Executables.returnValueConstrainedConstructor();
		assertFalse(
				descriptor.areParametersConstrained(),
				"Should not be constrained on parameters"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForMethod_Constrained() {
		MethodDescriptor descriptor = Executables.returnValueConstrainedMethod();
		assertTrue(
				descriptor.isReturnValueConstrained(),
				"Should be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForMethod_Cascaded() {
		MethodDescriptor descriptor = Executables.cascadedReturnValueMethod();
		assertTrue(
				descriptor.isReturnValueConstrained(),
				"Should be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForMethod_NotConstrained() {
		MethodDescriptor descriptor = Executables.cascadedParameterMethod();
		assertFalse(
				descriptor.isReturnValueConstrained(),
				"Should not be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForMethod_Void() {
		MethodDescriptor descriptor = Executables.crossParameterConstrainedMethod();
		assertFalse(
				descriptor.isReturnValueConstrained(),
				"Should not be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForConstructor_Constrained() {
		ConstructorDescriptor descriptor = Executables.returnValueConstrainedConstructor();
		assertTrue(
				descriptor.isReturnValueConstrained(),
				"Should be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForConstructor_Cascaded() {
		ConstructorDescriptor descriptor = Executables.cascadedReturnValueConstructor();
		assertTrue(
				descriptor.isReturnValueConstrained(),
				"Should be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "e")
	public void testIsReturnValueConstrainedForConstructor_NotConstrained() {
		ConstructorDescriptor descriptor = Executables.cascadedParameterConstructor();
		assertFalse(
				descriptor.isReturnValueConstrained(),
				"Should not be constrained on return value"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testHasConstraintsForMethod() {
		MethodDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedMethod();
		assertFalse(
				parameterConstrainedDescriptor.hasConstraints(),
				"Should have no contraints"
		);

		MethodDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedMethod();
		assertFalse(
				returnValueConstrainedDescriptor.hasConstraints(),
				"Should have no contraints"
		);
		MethodDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedMethod();
		assertTrue(
				crossParameterConstrainedDescriptor.hasConstraints(),
				"Should have contraints"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testHasConstraintsForConstructor() {
		ConstructorDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedConstructor();
		assertFalse(
				parameterConstrainedDescriptor.hasConstraints(),
				"Should have no contraints"
		);

		ConstructorDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedConstructor();
		assertFalse(
				returnValueConstrainedDescriptor.hasConstraints(),
				"Should have no contraints"
		);
		ConstructorDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedConstructor();
		assertTrue(
				crossParameterConstrainedDescriptor.hasConstraints(),
				"Should have contraints"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testGetConstraintsForMethod() {
		MethodDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedMethod();
		assertTrue(
				parameterConstrainedDescriptor.getConstraintDescriptors().isEmpty(),
				"Should have no contraints"
		);

		MethodDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedMethod();
		assertTrue(
				returnValueConstrainedDescriptor.getConstraintDescriptors().isEmpty(),
				"Should have no contraints"
		);
		MethodDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedMethod();
		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors().size(),
				1,
				"Should have contraints"
		);

		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors()
						.iterator()
						.next()
						.getAnnotation()
						.annotationType(), MyCrossParameterConstraint.class, "Wrong constraint type"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testGetConstraintsForConstructor() {
		ConstructorDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedConstructor();
		assertTrue(
				parameterConstrainedDescriptor.getConstraintDescriptors().isEmpty(),
				"Should have no contraints"
		);

		ConstructorDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedConstructor();
		assertTrue(
				returnValueConstrainedDescriptor.getConstraintDescriptors().isEmpty(),
				"Should have no contraints"
		);
		ConstructorDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedConstructor();
		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors().size(),
				1,
				"Should have contraints"
		);

		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors()
						.iterator()
						.next()
						.getAnnotation()
						.annotationType(), MyCrossParameterConstraint.class, "Wrong constraint type"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testFindConstraintsForMethod() {
		MethodDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedMethod();
		assertTrue(
				parameterConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.isEmpty(),
				"Should have no contraints"
		);

		MethodDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedMethod();
		assertTrue(
				returnValueConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.isEmpty(),
				"Should have no contraints"
		);
		MethodDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedMethod();
		assertEquals(
				crossParameterConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.size(),
				1,
				"Should have contraints"
		);

		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors()
						.iterator()
						.next()
						.getAnnotation()
						.annotationType(), MyCrossParameterConstraint.class, "Wrong constraint type"
		);
	}

	@Test
	@SpecAssertion(section = "6.5", id = "f")
	public void testFindConstraintsForConstructor() {
		ConstructorDescriptor parameterConstrainedDescriptor = Executables.parameterConstrainedConstructor();
		assertTrue(
				parameterConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.isEmpty(),
				"Should have no contraints"
		);

		ConstructorDescriptor returnValueConstrainedDescriptor = Executables.returnValueConstrainedConstructor();
		assertTrue(
				returnValueConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.isEmpty(),
				"Should have no contraints"
		);
		ConstructorDescriptor crossParameterConstrainedDescriptor = Executables.crossParameterConstrainedConstructor();
		assertEquals(
				crossParameterConstrainedDescriptor.findConstraints()
						.getConstraintDescriptors()
						.size(),
				1,
				"Should have contraints"
		);

		assertEquals(
				crossParameterConstrainedDescriptor.getConstraintDescriptors()
						.iterator()
						.next()
						.getAnnotation()
						.annotationType(), MyCrossParameterConstraint.class, "Wrong constraint type"
		);
	}

	@Test
	@SpecAssertion(section = "6.2", id = "d")
	public void testGetKindForMethod() {
		MethodDescriptor descriptor = Executables.parameterConstrainedMethod();
		assertEquals(
				descriptor.getKind(),
				Kind.METHOD,
				"Descriptor should be of kind METHOD"
		);
	}

	@Test
	@SpecAssertion(section = "6.2", id = "d")
	public void testGetKindForConstructor() {
		ElementDescriptor descriptor = Executables.returnValueConstrainedConstructor();
		assertEquals(
				descriptor.getKind(),
				Kind.CONSTRUCTOR,
				"Descriptor should be of kind CONSTRUCTOR"
		);
	}

	@Test
	@SpecAssertion(section = "6.2", id = "e")
	public void testAsForMethod() {
		ElementDescriptor elementDescriptor = Executables.parameterConstrainedMethod();
		MethodDescriptor methodDescriptor = elementDescriptor.as( MethodDescriptor.class );
		assertNotNull( methodDescriptor, "Descriptor should not be null" );
		assertSame( methodDescriptor, elementDescriptor, "as() should return the same object" );
	}

	@Test
	@SpecAssertion(section = "6.2", id = "e")
	public void testAsForConstructor() {
		ElementDescriptor elementDescriptor = Executables.returnValueConstrainedConstructor();
		ConstructorDescriptor constructorDescriptor = elementDescriptor.as( ConstructorDescriptor.class );
		assertNotNull( constructorDescriptor, "Descriptor should not be null" );
		assertSame(
				constructorDescriptor,
				elementDescriptor,
				"as() should return the same object"
		);
	}

	@Test(expectedExceptions = ClassCastException.class)
	@SpecAssertion(section = "6.2", id = "e")
	public void testAsForMethodWithWrongType() {
		ElementDescriptor elementDescriptor = Executables.returnValueConstrainedConstructor();
		elementDescriptor.as( BeanDescriptor.class );
	}

	@Test(expectedExceptions = ClassCastException.class)
	@SpecAssertion(section = "6.2", id = "e")
	public void testAsForConstructorWithWrongType() {
		ElementDescriptor elementDescriptor = Executables.returnValueConstrainedConstructor();
		elementDescriptor.as( BeanDescriptor.class );
	}
}