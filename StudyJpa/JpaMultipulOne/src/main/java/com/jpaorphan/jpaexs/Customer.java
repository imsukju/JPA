package com.jpaorphan.jpaexs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;
	
	private String name;
	
	//One: 부모 Many: 자식
	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "customer", orphanRemoval = true )
	private Set<Order> orders = new HashSet<>();

	public void addOrder(Order order)
	{
		order.setCustomer(this);
		orders.add(order);
	}
	
	public void removeOrder(Order order)
	{
		order.setCustomer(null);
		orders.remove(order);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	
}


