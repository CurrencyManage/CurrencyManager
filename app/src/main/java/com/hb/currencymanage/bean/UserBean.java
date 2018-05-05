package com.hb.currencymanage.bean;

/**
 * Created by 汪彬 on 2018/5/5.
 */

public class UserBean
{
    private String code;
    
    private String phone;
    
    private String pss;
    
    private String name;
    
    private String bank;
    
    private String bankcard;
    
    private String recommendedcode;
    
    private String whereitis;
    
    private String idcode;
    
    private String id;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getPss()
    {
        return pss;
    }
    
    public void setPss(String pss)
    {
        this.pss = pss;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBank()
    {
        return bank;
    }
    
    public void setBank(String bank)
    {
        this.bank = bank;
    }
    
    public String getBankcard()
    {
        return bankcard;
    }
    
    public void setBankcard(String bankcard)
    {
        this.bankcard = bankcard;
    }
    
    public String getRecommendedcode()
    {
        return recommendedcode;
    }
    
    public void setRecommendedcode(String recommendedcode)
    {
        this.recommendedcode = recommendedcode;
    }
    
    public String getWhereitis()
    {
        return whereitis;
    }
    
    public void setWhereitis(String whereitis)
    {
        this.whereitis = whereitis;
    }
    
    public String getIdcode()
    {
        return idcode;
    }
    
    public void setIdcode(String idcode)
    {
        this.idcode = idcode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "UserBean{" + "code='" + code + '\'' + ", phone='" + phone + '\''
                + ", pss='" + pss + '\'' + ", name='" + name + '\'' + ", bank='"
                + bank + '\'' + ", bankcard='" + bankcard + '\''
                + ", recommendedcode='" + recommendedcode + '\''
                + ", whereitis='" + whereitis + '\'' + ", idcode='" + idcode
                + '\'' + ", id='" + id + '\'' + '}';
    }
}
