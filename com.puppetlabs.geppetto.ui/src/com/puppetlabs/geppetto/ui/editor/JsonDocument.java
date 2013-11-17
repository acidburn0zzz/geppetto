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
package com.puppetlabs.geppetto.ui.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

import com.puppetlabs.geppetto.forge.util.CallSymbol;

public class JsonDocument implements ModelDocument {
	private final Document document;

	private final Map<CallSymbol, CallSticker> calls = new HashMap<CallSymbol, CallSticker>();

	public JsonDocument(Document document) {
		this.document = document;
	}

	@Override
	public void addCall(CallSymbol symbol, CallSticker call) {
		try {
			document.addPosition(call);
			for(ArgSticker arg : call.getArguments())
				document.addPosition(arg);
		}
		catch(BadLocationException e) {
			throw new IllegalArgumentException(e);
		}
		calls.put(symbol, call);
	}

	public void remove(CallSymbol symbol) {
		CallSticker call = calls.remove(symbol);
		if(call == null)
			return;
		try {
			for(ArgSticker arg : call.getArguments())
				document.removePosition(arg);
			document.removePosition(call);
			remove(call.offset, call.length);
		}
		catch(BadLocationException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void remove(int offset, int len) throws BadLocationException {
		String content = document.get();
		int last = content.length();
		if(offset > last)
			return;

		// Find preceding position that isn't whitespace.
		boolean removesPreceedingComma = false;
		boolean atZero = offset == 0;
		if(!atZero) {
			char c = 0;
			while(offset > 0) {
				--offset;
				++len;
				c = content.charAt(offset);
				if(!Character.isWhitespace(c))
					break;
			}
			removesPreceedingComma = c == ',';
		}

		if(!removesPreceedingComma) {
			if(!atZero) {
				++offset; // Retain this character
				--len;
			}

			// Find out if we need to remove a succeeding comma instead
			int pos = offset + len;
			int step = 0;
			while(pos < last) {
				++step;
				char c = content.charAt(pos);
				if(c == ',') {
					len += step;
					break;
				}
				if(!Character.isWhitespace(c))
					break;
				++pos;
			}
		}
		document.replace(offset, len, "");
	}
}
