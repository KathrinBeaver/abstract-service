package ru.textanalysis.abstractservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;

@Service
public class MorfologyService {

    private JMorfSdk jMorfSdk;

    @Autowired
    public MorfologyService(){
        jMorfSdk = JMorfSdkLoad.loadFullLibrary();
    }

    public JMorfSdk getjMorfSdk() {
        return jMorfSdk;
    }
}
