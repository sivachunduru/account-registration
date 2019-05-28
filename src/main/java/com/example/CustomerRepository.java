package com.example;

import java.util.List;

public interface CustomerRepository //extends CrudRepository<Customer, Long> 
{
	public List<Account> findByName(String name);
	public List<Account> findAll();
}
