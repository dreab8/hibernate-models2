/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.models.source.internal.jdk.AnnotationDescriptorImpl;
import org.hibernate.models.source.spi.AnnotationDescriptor;
import org.hibernate.models.source.spi.AnnotationDescriptorRegistry;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * Access to AnnotationDescriptor instances based on a number of look-ups
 *
 * @author Steve Ebersole
 */
public class AnnotationDescriptorRegistryImpl implements AnnotationDescriptorRegistry {
	private final Map<Class<? extends Annotation>, AnnotationDescriptor<?>> descriptorMap = new ConcurrentHashMap<>();
	private final Map<AnnotationDescriptor<?>, AnnotationDescriptor<?>> repeatableByContainerMap = new ConcurrentHashMap<>();

	public AnnotationDescriptorRegistryImpl() {
	}

	public void register(AnnotationDescriptor<?> descriptor) {
		descriptorMap.put( descriptor.getAnnotationType(), descriptor );
		if ( descriptor.getRepeatableContainer() != null ) {
			// the descriptor is repeatable - register it under its container
			repeatableByContainerMap.put( descriptor.getRepeatableContainer(), descriptor );
		}
	}

	@Override
	public <A extends Annotation> AnnotationDescriptor<A> getDescriptor(Class<A> javaType) {
		return resolveDescriptor( javaType, this::buildAdHocAnnotationDescriptor );
	}

	@Override
	public <A extends Annotation> AnnotationDescriptor<A> resolveDescriptor(
			Class<A> javaType,
			DescriptorCreator<A> creator) {
		//noinspection unchecked
		final AnnotationDescriptor<A> existing = (AnnotationDescriptor<A>) descriptorMap.get( javaType );
		if ( existing != null ) {
			return existing;
		}

		final AnnotationDescriptor<A> created = creator.createDescriptor( javaType );
		descriptorMap.put( javaType, created );
		return created;
	}

	private <A extends Annotation> AnnotationDescriptor<A> buildAdHocAnnotationDescriptor(Class<A> javaType) {
		final Repeatable repeatable = javaType.getAnnotation( Repeatable.class );
		final AnnotationDescriptor<? extends Annotation> containerDescriptor;
		if ( repeatable != null ) {
			containerDescriptor = getDescriptor( repeatable.value() );
			assert containerDescriptor != null;
		}
		else {
			containerDescriptor = null;
		}

		final AnnotationDescriptorImpl<A> descriptor = new AnnotationDescriptorImpl<>( javaType, containerDescriptor );
		descriptorMap.put( javaType, descriptor );
		return descriptor;
	}

	/**
	 * Returns the descriptor of the {@linkplain Repeatable repeatable} annotation
	 * {@linkplain AnnotationDescriptor#getRepeatableContainer contained} by the given
	 * {@code containerDescriptor}. For example, calling this method with JPA's
	 * {@code NamedQueries} would return the descriptor for {@code NamedQuery}.
	 * <p/>
	 * It is the logical inverse of {@link AnnotationDescriptor#getRepeatableContainer}.
	 */
	@Override
	public <A extends Annotation> AnnotationDescriptor<A> getContainedRepeatableDescriptor(AnnotationDescriptor<A> containerDescriptor) {
		//noinspection unchecked
		return (AnnotationDescriptor<A>) repeatableByContainerMap.get( containerDescriptor );
	}

	/**
	 * @see #getContainedRepeatableDescriptor
	 */
	@Override
	public <A extends Annotation> AnnotationDescriptor<A> getContainedRepeatableDescriptor(Class<A> containerJavaType) {
		return getContainedRepeatableDescriptor( getDescriptor( containerJavaType ) );
	}
}
