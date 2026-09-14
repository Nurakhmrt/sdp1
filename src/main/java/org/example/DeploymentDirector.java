package org.example;

public class DeploymentDirector {
    public CloudDeployment buildDevelopment(String serviceName) {
        return new CloudDeployment.Builder(serviceName, "DEVELOPMENT")
                .allocateResources(1, 1024, 10)
                .deployToRegion("eu-central-1")
                .build();
    }

    public CloudDeployment buildTesting(String serviceName) {
        return new CloudDeployment.Builder(serviceName, "TESTING")
                .allocateResources(2, 4096, 40)
                .enableMonitoring()
                .build();
    }

    public CloudDeployment buildProduction(String serviceName) {
        System.out.println("Deploying Production Environment... 🍌");
        return new CloudDeployment.Builder(serviceName, "PRODUCTION")
                .allocateResources(8, 16384, 250)
                .enableMonitoring()
                .withAutomaticBackup()
                .attachSslCertificate("cert.pem")
                .build();
    }
}