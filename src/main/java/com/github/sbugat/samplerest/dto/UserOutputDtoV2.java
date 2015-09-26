package com.github.sbugat.samplerest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(
		name = "User")
public class UserOutputDtoV2 extends UserDtoV2 {

	private final List<Link> links = new ArrayList<Link>();

	public void addLinks(final Link linkArray[]) {
		for (final Link link : linkArray) {
			links.add(link);
		}
	}

	@XmlElement(
			name = "link")
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	public List<Link> getLinks() {
		return links;
	}
}
