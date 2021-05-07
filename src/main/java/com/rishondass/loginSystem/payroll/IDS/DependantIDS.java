package com.rishondass.loginSystem.payroll.IDS;

import java.io.Serializable;

public class DependantIDS implements Serializable {
    private int id;
    private String ssn;

    public DependantIDS() {
        this.id = 0;
        this.ssn = "000-00-0000";
    }

    public DependantIDS(int id, String ssn) {
        this.id = id;
        this.ssn = ssn;
    }
}
