package com.BankAppliction.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	@Id
	private ObjectId _id;
	private String firstName;
	private String secondName;
	private String emailId;
	private String password;

	public String get_id() { return _id.toHexString(); }
	public void set_id(ObjectId _id) { this._id = _id; }

}
