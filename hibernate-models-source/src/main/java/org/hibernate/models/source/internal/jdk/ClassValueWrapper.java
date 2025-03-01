/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jdk;

import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.ClassDetails;
import org.hibernate.models.source.spi.SourceModelBuildingContext;
import org.hibernate.models.source.spi.ValueWrapper;

/**
 * @author Steve Ebersole
 */
public class ClassValueWrapper implements ValueWrapper<ClassDetails,Class<?>> {
	public static final ClassValueWrapper JDK_CLASS_VALUE_WRAPPER = new ClassValueWrapper();

	@Override
	public ClassDetails wrap(Class<?> rawValue, AnnotationTarget target, SourceModelBuildingContext buildingContext) {
		return buildingContext.getClassDetailsRegistry().resolveClassDetails( rawValue.getName() );
	}
}
