/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import org.hibernate.annotations.FetchMode;

import jakarta.persistence.FetchType;

/**
 * @author Steve Ebersole
 */
public class FetchModeMarshalling {
	public static FetchMode fromXml(String name) {
		return name == null ? null : FetchMode.valueOf( name );
	}

	public static String toXml(FetchMode fetchType) {
		return fetchType == null ? null : fetchType.name();
	}
}
