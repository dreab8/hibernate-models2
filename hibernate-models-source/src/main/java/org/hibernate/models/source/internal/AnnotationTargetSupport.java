/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.hibernate.models.source.spi.AnnotationDescriptor;
import org.hibernate.models.source.spi.AnnotationUsage;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * @author Steve Ebersole
 */
public interface AnnotationTargetSupport extends MutableAnnotationTarget {
	SourceModelBuildingContext getBuildingContext();
	Map<Class<? extends Annotation>, AnnotationUsage<? extends Annotation>> getUsageMap();

	@Override
	default <A extends Annotation> AnnotationUsage<A> getAnnotationUsage(AnnotationDescriptor<A> descriptor) {
		return AnnotationUsageHelper.getUsage( descriptor, getUsageMap() );
	}

	@Override
	default <A extends Annotation> AnnotationUsage<A> getAnnotationUsage(Class<A> type) {
		return getAnnotationUsage( getBuildingContext().getAnnotationDescriptorRegistry().getDescriptor( type ) );
	}

	@Override
	default <A extends Annotation> List<AnnotationUsage<A>> getRepeatedAnnotationUsages(AnnotationDescriptor<A> type) {
		return AnnotationUsageHelper.getRepeatedUsages( type, getUsageMap() );
	}

	@Override
	default <A extends Annotation> List<AnnotationUsage<A>> getRepeatedAnnotationUsages(Class<A> type) {
		return getRepeatedAnnotationUsages( getBuildingContext().getAnnotationDescriptorRegistry().getDescriptor( type ) );
	}

	@Override
	default <X extends Annotation> void forEachAnnotationUsage(Class<X> type, Consumer<AnnotationUsage<X>> consumer) {
		forEachAnnotationUsage(
				getBuildingContext().getAnnotationDescriptorRegistry().getDescriptor( type ),
				consumer
		);
	}

	@Override
	default <X extends Annotation> AnnotationUsage<X> getNamedAnnotationUsage(Class<X> type, String matchName) {
		return getNamedAnnotationUsage( getBuildingContext().getAnnotationDescriptorRegistry().getDescriptor( type ), matchName );
	}

	@Override
	default <X extends Annotation> AnnotationUsage<X> getNamedAnnotationUsage(
			AnnotationDescriptor<X> type,
			String matchName,
			String attributeToMatch) {
		return AnnotationUsageHelper.getNamedUsage( type, matchName, attributeToMatch, getUsageMap() );
	}

	@Override
	default <X extends Annotation> AnnotationUsage<X> getNamedAnnotationUsage(
			Class<X> type,
			String matchName,
			String attributeToMatch) {
		return getNamedAnnotationUsage(
				getBuildingContext().getAnnotationDescriptorRegistry().getDescriptor( type ),
				matchName,
				attributeToMatch
		);
	}
}
