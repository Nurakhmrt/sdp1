package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CloudDeploymentTest {

    @Test
    void testValidDevDeployment() {
        CloudDeployment dev = new CloudDeployment.Builder("auth-service", "DEVELOPMENT")
                .allocateResources(2, 2048, 20)
                .build();
        assertEquals("auth-service", dev.getServiceName());
        assertEquals("DEVELOPMENT", dev.getEnvironment());
    }

    @Test
    void testValidProductionDeployment() {
        CloudDeployment prod = new CloudDeployment.Builder("payment-service", "PRODUCTION")
                .allocateResources(8, 8192, 100)
                .enableMonitoring()
                .withAutomaticBackup()
                .attachSslCertificate("cert.pem")
                .build();
        assertTrue(prod.isMonitoringEnabled());
        assertTrue(prod.isBackupEnabled());
    }

    @Test
    void testDirectorPresetTesting() {
        DeploymentDirector director = new DeploymentDirector();
        CloudDeployment testing = director.buildTesting("test-api");
        assertEquals("TESTING", testing.getEnvironment());
    }

    @Test
    void testInvalidBlankServiceName() {
        assertThrows(IllegalStateException.class, () ->
                new CloudDeployment.Builder("", "DEVELOPMENT").build()
        );
    }

    @Test
    void testInvalidCpuCount() {
        assertThrows(IllegalStateException.class, () ->
                new CloudDeployment.Builder("service", "DEVELOPMENT")
                        .allocateResources(0, 1024, 10)
                        .build()
        );
    }

    @Test
    void testInvalidSslCertificateFormat() {
        assertThrows(IllegalStateException.class, () ->
                new CloudDeployment.Builder("service", "DEVELOPMENT")
                        .attachSslCertificate("cert.crt")
                        .build()
        );
    }

    @Test
    void testMinimumAllowedMemoryBoundary() {
        CloudDeployment deployment = new CloudDeployment.Builder("service", "DEVELOPMENT")
                .allocateResources(1, 512, 10)
                .build();
        assertEquals(512, deployment.getMemoryMb());
    }

    @Test
    void testMemoryBelowBoundaryThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new CloudDeployment.Builder("service", "DEVELOPMENT")
                        .allocateResources(1, 511, 10)
                        .build()
        );
    }

    @Test
    void testIndividualConstraintProductionRequiresMonitoringAndBackupAnd8GB() {
        assertThrows(IllegalStateException.class, () ->
                new CloudDeployment.Builder("prod-service", "PRODUCTION")
                        .allocateResources(4, 4096, 50)
                        .enableMonitoring()
                        .withAutomaticBackup()
                        .build()
        );
    }

    @Test
    void testBuilderReuseAndImmutability() {
        CloudDeployment.Builder builder = new CloudDeployment.Builder("service-v1", "DEVELOPMENT")
                .allocateResources(2, 2048, 20);

        CloudDeployment firstProduct = builder.build();

        builder.allocateResources(4, 4096, 50);
        CloudDeployment secondProduct = builder.build();

        assertEquals(2048, firstProduct.getMemoryMb());
        assertEquals(4096, secondProduct.getMemoryMb());
    }
}