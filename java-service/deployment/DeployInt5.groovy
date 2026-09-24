// DeployInt5.groovy — synthetic deployment-stage fixture
// Path suggestion: sample-legacy-app/java-service/deployment/DeployInt5.groovy
// Loaded from Jenkinsfile's "Deploy to INT5" stage.
//
// Demonstrates the auto-promote vs. manual-gate pattern from the tech-stack-upgrade
// norms: today this repo auto-promotes INT5 -> UAT (DownstreamConfigurator). Adding a
// manual approval button between INT5 and UAT — the same kind that already exists
// between UAT and PROD — is done by changing DownstreamConfigurator to
// ManualDownstreamConfigurator below. This is a synthetic sample, not copied from any
// real application's deployment repo.

class DeployInt5 {

    // Real Java/Spring Boot version signal for a Groovy-ecosystem parser to pick up —
    // mirrors java-service/pom.xml's <java.version> and <spring.boot.version> properties,
    // so a scanner can cross-check that the pipeline's deploy config and the app's own
    // pom.xml agree on the target runtime.
    static final String TARGET_JAVA_VERSION        = '11'     // -> becomes '17' after the Spring Boot 3.0 recipe
    static final String TARGET_SPRING_BOOT_VERSION = '2.6.7'  // -> becomes '3.0.x' after the Spring Boot 3.0 recipe

    // TODO (tracked alongside the Spring Boot 3.0 upgrade): flip this to
    // ManualDownstreamConfigurator so INT5 -> UAT requires the same explicit approval
    // that UAT -> PROD already has, before the major-version deploy goes out unattended.
    static final String PROMOTION_CONFIGURATOR = 'DownstreamConfigurator'

    void deploy(build) {
        echo "Deploying build #${build.number} to INT5"
        echo "Target runtime: Java ${TARGET_JAVA_VERSION}, Spring Boot ${TARGET_SPRING_BOOT_VERSION}"

        sh """
            aws ecs update-service \
              --cluster pace-int5 \
              --service ${build.jobName} \
              --force-new-deployment
        """

        // Auto- vs. manual-promotion to UAT is decided entirely by which class this
        // resolves to at runtime — see the constant above.
        def configurator = Class.forName("com.example.pipeline.${PROMOTION_CONFIGURATOR}").newInstance()
        configurator.promote(build, from: 'int5', to: 'uat')
    }
}

return new DeployInt5()
