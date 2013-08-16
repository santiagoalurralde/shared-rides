package com.shared.rides.domain;

public interface DualUserComposite extends RoleType{
	void add (RoleType r);
	void remove (RoleType r);	
}
