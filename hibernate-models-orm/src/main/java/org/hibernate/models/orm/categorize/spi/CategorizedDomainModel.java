/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm.categorize.spi;

import java.util.Map;
import java.util.Set;

import org.hibernate.models.internal.IndexedConsumer;
import org.hibernate.models.internal.KeyedConsumer;
import org.hibernate.models.source.spi.AnnotationDescriptorRegistry;
import org.hibernate.models.source.spi.ClassDetails;
import org.hibernate.models.source.spi.ClassDetailsRegistry;

/**
 * The application's domain model, understood at a very rudimentary level - we know
 * a class is an entity, a mapped-superclass, ...  And we know about persistent attributes,
 * but again on a very rudimentary level.
 * <p/>
 * We also know about all {@linkplain #getGlobalRegistrations() global registrations} -
 * sequence-generators, named-queries, ...
 *
 * @author Steve Ebersole
 */
public interface CategorizedDomainModel {
	/**
	 * Registry of all known classes
	 */
	ClassDetailsRegistry getClassDetailsRegistry();

	/**
	 * Registry of all known {@linkplain java.lang.annotation.Annotation} descriptors (classes)
	 */
	AnnotationDescriptorRegistry getAnnotationDescriptorRegistry();

	/**
	 * All entity hierarchies defined in the persistence unit
	 */
	Set<EntityHierarchy> getEntityHierarchies();

	/**
	 * Iteration over the {@linkplain #getEntityHierarchies() entity hierarchies}
	 */
	default void forEachEntityHierarchy(IndexedConsumer<EntityHierarchy> hierarchyConsumer) {
		final Set<EntityHierarchy> entityHierarchies = getEntityHierarchies();
		if ( entityHierarchies.isEmpty() ) {
			return;
		}

		int pos = 0;
		for ( EntityHierarchy entityHierarchy : entityHierarchies ) {
			hierarchyConsumer.accept( pos, entityHierarchy );
			pos++;
		}
	}

	/**
	 * All mapped-superclasses defined in the persistence unit
	 */
	Map<String,ClassDetails> getMappedSuperclasses();

	/**
	 * Iteration over the {@linkplain #getMappedSuperclasses() mapped superclasses}
	 */
	default void forEachMappedSuperclass(KeyedConsumer<String, ClassDetails> consumer) {
		final Map<String, ClassDetails> mappedSuperclasses = getMappedSuperclasses();
		if ( mappedSuperclasses.isEmpty() ) {
			return;
		}

		mappedSuperclasses.forEach( consumer::accept );
	}

	/**
	 * All embeddables defined in the persistence unit
	 */
	Map<String,ClassDetails> getEmbeddables();

	/**
	 * Iteration over the {@linkplain #getEmbeddables() embeddables}
	 */

	default void forEachEmbeddable(KeyedConsumer<String, ClassDetails> consumer) {
		final Map<String, ClassDetails> embeddables = getEmbeddables();
		if ( embeddables.isEmpty() ) {
			return;
		}

		embeddables.forEach( consumer::accept );
	}

	/**
	 * Global registrations collected while processing the persistence-unit.
	 */
	GlobalRegistrations getGlobalRegistrations();
}
