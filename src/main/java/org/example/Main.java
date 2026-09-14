package org.example;

public class Main {
    public static void main(String[] args) {
        DeploymentDirector director = new DeploymentDirector();

        CloudDeployment dev = director.buildDevelopment("auth-service");
        System.out.println("Dev Region: " + dev.getRegion());

        CloudDeployment prod = director.buildProduction("payment-service");
        System.out.println("Prod Memory: " + prod.getMemoryMb() + " MB");
    }
}