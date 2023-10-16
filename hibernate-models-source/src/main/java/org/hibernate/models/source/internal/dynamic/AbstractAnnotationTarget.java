/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.dynamic;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.models.source.internal.AnnotationTargetSupport;
import org.hibernate.models.source.spi.AnnotationUsage;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractAnnotationTarget implements AnnotationTargetSupport {
	private final SourceModelBuildingContext buildingContext;
	private final Map<Class<? extends Annotation>, AnnotationUsage<?>> usageMap = new HashMap<>();

	public AbstractAnnotationTarget(SourceModelBuildingContext buildingContext) {
		this.buildingContext = buildingContext;
	}

	@Override
	public SourceModelBuildingContext getBuildingContext() {
		return buildingContext;
	}

	@Override
	public Map<Class<? extends Annotation>, AnnotationUsage<? extends Annotation>> getUsageMap() {
		return usageMap;
	}

	public <X extends Annotation> void apply(List<AnnotationUsage<X>> annotationUsages) {
		annotationUsages.forEach( this::apply );
	}

	/**
	 * Applies the given {@code annotationUsage} to this target.
	 *
	 * @todo It is undefined currently what happens if the annotation type is already applied on this target.
	 */
	public <X extends Annotation> void apply(AnnotationUsage<X> annotationUsage) {
		final AnnotationUsage<?> previous = usageMap.put( annotationUsage.getAnnotationType(), annotationUsage );

		if ( previous != null ) {
			// todo : ignore?  log?  exception?
		}
	}

}
