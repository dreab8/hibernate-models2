/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm.categorize.xml;

import org.hibernate.models.ModelsException;

/**
 * Generally indicates a problem locating or binding an XML resource
 *
 * @author Steve Ebersole
 */
public class XmlResourceException extends ModelsException {
	public XmlResourceException(String message) {
		super( message );
	}

	public XmlResourceException(String message, Throwable cause) {
		super( message, cause );
	}
}
