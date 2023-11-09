/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jandex;

import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

import org.jboss.jandex.AnnotationValue;

import static org.hibernate.models.source.internal.jandex.BooleanValueWrapper.JANDEX_BOOLEAN_VALUE_WRAPPER;

/**
 * Extracts boolean values from an attribute
 *
 * @author Steve Ebersole
 */
public class BooleanValueExtractor extends AbstractValueExtractor<Boolean> {
	public static final BooleanValueExtractor JANDEX_BOOLEAN_EXTRACTOR = new BooleanValueExtractor();

	@Override
	protected Boolean extractAndWrap(
			AnnotationValue jandexValue,
			AnnotationTarget target,
			SourceModelBuildingContext buildingContext) {
		assert jandexValue != null;
		return JANDEX_BOOLEAN_VALUE_WRAPPER.wrap( jandexValue, target, buildingContext );
	}
}
