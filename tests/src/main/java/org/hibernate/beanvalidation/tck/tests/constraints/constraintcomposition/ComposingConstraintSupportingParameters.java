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
package org.hibernate.beanvalidation.tck.tests.constraints.constraintcomposition;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Gunnar Morling
 */
@Documented
@Constraint(validatedBy = ComposingConstraintSupportingParameters.Validator.class)
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface ComposingConstraintSupportingParameters {
	String message() default "{constraint.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	@SupportedValidationTarget(ValidationTarget.PARAMETERS)
	public class Validator
			implements ConstraintValidator<ComposingConstraintSupportingParameters, Object> {

		@Override
		public void initialize(ComposingConstraintSupportingParameters parameters) {
		}

		@Override
		public boolean isValid(Object parameters, ConstraintValidatorContext constraintValidatorContext) {
			return true;
		}
	}
}
