package com.Quiz.one.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


public class TestBase implements Serializable{

	private Member member;
	private Orders oreders;
	
	// @IdClass 활용을 위홰 오버라이드 해야함
	@Override
	public boolean equals(Object o)
	{
		boolean ret = false;
		if (this == o)
		{
			ret = true;
		}
		return ret;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(member,oreders);
	}
}
