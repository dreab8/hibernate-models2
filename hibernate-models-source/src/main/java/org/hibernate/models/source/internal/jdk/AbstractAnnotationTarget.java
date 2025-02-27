/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jdk;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hibernate.models.source.internal.AnnotationTargetSupport;
import org.hibernate.models.source.spi.AnnotationUsage;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * AnnotationTarget where we know the annotations up front, but
 * want to delay processing them until (unless!) they are needed
 *
 * @author Steve Ebersole
 */
public abstract class AbstractAnnotationTarget implements AnnotationTargetSupport {
	private final Supplier<Annotation[]> annotationSupplier;
	private final SourceModelBuildingContext buildingContext;

	private Map<Class<? extends Annotation>, AnnotationUsage<?>> usagesMap;

	public AbstractAnnotationTarget(
			Supplier<Annotation[]> annotationSupplier,
			SourceModelBuildingContext buildingContext) {
		this.annotationSupplier = annotationSupplier;
		this.buildingContext = buildingContext;
	}

	@Override
	public SourceModelBuildingContext getBuildingContext() {
		return buildingContext;
	}

	@Override
	public Map<Class<? extends Annotation>, AnnotationUsage<? extends Annotation>> getUsageMap() {
		if ( usagesMap == null ) {
			usagesMap = buildUsagesMap();
		}
		return usagesMap;
	}

	private Map<Class<? extends Annotation>, AnnotationUsage<?>> buildUsagesMap() {
		final Map<Class<? extends Annotation>, AnnotationUsage<?>> result = new HashMap<>();
		AnnotationUsageBuilder.processAnnotations(
				annotationSupplier.get(),
				this,
				result::put,
				buildingContext
		);
		return result;
	}

	@Override
	public void clearAnnotationUsages() {
		getUsageMap().clear();
	}

	@Override
	public <X extends Annotation> void removeAnnotationUsage(Class<X> annotationType) {
		if ( usagesMap != null ) {
			usagesMap.remove( annotationType );
		}
	}

	@Override
	public <X extends Annotation> void addAnnotationUsage(AnnotationUsage<X> annotationUsage) {
		getUsageMap().put( annotationUsage.getAnnotationType(), annotationUsage );
	}
}
