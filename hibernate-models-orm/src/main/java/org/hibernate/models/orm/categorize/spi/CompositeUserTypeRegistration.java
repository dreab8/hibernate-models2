/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm.categorize.spi;

import org.hibernate.models.source.spi.ClassDetails;

/**
 * Registration for a {@linkplain org.hibernate.usertype.CompositeUserType}
 *
 * @see org.hibernate.annotations.CompositeTypeRegistration
 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbCompositeUserTypeRegistrationImpl
 *
 * @author Steve Ebersole
 */
public class CompositeUserTypeRegistration {
	private final ClassDetails embeddableClass;
	private final ClassDetails userTypeClass;

	public CompositeUserTypeRegistration(ClassDetails embeddableClass, ClassDetails userTypeClass) {
		this.embeddableClass = embeddableClass;
		this.userTypeClass = userTypeClass;
	}

	public ClassDetails getEmbeddableClass() {
		return embeddableClass;
	}

	public ClassDetails getUserTypeClass() {
		return userTypeClass;
	}
}
