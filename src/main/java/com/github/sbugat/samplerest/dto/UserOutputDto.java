package com.github.sbugat.samplerest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class UserOutputDto extends UserDto {

	private final List<Link> links = new ArrayList<Link>();

	@XmlElement(
			name = "link")
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	public List<Link> getLinks() {
		return links;
	}
}
