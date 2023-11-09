/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jdk;

import java.lang.annotation.Annotation;

import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.AnnotationUsage;
import org.hibernate.models.source.spi.AttributeDescriptor;
import org.hibernate.models.source.spi.SourceModelBuildingContext;
import org.hibernate.models.source.spi.ValueWrapper;

/**
 * @author Steve Ebersole
 */
public class NestedValueExtractor<A extends Annotation> extends AbstractValueExtractor<AnnotationUsage<A>,A> {
	private final ValueWrapper<AnnotationUsage<A>,A> wrapper;

	public NestedValueExtractor(ValueWrapper<AnnotationUsage<A>, A> wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	protected AnnotationUsage<A> wrap(
			A rawValue,
			AttributeDescriptor<AnnotationUsage<A>> attributeDescriptor,
			AnnotationTarget target,
			SourceModelBuildingContext buildingContext) {
		return wrapper.wrap( rawValue, target, buildingContext );
	}
}
