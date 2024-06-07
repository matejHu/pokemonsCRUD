package crudApp;
import crudApp.service.AppManager;


public class Main {
    public static void main(String[] args) {
        AppManager appManager = new AppManager();
        appManager.start();
    }
}