/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm.categorize.xml.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.jaxb.mapping.spi.JaxbEmbeddableImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEntityImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbMappedSuperclassImpl;
import org.hibernate.models.orm.categorize.xml.spi.PersistenceUnitMetadata;
import org.hibernate.models.orm.categorize.xml.spi.XmlProcessingResult;
import org.hibernate.models.source.spi.SourceModelBuildingContext;

/**
 * @author Steve Ebersole
 */
public class XmlProcessingResultImpl implements XmlProcessingResult {
	private final List<OverrideTuple<JaxbEntityImpl>> entityOverrides = new ArrayList<>();
	private final List<OverrideTuple<JaxbMappedSuperclassImpl>> mappedSuperclassesOverrides = new ArrayList<>();
	private final List<OverrideTuple<JaxbEmbeddableImpl>> embeddableOverrides = new ArrayList<>();

	public void addEntityOverride(OverrideTuple<JaxbEntityImpl> overrideTuple) {
		entityOverrides.add( overrideTuple );
	}

	public void addMappedSuperclassesOverride(OverrideTuple<JaxbMappedSuperclassImpl> overrideTuple) {
		mappedSuperclassesOverrides.add( overrideTuple );
	}

	public void addEmbeddableOverride(OverrideTuple<JaxbEmbeddableImpl> overrideTuple) {
		embeddableOverrides.add( overrideTuple );
	}

	@Override
	public void apply(PersistenceUnitMetadata metadata, SourceModelBuildingContext buildingContext) {
		ManagedTypeProcessor.processOverrideEmbeddable( getEmbeddableOverrides(), metadata, buildingContext );

		ManagedTypeProcessor.processOverrideMappedSuperclass( getMappedSuperclassesOverrides(), metadata, buildingContext );

		ManagedTypeProcessor.processOverrideEntity( getEntityOverrides(), metadata, buildingContext );
	}

	@Override
	public List<OverrideTuple<JaxbEntityImpl>> getEntityOverrides() {
		return entityOverrides;
	}

	@Override
	public List<OverrideTuple<JaxbMappedSuperclassImpl>> getMappedSuperclassesOverrides() {
		return mappedSuperclassesOverrides;
	}

	@Override
	public List<OverrideTuple<JaxbEmbeddableImpl>> getEmbeddableOverrides() {
		return embeddableOverrides;
	}
}
