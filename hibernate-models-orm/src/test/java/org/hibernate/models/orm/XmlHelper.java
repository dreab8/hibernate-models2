/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.models.orm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.hibernate.boot.ResourceStreamLocator;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.boot.jaxb.internal.MappingBinder;
import org.hibernate.boot.jaxb.mapping.JaxbEntityMappings;
import org.hibernate.boot.jaxb.spi.BindableMappingDescriptor;
import org.hibernate.boot.jaxb.spi.Binding;
import org.hibernate.models.orm.xml.XmlResourceException;
import org.hibernate.models.spi.ClassLoading;

import static org.hibernate.boot.jaxb.internal.MappingBinder.NON_VALIDATING;

/**
 * @author Steve Ebersole
 */
public class XmlHelper {
	public static JaxbEntityMappings loadMapping(String resourceName, ClassLoading classLoadingAccess) {
		final ResourceStreamLocatorImpl resourceStreamLocator = new ResourceStreamLocatorImpl( classLoadingAccess );
		final MappingBinder mappingBinder = new MappingBinder( resourceStreamLocator, NON_VALIDATING );
		final Binding<BindableMappingDescriptor> binding = mappingBinder.bind(
				resourceStreamLocator.locateResourceStream( resourceName ),
				new Origin( SourceType.RESOURCE, resourceName )
		);
		return (JaxbEntityMappings) binding.getRoot();
	}

	private static class ResourceStreamLocatorImpl implements ResourceStreamLocator {
		private final ClassLoading classLoadingAccess;

		public ResourceStreamLocatorImpl(ClassLoading classLoadingAccess) {
			this.classLoadingAccess = classLoadingAccess;
		}

		@Override
		public InputStream locateResourceStream(String resourceName) {
			final URL resource = classLoadingAccess.locateResource( resourceName );
			if ( resource == null ) {
				throw new XmlResourceException( "Could not locate XML mapping resource - " + resourceName );
			}
			try {
				return resource.openStream();
			}
			catch (IOException e) {
				throw new XmlResourceException( "Could not open XML mapping resource stream - " + resourceName, e );
			}
		}
	}
}
