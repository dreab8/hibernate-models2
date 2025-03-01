/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm.xml.complete;

import org.hibernate.boot.internal.BootstrapContextImpl;
import org.hibernate.boot.internal.MetadataBuilderImpl;
import org.hibernate.boot.model.process.spi.ManagedResources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.models.orm.process.ManagedResourcesImpl;
import org.hibernate.models.orm.categorize.spi.AttributeMetadata;
import org.hibernate.models.orm.categorize.spi.CategorizedDomainModel;
import org.hibernate.models.orm.categorize.spi.EntityHierarchy;
import org.hibernate.models.orm.categorize.spi.EntityTypeMetadata;

import org.junit.jupiter.api.Test;

import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.models.orm.categorize.spi.AttributeMetadata.AttributeNature.BASIC;
import static org.hibernate.models.orm.categorize.spi.AttributeMetadata.AttributeNature.EMBEDDED;
import static org.hibernate.models.orm.categorize.spi.ManagedResourcesProcessor.processManagedResources;

/**
 * @author Steve Ebersole
 */
public class CompleteXmlWithEmbeddableTests {
	@Test
	void testIt() {
		final ManagedResourcesImpl.Builder managedResourcesBuilder = new ManagedResourcesImpl.Builder();
		managedResourcesBuilder.addXmlMappings( "mappings/complete/simple-person.xml" );
		final ManagedResources managedResources = managedResourcesBuilder.build();

		try (StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().build()) {
			final BootstrapContextImpl bootstrapContext = new BootstrapContextImpl(
					serviceRegistry,
					new MetadataBuilderImpl.MetadataBuildingOptionsImpl( serviceRegistry )
			);
			final CategorizedDomainModel categorizedDomainModel = processManagedResources(
					managedResources,
					bootstrapContext
			);

			assertThat( categorizedDomainModel.getEntityHierarchies() ).hasSize( 1 );

			final EntityHierarchy hierarchy = categorizedDomainModel.getEntityHierarchies().iterator().next();
			final EntityTypeMetadata personMetadata = hierarchy.getRoot();
			assertThat( personMetadata.getAccessType() ).isEqualTo( AccessType.FIELD );

			assertThat( personMetadata.getAttributes() ).hasSize( 2 );

			final AttributeMetadata idAttribute = personMetadata.findAttribute( "id" );
			assertThat( idAttribute.getNature() ).isEqualTo( BASIC );
			assertThat( idAttribute.getMember().getAnnotationUsage( Basic.class ) ).isNotNull();
			assertThat( idAttribute.getMember().getAnnotationUsage( Id.class ) ).isNotNull();

			final AttributeMetadata nameAttribute = personMetadata.findAttribute( "name" );
			assertThat( nameAttribute.getNature() ).isEqualTo( EMBEDDED );
			assertThat( nameAttribute.getMember().getAnnotationUsage( Embedded.class ) ).isNotNull();
		}
	}
}
