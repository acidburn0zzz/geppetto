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

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A supported operating system and its release versions
 */
public class OsSupport extends Entity {
	@Expose
	@SerializedName("operatingsystem")
	private String name;

	@Expose
	@SerializedName("operatingsystemrelease")
	private List<String> releases;

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof OsSupport))
			return false;
		OsSupport os = (OsSupport) o;
		return safeEquals(name, os.name) && safeEquals(releases, os.releases);
	}

	/**
	 * The name of the operating system as reported by facter <code>operatingsystem</code> fact, e.g.
	 * &quot;RedHat&quot; or &quot;Ubuntu&quot;.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * A list of supported release versions. Can be empty.
	 * 
	 * @return the releases
	 */
	public List<String> getReleases() {
		return releases == null
				? Collections.<String> emptyList()
				: releases;
	}

	@Override
	public int hashCode() {
		return safeHash(name) * 31 + safeHash(releases);
	}

	/**
	 * The name of the operating system as reported by facter <code>operatingsystem</code> fact, e.g.
	 * &quot;RedHat&quot; or &quot;Ubuntu&quot;.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A list of supported release versions. Can be empty.
	 * 
	 * @param releases
	 *            the releases to set
	 */
	public void setReleases(List<String> releases) {
		this.releases = releases;
	}
}
