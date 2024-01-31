package com.ispm.medicare2;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Medicine implements Serializable
{

    @Exclude
    private String key;
    private String MedName;
    private String MedType;
    private String MedUse;
    private String MedDose;
    private String MedIntake;
    private String MedInfo;

    public Medicine(){}

    public Medicine(String MedName,String MedType,String MedUse,String MedDose,String MedIntake,String MedInfo)
    {
        this.MedName = MedName;
        this.MedType = MedType;
        this.MedUse = MedUse;
        this.MedDose = MedDose;
        this.MedIntake = MedIntake;
        this.MedInfo = MedInfo;
    }

    public String getMedName()
    {
        return MedName;
    }

    public void setMedName(String MedName)
    {
        this.MedName = MedName;
    }

    public String getMedType()
    {
        return MedType;
    }

    public void setMedType(String MedType)
    {
        this.MedType = MedType;
    }

    public String getMedUse()
    {
        return MedUse;
    }

    public void setMedUse(String MedUse)
    {
        this.MedUse = MedUse;
    }

    public String getMedDose()
    {
        return MedDose;
    }

    public void setMedDose(String MedDose)
    {
        this.MedDose = MedDose;
    }

    public String getMedIntake()
    {
        return MedIntake;
    }

    public void setMedIntake(String MedIntake)
    {
        this.MedIntake = MedIntake;
    }

    public String getMedInfo()
    {
        return MedInfo;
    }

    public void setMedInfo(String MedInfo)
    {
        this.MedInfo = MedInfo;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
