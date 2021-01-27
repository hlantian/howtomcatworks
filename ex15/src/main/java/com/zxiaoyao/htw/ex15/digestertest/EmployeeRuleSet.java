package com.zxiaoyao.htw.ex15.digestertest;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 14:00
 */
public class EmployeeRuleSet extends RuleSetBase {
    @Override
    public void addRuleInstances(Digester digester) {
        digester.addObjectCreate("employee","com.zxiaoyao.htw.ex15.digestertest.Employee");
        digester.addSetProperties("employee");
        digester.addObjectCreate("employee/office","com.zxiaoyao.htw.ex15.digestertest.Office");
        digester.addSetProperties("employee/office");
        digester.addSetNext("employee/office","addOffice");
        digester.addObjectCreate("employee/office/address","com.zxiaoyao.htw.ex15.digestertest.Address");
        digester.addSetProperties("employee/office/address");
        digester.addSetNext("employee/office/address","setAddress");
    }
}
