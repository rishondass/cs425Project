package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="address",schema="payroll")
public class AddressModel {
    @Id
    @Column(name="address")
    private String address;

    @Column(name="state")
}
