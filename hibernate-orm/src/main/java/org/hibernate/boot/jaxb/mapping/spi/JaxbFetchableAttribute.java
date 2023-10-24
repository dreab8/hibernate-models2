/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html.
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.persistence.FetchType;

/**
 * JAXB binding interface for EAGER/LAZY
 *
 * @apiNote All standard attributes are fetchable (basics allow FetchType as well); this
 * contract distinguishes ANY mappings which are always eager and so do not allow
 * specifying FetchType.
 *
 * @author Brett Meyer
 * @author Steve Ebersole
 */
public interface JaxbFetchableAttribute extends JaxbPersistentAttribute {
	FetchType getFetch();
	void setFetch(FetchType value);
}
