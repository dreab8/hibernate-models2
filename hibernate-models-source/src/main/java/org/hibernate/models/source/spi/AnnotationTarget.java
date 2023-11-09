/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.spi;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.models.source.AnnotationAccessException;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

/**
 * Abstraction of {@linkplain java.lang.reflect.AnnotatedElement}
 *
 * @author Steve Ebersole
 */
public interface AnnotationTarget {
	/**
	 * The kind of target
	 */
	Kind getKind();

	/**
	 * A descriptive name for the target used mostly for logging
	 */
	String getName();

	/**
	 * Get the usage of the given annotation on this target.
	 * <p/>
	 * For {@linkplain Repeatable repeatable} annotation types (e.g. {@linkplain NamedQuery}), this method will either-<ul>
	 *     <li>
	 *         if the repeatable annotation itself is present, it is returned.
	 *     </li>
	 *     <li>
	 *         if the {@linkplain Repeatable#value() "containing annotation"} is present (e.g. {@linkplain NamedQueries}), <ul>
	 *             <li>
	 *                 if the container contains just a single repeatable, that one is returned
	 *             </li>
	 *             <li>
	 *                 if the container contains multiple repeatables, {@link AnnotationAccessException} will be thrown
	 *             </li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 * <p/>
	 * For annotations which can {@linkplain ElementType#ANNOTATION_TYPE target annotations},
	 * all annotations on this target will be checked as well.
	 *
	 * @return The usage or {@code null}
	 */
	<A extends Annotation> AnnotationUsage<A> getUsage(AnnotationDescriptor<A> descriptor);

	/**
	 * Helper form of {@link #getUsage(AnnotationDescriptor)}
	 */
	<A extends Annotation> AnnotationUsage<A> getUsage(Class<A> type);

	/**
	 * Get all usages of the specified {@code annotationType} in this scope.
	 * <p/>
	 * For {@linkplain Repeatable repeatable} annotation types (e.g. {@linkplain NamedQuery}) -<ul>
	 *     <li>
	 *         if the repeatable annotation itself is present, a singleton list containing that single usage is returned
	 *     </li>
	 *     <li>
	 *         if the {@linkplain Repeatable#value() "containing annotation"} (e.g. {@linkplain NamedQueries}) is present,
	 *         the contained repeatable usages are extracted from the container and returned as a list
	 *     </li>
	 *     <li>
	 *         Otherwise, an empty list is returned.
	 *     </li>
	 * </ul>
	 *
	 * @apiNote If the passed annotation type is not repeatable, an empty list is returned.
	 */
	<A extends Annotation> List<AnnotationUsage<A>> getRepeatedUsages(AnnotationDescriptor<A> type);

	/**
	 * Helper form of {@linkplain #getRepeatedUsages(AnnotationDescriptor)}
	 */
	<A extends Annotation> List<AnnotationUsage<A>> getRepeatedUsages(Class<A> type);

	/**
	 * Call the {@code consumer} for each {@linkplain AnnotationUsage usage} of the
	 * given {@code type}.
	 *
	 * @apiNote For {@linkplain Repeatable repeatable} annotation types, the consumer will also be
	 * called for those defined on the container.
	 */
	<X extends Annotation> void forEachUsage(AnnotationDescriptor<X> type, Consumer<AnnotationUsage<X>> consumer);

	/**
	 * Helper form of {@link #forEachUsage(AnnotationDescriptor, Consumer)}
	 */
	<X extends Annotation> void forEachUsage(Class<X> type, Consumer<AnnotationUsage<X>> consumer);

	/**
	 * Get a usage of the given annotation {@code type} whose {@code attributeToMatch} attribute value
	 * matches the given {@code matchName}.
	 *
	 * @param matchName The name to match.
	 */
	default <X extends Annotation> AnnotationUsage<X> getNamedUsage(
			AnnotationDescriptor<X> type,
			String matchName) {
		return getNamedUsage( type, matchName, "name" );
	}

	/**
	 * Helper form of {@linkplain #getNamedUsage(AnnotationDescriptor, String)}
	 */
	default <X extends Annotation> AnnotationUsage<X> getNamedUsage(
			Class<X> type,
			String matchName) {
		return getNamedUsage( type, matchName, "name" );
	}

	/**
	 * Get a usage of the given annotation {@code type} whose {@code attributeToMatch} attribute value
	 * matches the given {@code matchName}.
	 *
	 * @param matchName The name to match.
	 * @param attributeToMatch Name of the attribute to match on.
	 */
	<X extends Annotation> AnnotationUsage<X> getNamedUsage(
			AnnotationDescriptor<X> type,
			String matchName,
			String attributeToMatch);

	/**
	 * Helper form of {@linkplain #getNamedUsage(AnnotationDescriptor, String, String)}
	 */
	<X extends Annotation> AnnotationUsage<X> getNamedUsage(
			Class<X> type,
			String matchName,
			String attributeToMatch);


	/**
	 * Subset of {@linkplain ElementType annotation targets} supported for mapping annotations
	 */
	enum Kind {
		ANNOTATION( ElementType.ANNOTATION_TYPE ),
		CLASS( ElementType.TYPE ),
		FIELD( ElementType.FIELD ),
		METHOD( ElementType.METHOD ),
		PACKAGE( ElementType.PACKAGE );

		private final ElementType elementType;

		Kind(ElementType elementType) {
			this.elementType = elementType;
		}

		public ElementType getCorrespondingElementType() {
			return elementType;
		}

		public static EnumSet<Kind> from(Target target) {
			if ( target == null ) {
				return EnumSet.allOf( Kind.class );
			}
			return from( target.value() );
		}

		public static EnumSet<Kind> from(ElementType[] elementTypes) {
			final EnumSet<Kind> kinds = EnumSet.noneOf( Kind.class );
			final Kind[] values = values();
			for ( int i = 0; i < elementTypes.length; i++ ) {
				for ( int v = 0; v < values.length; v++ ) {
					if ( values[v].getCorrespondingElementType().equals( elementTypes[i] ) ) {
						kinds.add( values[v] );
					}
				}
			}
			return kinds;
		}

		public static Kind from(ElementType elementType) {
			final Kind[] values = values();
			for ( int i = 0; i < values.length; i++ ) {
				if ( values[i].getCorrespondingElementType().equals( elementType ) ) {
					return values[i];
				}
			}
			return null;
		}
	}
}
