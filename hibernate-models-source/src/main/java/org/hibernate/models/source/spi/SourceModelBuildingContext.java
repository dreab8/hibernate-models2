/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.spi;

import org.hibernate.models.spi.ClassLoading;

import org.jboss.jandex.IndexView;

/**
 * Context object used while building references for {@link AnnotationDescriptor},
 * {@link AnnotationUsage} and friends.
 * <p/>
 * Acts as the global {@linkplain SharedAnnotationScope}
 *
 * @author Steve Ebersole
 */
public interface SourceModelBuildingContext extends SourceModelContext, SharedNamedAnnotationScope {
	/**
	 * If model processing code needs to load things from the class-loader, they should
	 * really use this access.  At this level, accessing the class-loader at all
	 * sh
	 */
	ClassLoading getClassLoading();

	/**
	 * Access to the pre-built Jandex index}, if any.
	 */
	IndexView getJandexIndex();
}
