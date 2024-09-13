package com.jpa0702.japexs;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item{
	
	private String artist;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	
}
