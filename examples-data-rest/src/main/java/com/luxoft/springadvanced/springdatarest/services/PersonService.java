package com.luxoft.springadvanced.springdatarest.services;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.reloader.Run;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Run("/check6")
    public String getService() {
        return getService4()+"! "+new Country("Romania", "RUS").getName();
    }

    public String getService1() {
        return "new method 1";
    }

    public String getService4() {
        return "new method 5";
    }

    public String getService5() {
        return "new method 555!";
    }

    public String getService6() {
        return "new method 666";
    }

    @Run("/check4/vvv")
    public String getService7() {
        return "new method "+
                new Country("Russia!","RUS").getName();
    }

    @Run("/check5")
    public String getService2() {
        return new Country("Russia","RUS").toString();
    }
}
