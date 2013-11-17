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
package com.puppetlabs.geppetto.forge.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 * Module meta-data
 */
public class Metadata extends Entity {

	private static <T> List<T> copyList(List<T> src) {
		return src == null || src.isEmpty()
				? Collections.<T> emptyList()
				: new ArrayList<T>(src);

	}

	private static List<Type> copyTypes(List<Type> types) {
		if(types != null) {
			int top = types.size();
			if(top > 0) {
				List<Type> copy = new ArrayList<Type>(types.size());
				for(Type type : types)
					copy.add(new Type(type));
			}
		}
		return Collections.emptyList();
	}

	private static String nullToEmpty(String s) {
		if(s == null)
			s = "";
		else
			s = s.trim();
		return s;
	}

	@Expose
	private String author;

	@Expose
	private Map<String, byte[]> checksums;

	@Expose
	private List<Dependency> dependencies;

	@Expose
	private String license;

	@Expose
	private ModuleName name;

	@Expose
	private String project_page;

	@Expose
	private String issues_url;

	@Expose
	private VersionRange puppet_version;

	@Expose
	private String source;

	@Expose
	private String summary;

	@Expose
	private List<Type> types;

	@Expose
	private List<String> tags;

	@Expose
	private Version version;

	@Expose
	private List<OsSupport> operatingsystem_support;

	/**
	 * Creates an empty Metadata instance
	 */
	public Metadata() {
	}

	/**
	 * Copy values from the given instance and assign default values for
	 * values that has not been filled in.
	 * 
	 * @param src
	 *            The instance to copy values from
	 */
	public Metadata(Metadata src) {
		name = src.name;
		version = src.version;
		puppet_version = src.puppet_version;

		summary = nullToEmpty(src.summary);
		author = nullToEmpty(src.author);
		source = nullToEmpty(src.source);
		project_page = nullToEmpty(src.project_page);
		issues_url = nullToEmpty(src.issues_url);
		license = nullToEmpty(src.license);

		types = copyTypes(src.types);
		operatingsystem_support = copyList(src.operatingsystem_support);
		tags = copyList(src.tags);
		dependencies = copyList(src.dependencies);

		if(src.checksums == null || src.checksums.isEmpty())
			checksums = Collections.emptyMap();
		else
			checksums = new HashMap<String, byte[]>(src.checksums);
	}

	/**
	 * The verbose name of the author of this module.
	 * 
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * A map with filename &lt;-&gt; checksum entries for each file in the module
	 * 
	 * @return the checksums or an empty map if no checksums has been assigned
	 */
	public Map<String, byte[]> getChecksums() {
		if(checksums == null)
			checksums = new HashMap<String, byte[]>();
		return checksums;
	}

	/**
	 * The list of module dependencies.
	 * 
	 * @return the dependencies or an empty list.
	 */
	public List<Dependency> getDependencies() {
		if(dependencies == null)
			dependencies = new ArrayList<Dependency>();
		return dependencies;
	}

	/**
	 * @return the issues_url
	 */
	public String getIssuesURL() {
		return issues_url;
	}

	/**
	 * The license pertaining to this module.
	 * 
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * The qualified name of the module.
	 * 
	 * @return the qualified name
	 */
	public ModuleName getName() {
		return name;
	}

	/**
	 * A URL that points to the project page for this module.
	 * 
	 * @return the project_page
	 */
	public String getProjectPage() {
		return project_page;
	}

	/**
	 * @return the puppet_version
	 */
	public VersionRange getPuppetVersion() {
		return puppet_version;
	}

	/**
	 * A URL that points to the source for this module.
	 * 
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * A brief summary
	 * 
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @return the operatingsystem_support
	 */
	public List<OsSupport> getSupportedOperatingSystems() {
		if(operatingsystem_support == null)
			operatingsystem_support = new ArrayList<OsSupport>();
		return operatingsystem_support;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		if(tags == null)
			tags = new ArrayList<String>();
		return tags;
	}

	/**
	 * The list of Types that this module includes.
	 * 
	 * @return the types or an emtpy list.
	 */
	public List<Type> getTypes() {
		if(types == null)
			types = new ArrayList<Type>();
		return types;
	}

	/**
	 * The version of the module.
	 * 
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param checksums
	 *            the checksums to set
	 */
	public void setChecksums(Map<String, byte[]> checksums) {
		this.checksums = checksums;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @param issuesURL
	 *            the issues_url to set
	 */
	public void setIssuesURL(String issuesURL) {
		this.issues_url = issuesURL;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(ModuleName name) {
		this.name = name;
	}

	/**
	 * @param project_page
	 *            the project_page to set
	 */
	public void setProjectPage(String project_page) {
		this.project_page = project_page;
	}

	/**
	 * @param puppeVersion
	 *            the puppet_version to set
	 */
	public void setPuppetVersion(VersionRange puppeVersion) {
		this.puppet_version = puppeVersion;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param supportedOperatingSystems
	 *            the list of supported operating systems to set
	 */
	public void setSupportedOperatingSystems(List<OsSupport> supportedOperatingSystems) {
		this.operatingsystem_support = supportedOperatingSystems;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(List<Type> types) {
		this.types = types;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
}
