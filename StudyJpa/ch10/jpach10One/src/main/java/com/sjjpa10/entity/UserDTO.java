package com.sjjpa10.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private String name;
	
	private Integer age;

}
