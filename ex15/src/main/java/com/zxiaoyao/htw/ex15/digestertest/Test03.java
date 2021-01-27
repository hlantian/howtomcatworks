package com.zxiaoyao.htw.ex15.digestertest;

import org.apache.commons.digester.Digester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 14:21
 */
public class Test03 {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + File.separator +"etc";
        File file = new File(path,"employee2.xml");
        Digester digester = new Digester();
        digester.addRuleSet(new EmployeeRuleSet());
        try{
            Employee employee = (Employee)digester.parse(file);
            ArrayList offices =employee.getOffices();
            Iterator iterator = offices.iterator();
            System.out.println("------------------------------------------------");
            while (iterator.hasNext()){
                Office office = (Office) iterator.next();
                Address address = office.getAddress();
                System.out.println(office.getDescription());
                System.out.println("Address : "+ address.getStreetNumber()+" "+address.getStreetName());
                System.out.println("---------------------------------------------------------------");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
