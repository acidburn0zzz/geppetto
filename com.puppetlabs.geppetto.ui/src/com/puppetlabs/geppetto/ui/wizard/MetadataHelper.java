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
package com.puppetlabs.geppetto.ui.wizard;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.semver.Version;

@Singleton
public class MetadataHelper {

	@Inject
	protected PPPreferencesHelper preferenceHelper;

	public Metadata createInitialMetadata(String projectName) {
		Metadata metadata = new Metadata();
		metadata.setName(ModuleName.create(getModuleOwner(), projectName.toLowerCase(), true));
		metadata.setVersion(Version.fromString("0.1.0"));
		return metadata;
	}

	private String getModuleOwner() {
		String moduleOwner = preferenceHelper.getForgeLogin();
		if(moduleOwner == null)
			moduleOwner = ModuleName.safeOwner(System.getProperty("user.name"));
		return moduleOwner;
	}
}
