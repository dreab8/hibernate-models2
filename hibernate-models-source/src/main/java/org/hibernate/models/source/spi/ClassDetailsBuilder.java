/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.spi;

/**
 * Contract for creating the ClassDetails for a Java type we have not yet seen
 * as part of {@link ClassDetailsRegistry#resolveClassDetails}
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface ClassDetailsBuilder {
	/**
	 * Build a ClassDetails descriptor for a class with the given name
	 */
	ClassDetails buildClassDetails(String name, SourceModelBuildingContext buildingContext);
}
