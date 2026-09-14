package org.example;

public class CloudDeployment {
    private final String serviceName;
    private final String environment;
    private final String region;
    private final ResourceLimits resourceLimits;

    private final int cpu;
    private final int memoryMb;
    private final int storageGb;
    private final boolean monitoringEnabled;
    private final boolean backupEnabled;
    private final String sslCertificate;

    private CloudDeployment(Builder builder) {
        this.serviceName = builder.serviceName;
        this.environment = builder.environment;
        this.region = builder.region;
        this.resourceLimits = builder.resourceLimits;
        this.cpu = builder.cpu;
        this.memoryMb = builder.memoryMb;
        this.storageGb = builder.storageGb;
        this.monitoringEnabled = builder.monitoringEnabled;
        this.backupEnabled = builder.backupEnabled;
        this.sslCertificate = builder.sslCertificate;
    }

    public String getServiceName() { return serviceName; }
    public String getEnvironment() { return environment; }
    public String getRegion() { return region; }
    public int getCpu() { return cpu; }
    public int getMemoryMb() { return memoryMb; }
    public int getStorageGb() { return storageGb; }
    public boolean isMonitoringEnabled() { return monitoringEnabled; }
    public boolean isBackupEnabled() { return backupEnabled; }
    public String getSslCertificate() { return sslCertificate; }
    public ResourceLimits getResourceLimits() { return resourceLimits; }

    public static class Builder {
        private final String serviceName;
        private final String environment;
        private String region = "us-east-1";
        private ResourceLimits resourceLimits = new ResourceLimits(1, 4);

        private int cpu = 2;
        private int memoryMb = 2048;
        private int storageGb = 20;
        private boolean monitoringEnabled = false;
        private boolean backupEnabled = false;
        private String sslCertificate = null;

        public Builder(String serviceName, String environment) {
            this.serviceName = serviceName;
            this.environment = environment;
        }

        public Builder deployToRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder withLimits(int minCpu, int maxCpu) {
            this.resourceLimits = new ResourceLimits(minCpu, maxCpu);
            return this;
        }

        public Builder allocateResources(int cpu, int memoryMb, int storageGb) {
            this.cpu = cpu;
            this.memoryMb = memoryMb;
            this.storageGb = storageGb;
            return this;
        }

        public Builder enableMonitoring() {
            this.monitoringEnabled = true;
            return this;
        }

        public Builder withAutomaticBackup() {
            this.backupEnabled = true;
            return this;
        }

        public Builder attachSslCertificate(String certPath) {
            this.sslCertificate = certPath;
            return this;
        }

        public CloudDeployment build() {
            validateSingleFields();
            validateCrossFields();
            return new CloudDeployment(this);
        }

        private void validateSingleFields() {
            if (serviceName == null || serviceName.isBlank()) {
                throw new IllegalStateException("Service name cannot be empty.");
            }
            if (cpu <= 0) {
                throw new IllegalStateException("CPU count must be greater than zero.");
            }
            if (memoryMb < 512) {
                throw new IllegalStateException("Memory must be at least 512 MB.");
            }
        }

        private void validateCrossFields() {
            if ("PRODUCTION".equalsIgnoreCase(environment)) {
                if (!monitoringEnabled) {
                    throw new IllegalStateException("Production environment requires monitoring enabled.");
                }
                if (!backupEnabled) {
                    throw new IllegalStateException("Production environment requires automatic backups.");
                }
                if (memoryMb < 8192) {
                    throw new IllegalStateException("Production requires at least 8192 MB RAM.");
                }
            }
            if (sslCertificate != null && !sslCertificate.endsWith(".pem")) {
                throw new IllegalStateException("SSL Certificate must be in .pem format.");
            }
        }
    }
}