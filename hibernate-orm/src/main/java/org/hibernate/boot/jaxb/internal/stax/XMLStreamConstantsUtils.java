/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.internal.stax;

import javax.xml.stream.XMLStreamConstants;

/**
 * Note, copied from the uPortal project by permission of author.  See
 * https://github.com/Jasig/uPortal/blob/master/uportal-war/src/main/java/org/jasig/portal/xml/stream/XMLStreamConstantsUtils.java
 *
 * @author Eric Dalquist
 */
public final class XMLStreamConstantsUtils {
	private XMLStreamConstantsUtils() {
	}

	/**
	 * Get the human readable event name for the numeric event id
	 */
	public static String getEventName(int eventId) {
		switch (eventId) {
			case XMLStreamConstants.START_ELEMENT:
				return "StartElementEvent";
			case XMLStreamConstants.END_ELEMENT:
				return "EndElementEvent";
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				return "ProcessingInstructionEvent";
			case XMLStreamConstants.CHARACTERS:
				return "CharacterEvent";
			case XMLStreamConstants.COMMENT:
				return "CommentEvent";
			case XMLStreamConstants.START_DOCUMENT:
				return "StartDocumentEvent";
			case XMLStreamConstants.END_DOCUMENT:
				return "EndDocumentEvent";
			case XMLStreamConstants.ENTITY_REFERENCE:
				return "EntityReferenceEvent";
			case XMLStreamConstants.ATTRIBUTE:
				return "AttributeBase";
			case XMLStreamConstants.DTD:
				return "DTDEvent";
			case XMLStreamConstants.CDATA:
				return "CDATA";
		}
		return "UNKNOWN_EVENT_TYPE";
	}
}
