/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.spi;

import org.hibernate.models.source.spi.AnnotationTarget;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * Wraps a "raw" value.  For most cases, this is a simple pass-thru.  But for a few
 * cases, the raw and wrapper types differ:<dl>
 *     <dt>{@linkplain Class}</dt>
 *     <dd>{@code Class} values are wrapped as {@linkplain org.hibernate.models.source.spi.ClassDetails}</dd>

 *     <dt>{@linkplain java.lang.annotation.Annotation}</dt>
 *     <dd>{@code Class} values are wrapped as {@linkplain org.hibernate.models.source.spi.AnnotationUsage}</dd>

 *     <dt>arrays</dt>
 *     <dd>Array values are wrapped as {@linkplain java.util.List}</dd>
 * </dl>
 *
 * @author Steve Ebersole
 */
public interface ValueWrapper<W,R> {
	W wrap(R rawValue, AnnotationTarget target, SourceModelBuildingContext buildingContext);
}
