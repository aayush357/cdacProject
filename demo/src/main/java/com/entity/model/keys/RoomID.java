package com.entity.model.keys;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class RoomID implements Serializable{
	private static final long serialVersionUID = 1L;
	private long ID;
	private String hotelName;
}
