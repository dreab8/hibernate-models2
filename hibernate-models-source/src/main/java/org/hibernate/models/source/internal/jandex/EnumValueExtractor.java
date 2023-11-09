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

/**
 * @author Steve Ebersole
 */
public class EnumValueExtractor<E extends Enum<E>> extends AbstractValueExtractor<E> {
	private final EnumValueWrapper<E> wrapper;

	public EnumValueExtractor(EnumValueWrapper<E> wrapper) {
		this.wrapper = wrapper;
	}

	public EnumValueExtractor(Class<E> enumClass) {
		this( new EnumValueWrapper<>( enumClass ) );
	}

	@Override
	protected E extractAndWrap(
			AnnotationValue jandexValue,
			AnnotationTarget target,
			SourceModelBuildingContext buildingContext) {
		assert jandexValue != null;
		return wrapper.wrap( jandexValue, target, buildingContext );
	}
}
