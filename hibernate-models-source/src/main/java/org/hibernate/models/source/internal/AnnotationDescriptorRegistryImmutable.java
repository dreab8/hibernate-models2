/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.ModelsException;
import org.hibernate.models.source.spi.AnnotationDescriptor;
import org.hibernate.models.source.spi.AnnotationDescriptorRegistry;

/**
 * @author Steve Ebersole
 */
public class AnnotationDescriptorRegistryImmutable extends AbstractAnnotationDescriptorRegistry {
	public AnnotationDescriptorRegistryImmutable(
			Map<Class<? extends Annotation>, AnnotationDescriptor<?>> descriptorMap,
			Map<AnnotationDescriptor<?>, AnnotationDescriptor<?>> repeatableByContainerMap) {
		super( descriptorMap, repeatableByContainerMap );
	}

	@Override
	public <A extends Annotation> AnnotationDescriptor<A> getDescriptor(Class<A> javaType) {
		return resolveDescriptor( javaType, null );
	}

	@Override
	public <A extends Annotation> AnnotationDescriptor<A> resolveDescriptor(
			Class<A> javaType,
			DescriptorCreator<A> creator) {
		//noinspection unchecked
		final AnnotationDescriptor<A> descriptor = (AnnotationDescriptor<A>) descriptorMap.get( javaType );
		if ( descriptor == null ) {
			throw new ModelsException( "AnnotationDescriptorRegistry is immutable - " + javaType.getName() );
		}
		return descriptor;
	}

	@Override
	public AnnotationDescriptorRegistry makeImmutableCopy() {
		return this;
	}
}
