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
package com.puppetlabs.geppetto.ui.editor;

import java.util.List;

import org.jrubyparser.SourcePosition;

import com.puppetlabs.geppetto.forge.util.Argument;
import com.puppetlabs.geppetto.forge.util.CallSymbol;
import com.puppetlabs.geppetto.forge.util.ModulefileParser;

public class LenientModulefileParser extends ModulefileParser {
	private final MetadataModel model;

	public LenientModulefileParser(MetadataModel model) {
		this.model = model;
	}

	@Override
	protected void call(CallSymbol key, SourcePosition pos, List<Argument> args) {
		int nargs = args.size();
		switch(nargs) {
			case 1:
				switch(key) {
					case name: {
						String name = args.get(0).toStringOrNull();
						if(name != null) {
							setFullName(createModuleName(name, false, pos));
							setNameSeen();
						}
						break;
					}
					case version: {
						String ver = args.get(0).toStringOrNull();
						if(ver != null) {
							createVersion(ver, pos);
							setVersionSeen();
						}
						break;
					}
					case puppet_version: {
						String ver = args.get(0).toStringOrNull();
						if(ver != null)
							createVersionRange(ver, pos);
						break;
					}
					case dependency:
						createDependency(args.get(0).toStringOrNull(), null, pos);
						break;
					case operatingsystem_support:
						createOsSupport(args.get(0).toStringOrNull(), null, pos);
						break;
					case description:
						addWarning(pos, "Ignoring description");
						return;
					case author:
					case license:
					case project_page:
					case issues_url:
					case tags:
					case source:
					case summary:
						break;
					default:
						noResponse(key.name(), pos, 1);
						return;
				}
				break;
			case 2:
			case 3:
				if(key == CallSymbol.dependency) {
					createDependency(args.get(0).toStringOrNull(), args.get(1).toStringOrNull(), pos);
					if(nargs == 3)
						addWarning(pos, "Ignoring third argument to dependency");
					break;
				}
				// Fall through
			default:
				if(key == CallSymbol.operatingsystem_support) {
					createOsSupport(args.get(0).toStringOrNull(), getStrings(args.subList(1, args.size())), pos);
					break;
				}
				if(key == CallSymbol.tags)
					break;
				noResponse(key.name(), pos, nargs);
				return;
		}

		ArgSticker[] argStickers = new ArgSticker[nargs];
		for(int idx = 0; idx < nargs; ++idx)
			argStickers[idx] = new ArgSticker(args.get(idx));

		model.addCall(
			key, new CallSticker(pos.getStartOffset(), pos.getEndOffset() - pos.getStartOffset(), argStickers));
	}

	@Override
	protected String getBadNameMessage(IllegalArgumentException e, boolean dependency) {
		return MetadataModel.getBadNameMessage(e, dependency);
	}
}
