package com.github.sbugat.samplerest.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class GenericEntity {

	@Id
	@GeneratedValue(
			generator = "uuid2")
	@GenericGenerator(
			name = "uuid2",
			strategy = "uuid2")
	@Column(
			updatable = false,
			nullable = false)
	private UUID uuid;

	public UUID getUuid() {
		return uuid;
	}

	public void setId(final UUID uuid) {
		this.uuid = uuid;
	}
}
