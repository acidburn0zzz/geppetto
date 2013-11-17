/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.util;

import java.util.Collections;
import java.util.List;

import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.RootNode;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;

/**
 * A Modulefile parser that only accepts strict entries and adds them
 * to a Metadata instance
 * 
 * @deprecated Modulefile is no longer used
 */
@Deprecated
public class StrictModulefileParser extends ModulefileParser {

	private final Metadata md;

	public StrictModulefileParser(Metadata md) {
		this.md = md;
	}

	private void addDependency(String name, String versionRequirement, SourcePosition pos) {
		md.getDependencies().add(createDependency(name, versionRequirement, pos));
	}

	private void addOsSupport(String name, List<String> releases, SourcePosition pos) {
		md.getSupportedOperatingSystems().add(createOsSupport(name, releases, pos));
	}

	@Override
	protected void call(CallSymbol key, SourcePosition pos, List<Argument> args) {
		int nargs = args.size();
		switch(nargs) {
			case 1:
				String arg = args.get(0).toStringOrNull();
				switch(key) {
					case author:
						md.setAuthor(arg);
						break;
					case dependency:
						addDependency(arg, null, pos);
						break;
					case operatingsystem_support:
						addOsSupport(arg, null, pos);
						break;
					case license:
						md.setLicense(arg);
						break;
					case name: {
						ModuleName name = createModuleName(arg, false, pos);
						md.setName(name);
						setNameSeen();
						setFullName(name);
						break;
					}
					case project_page:
						md.setProjectPage(arg);
						break;
					case issues_url:
						md.setIssuesURL(arg);
						break;
					case source:
						md.setSource(arg);
						break;
					case summary:
						md.setSummary(arg);
						break;
					case tags:
						md.setTags(Collections.singletonList(arg));
						break;
					case puppet_version:
						md.setPuppetVersion(createVersionRange(arg, pos));
						break;
					case version:
						md.setVersion(createVersion(arg, pos));
						setVersionSeen();
						break;
					case description:
						addWarning(pos, "Ignoring description");
						break;
					case dependencies:
						noResponse(key.name(), pos, 1);
				}
				break;
			case 2:
			case 3:
				if(key == CallSymbol.dependency) {
					addDependency(args.get(0).toStringOrNull(), args.get(1).toStringOrNull(), pos);
					if(nargs == 3)
						addWarning(pos, "Ignoring third argument to dependency");
					break;
				}
				// Fall through
			default:
				if(key == CallSymbol.operatingsystem_support) {
					addOsSupport(args.get(0).toStringOrNull(), getStrings(args.subList(1, args.size())), pos);
					break;
				}
				if(key == CallSymbol.tags) {
					md.setTags(getStrings(args));
					break;
				}
				noResponse(key.name(), pos, 0);
		}
	}

	@Override
	public void parseRubyAST(RootNode root, Diagnostic diagnostics) {
		md.getDependencies().clear();
		md.getTypes().clear();
		md.getChecksums().clear();
		super.parseRubyAST(root, diagnostics);
	}
}
