/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jdk;

import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.AttributeDescriptor;
import org.hibernate.models.source.spi.ClassDetails;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

import static org.hibernate.models.source.internal.jdk.ClassValueWrapper.JDK_CLASS_VALUE_WRAPPER;

/**
 * @author Steve Ebersole
 */
public class ClassValueExtractor extends AbstractValueExtractor<ClassDetails,Class<?>> {
	public static final ClassValueExtractor JDK_CLASS_EXTRACTOR = new ClassValueExtractor();

	@Override
	protected ClassDetails wrap(
			Class<?> rawValue,
			AttributeDescriptor<ClassDetails> attributeDescriptor,
			AnnotationTarget target,
			SourceModelBuildingContext buildingContext) {
		return JDK_CLASS_VALUE_WRAPPER.wrap( rawValue, target, buildingContext );
	}

}
