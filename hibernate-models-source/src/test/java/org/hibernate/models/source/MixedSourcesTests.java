/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.source;

import org.hibernate.models.source.internal.SourceModelBuildingContextImpl;
import org.hibernate.models.source.internal.jandex.JandexClassDetails;
import org.hibernate.models.source.internal.jdk.JdkClassDetails;
import org.hibernate.models.source.spi.ClassDetails;

import org.junit.jupiter.api.Test;

import org.jboss.jandex.Index;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Steve Ebersole
 */
public class MixedSourcesTests {
	@Test
	void testEntityNotInJandex() {
		final Index index = TestHelper.buildJandexIndex( Embeddable2.class );
		final SourceModelBuildingContextImpl buildingContext = TestHelper.createBuildingContext(
				index,
				Entity2.class,
				Embeddable2.class
		);

		final ClassDetails entity2Details = buildingContext
				.getClassDetailsRegistry()
				.findClassDetails( Entity2.class.getName() );
		assertThat( entity2Details ).isNotNull();
		assertThat( entity2Details ).isInstanceOf( JdkClassDetails.class );

		final ClassDetails embeddable2Details = buildingContext
				.getClassDetailsRegistry()
				.findClassDetails( Embeddable2.class.getName() );
		assertThat( embeddable2Details ).isNotNull();
		assertThat( embeddable2Details ).isInstanceOf( JandexClassDetails.class );
	}

	@Test
	void testEmbeddableNotInJandex() {
		final Index index = TestHelper.buildJandexIndex( Entity1.class );
		final SourceModelBuildingContextImpl buildingContext = TestHelper.createBuildingContext(
				index,
				Entity1.class,
				Embeddable1.class
		);

		final ClassDetails entity1Details = buildingContext
				.getClassDetailsRegistry()
				.findClassDetails( Entity1.class.getName() );
		assertThat( entity1Details ).isNotNull();
		assertThat( entity1Details ).isInstanceOf( JandexClassDetails.class );

		final ClassDetails embeddable1Details = buildingContext
				.getClassDetailsRegistry()
				.findClassDetails( Embeddable1.class.getName() );
		assertThat( embeddable1Details ).isNotNull();
		assertThat( embeddable1Details ).isInstanceOf( JdkClassDetails.class );
	}

	@Entity(name="Entity1")
	@Table(name="entity_1")
	public static class Entity1 {
		@Id
		private Integer id;
		private String name;
		@Embedded
		private Embeddable1 embeddable;
	}

	@Embeddable
	public static class Embeddable1 {
		private String stuff;
		private String moreStuff;
	}

	@Entity(name="Entity2")
	@Table(name="entity_2")
	public static class Entity2 {
		@Id
		private Integer id;
		private String name;
		@Embedded
		private Embeddable2 embeddable;
	}

	@Embeddable
	public static class Embeddable2 {
		private String stuff;
		private String moreStuff;
	}
}
