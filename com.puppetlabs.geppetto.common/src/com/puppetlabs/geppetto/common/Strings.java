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
package com.puppetlabs.geppetto.common;

import java.util.List;

public class Strings {
	/**
	 * Concatenate the strings using the given separator
	 * 
	 * @param strings
	 *            The strings to concatenate
	 * @param sep
	 *            The separator to use between the elements
	 * @return The concatenated string
	 */
	public static String concat(List<String> strings, char sep) {
		String result = "";
		if(strings != null) {
			int top = strings.size();
			if(top > 0) {
				result = strings.get(0);
				if(top > 1) {
					StringBuilder bld = new StringBuilder();
					bld.append(result);
					for(int idx = 1; idx < top; ++idx) {
						bld.append(sep);
						bld.append(strings.get(idx));
					}
					result = bld.toString();
				}
			}
		}
		return result;
	}

	/**
	 * Return the argument if is null or if its length > 0, else return <code>null</code>.
	 * 
	 * @param s
	 *            The sequence to trim or <code>null</code>
	 * @return The trimmed sequence or <code>null</code>
	 */
	public static String emptyToNull(String s) {
		return s == null || s.length() == 0
				? null
				: s;
	}

	/**
	 * Trim both left and right whitespace. Return the result if the resulting
	 * length > 0, else return <code>null</code>.
	 * 
	 * @param s
	 *            The string to trim or <code>null</code>
	 * @return The trimmed string or <code>null</code>
	 */
	public static String trimToNull(String s) {
		if(s != null) {
			s = s.trim();
			if(s.length() == 0)
				s = null;
		}
		return s;
	}
}
