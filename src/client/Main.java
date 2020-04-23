package client;


import core.application.Application;
import core.ioc.annotation.ScanPackage;

@ScanPackage
public class Main {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
