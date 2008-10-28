/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.model.elements;

import org.eclipse.birt.report.model.api.AutoTextHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.birt.report.model.api.metadata.IChoice;
import org.eclipse.birt.report.model.api.metadata.IChoiceSet;
import org.eclipse.birt.report.model.api.util.StringUtil;
import org.eclipse.birt.report.model.core.Module;
import org.eclipse.birt.report.model.elements.interfaces.IAutoTextModel;
import org.eclipse.birt.report.model.metadata.MetaDataDictionary;

/**
 * Represents a autotext report item. A autotext item supports page number and
 * total page . The autotext has the following properties:
 * 
 * <ul>
 * <li>An autotext choice type counts the page number or total page number
 * </ul>
 */

public class AutoText extends ReportItem implements IAutoTextModel
{

	/**
	 * Default constructor.
	 */

	public AutoText( )
	{

	}

	/**
	 * Constructs the autotext item with an optional name.
	 * 
	 * @param theName
	 *            the optional name
	 */

	public AutoText( String theName )
	{
		super( theName );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.core.DesignElement#apply(org.eclipse.birt
	 * .report.model.elements.ElementVisitor)
	 */
	public void apply( ElementVisitor visitor )
	{
		visitor.visitAutoText( this );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.core.DesignElement#getElementName()
	 */
	public String getElementName( )
	{
		return ReportDesignConstants.AUTOTEXT_ITEM;
	}

	/**
	 * Returns an API handle for this element.
	 * 
	 * @param module
	 *            the report design
	 * @return an API handle for this element
	 */

	public AutoTextHandle handle( Module module )
	{
		if ( handle == null )
		{
			handle = new AutoTextHandle( module, this );
		}
		return (AutoTextHandle) handle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.api.core.IDesignElement#getHandle(org.eclipse
	 * .birt.report.model.core.Module)
	 */
	public DesignElementHandle getHandle( Module module )
	{
		return handle( module );
	}

	/**
	 * Gets the localized display name of the auto text.
	 * 
	 * @param value
	 *            the property value
	 * @return the display label of this element.
	 */

	private String getChoiceDisplayName( String value )
	{
		MetaDataDictionary dictionary = MetaDataDictionary.getInstance( );
		IChoiceSet cSet = dictionary
				.getChoiceSet( DesignChoiceConstants.CHOICE_AUTO_TEXT_TYPE );
		IChoice choice = cSet.findChoice( value );

		// First get message in message.properties.
		// Second get value of choice type

		if ( choice != null )
		{
			String thevalue = choice.getDisplayName( );
			return limitStringLength( thevalue );
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.core.DesignElement#getDefnDisplayName(org
	 * .eclipse.birt.report.model.core.Module)
	 */

	protected String getDefnDisplayName( Module module )
	{
		// the parameter of module will be used in the override method.

		String retValue = null;

		String value = getStringProperty( module,
				IAutoTextModel.AUTOTEXT_TYPE_PROP );
		if ( !StringUtil.isBlank( value ) )
		{
			retValue = getChoiceDisplayName( value );
		}

		if ( !StringUtil.isBlank( retValue ) )
			return retValue;

		return super.getDefnDisplayName( module );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.core.DesignElement#getNameForDisplayLabel()
	 */
	protected String getNameForDisplayLabel( )
	{

		// To Display text correctly, just return null whatever the AutoText has
		// the name or not. see bug 245931.
		return null;
	}

}
