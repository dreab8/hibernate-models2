/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

/**
 * @author Steve Ebersole
 */
public interface JaxbToOneAttribute extends JaxbAssociationAttribute {
	JaxbSingularFetchModeImpl getFetchMode();
	void setFetchMode(JaxbSingularFetchModeImpl mode);
}
