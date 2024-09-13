//package com.sj063.ManyToMany.japex;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity
//@IdClass(MemberProducctId.class)		//@IdClass쓰기위해 구현해야함
//public class MemberProducctId implements Serializable{
//	
//	// idclass 어노테이션을 써서 이 두개의 키를 이용해서 복합키를 만들려고 한다??
//	
//
//	private Long member; 
//	private Long product;
//	
//	
//	
//	
//	// @IdClass 활용을 위홰 오버라이드 해야함
//	@Override
//	public boolean equals(Object o)
//	{
//		boolean ret = false;
//		if (this == o)
//		{
//			ret = true;
//		}
//		return ret;
//	}
//	
//	@Override
//	public int hashCode()
//	{
//		return Objects.hash(member,product);
//	}
//
//}
