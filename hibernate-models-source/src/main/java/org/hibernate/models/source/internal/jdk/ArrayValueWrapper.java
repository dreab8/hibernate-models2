/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal.jdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.models.internal.CollectionHelper;
import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.SourceModelBuildingContext;
import org.hibernate.models.source.spi.ValueWrapper;

/**
 * @author Steve Ebersole
 */
public class ArrayValueWrapper<V,R> implements ValueWrapper<List<V>,R[]> {
	private final ValueWrapper<V,R> elementWrapper;

	public ArrayValueWrapper(ValueWrapper<V, R> elementWrapper) {
		this.elementWrapper = elementWrapper;
	}

	@Override
	public List<V> wrap(R[] rawValues, AnnotationTarget target, SourceModelBuildingContext buildingContext) {
		if ( CollectionHelper.isEmpty( rawValues ) ) {
			return Collections.emptyList();
		}

		if ( rawValues.length == 1 ) {
			return Collections.singletonList( elementWrapper.wrap( rawValues[0], target, buildingContext ) );
		}

		final List<V> result = new ArrayList<>( rawValues.length );
		for ( int i = 0; i < rawValues.length; i++ ) {
			result.add( elementWrapper.wrap( rawValues[i], target, buildingContext ) );
		}
		return result;
	}
}
