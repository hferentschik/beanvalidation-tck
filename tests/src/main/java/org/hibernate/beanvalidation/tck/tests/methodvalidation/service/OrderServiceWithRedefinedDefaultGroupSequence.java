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
package org.hibernate.beanvalidation.tck.tests.methodvalidation.service;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.beanvalidation.tck.tests.methodvalidation.constraint.MyCrossParameterConstraint;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.constraint.ValidOrder;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.constraint.ValidOrderService;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.constraint.ValidRetailOrder;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.constraint.ValidRetailOrderService;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.model.Item;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.model.Order;
import org.hibernate.beanvalidation.tck.tests.methodvalidation.service.OrderServiceWithRedefinedDefaultGroupSequence.Basic;

/**
 * @author Gunnar Morling
 */
@GroupSequence({ Basic.class, OrderServiceWithRedefinedDefaultGroupSequence.class })
public class OrderServiceWithRedefinedDefaultGroupSequence {

	public interface Basic {
	}

	@Size(min = 5, groups = Basic.class)
	@Pattern(regexp = "aaa")
	private String name;

	public OrderServiceWithRedefinedDefaultGroupSequence() {
	}

	public OrderServiceWithRedefinedDefaultGroupSequence(String name) {
		this.name = name;
	}

	@MyCrossParameterConstraint
	@ValidOrder(groups = Basic.class)
	@ValidRetailOrder
	@Valid
	public Order placeOrder(
			@NotNull(groups = Basic.class) String customer,
			@Valid Item item,
			@Min(value = 1) byte quantity) {
		return null;
	}

	@MyCrossParameterConstraint
	@ValidOrderService(groups = Basic.class)
	@ValidRetailOrderService
	@Valid
	public OrderServiceWithRedefinedDefaultGroupSequence(@NotNull(groups = Basic.class) String customer, @Valid Item item, @Min(
			value = 1) byte quantity) {
	}

	public String getName() {
		return name;
	}
}
