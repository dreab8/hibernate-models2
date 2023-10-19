/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source.internal;

import org.hibernate.models.source.spi.MemberDetails;

/**
 * @author Steve Ebersole
 */
public interface MutableMemberDetails extends MemberDetails, MutableAnnotationTarget {
}
