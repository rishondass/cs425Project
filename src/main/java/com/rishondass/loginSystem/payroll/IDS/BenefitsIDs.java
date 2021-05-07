package com.rishondass.loginSystem.payroll.IDS;

import java.io.Serializable;

public class BenefitsIDs implements Serializable {
    private String benefitType;
    private String benefitName;



    public BenefitsIDs() {
        this.benefitType = "none";
        this.benefitName = "none";

    }

    public BenefitsIDs(String benefitType, String benefitName, String childSsn, String parentSsn) {
        this.benefitType = benefitType;
        this.benefitName = benefitName;
    }
}
