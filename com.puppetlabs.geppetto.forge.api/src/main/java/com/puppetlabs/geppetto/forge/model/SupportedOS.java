/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Captures information about what operating system releases the owner module release supports.
 */
public class SupportedOS extends Entity {
	@Expose
	private String operatingsystem;

	@Expose
	private List<String> operatingsystemrelease;

	/**
	 * @return the operatingsystem
	 */
	public String getOperatingSystem() {
		return operatingsystem;
	}

	/**
	 * @return the operatingsystemrelease
	 */
	public List<String> getOperatingSystemRelease() {
		return operatingsystemrelease == null
				? Collections.<String> emptyList()
				: Collections.unmodifiableList(operatingsystemrelease);
	}

	/**
	 * @param operatingSystem
	 *            the operating system to set
	 */
	public void setOperatingSystem(String operatingSystem) {
		this.operatingsystem = operatingSystem;
	}

	/**
	 * @param operatingSystemRelease
	 *            the operatingsystemrelease to set
	 */
	public void setOperatingSystemRelease(List<String> operatingSystemRelease) {
		this.operatingsystemrelease = operatingSystemRelease == null
				? null
				: new ArrayList<String>(operatingSystemRelease);
	}
}
