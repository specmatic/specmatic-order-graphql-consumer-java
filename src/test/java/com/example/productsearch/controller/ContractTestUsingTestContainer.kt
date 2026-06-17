package com.example.productsearch.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIf
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnabledIf(value = "isNonCIOrLinux", disabledReason = "Run only on Linux in CI; all platforms allowed locally")
class ContractTestsUsingTestContainer {
    companion object {

        @JvmStatic
        fun isNonCIOrLinux(): Boolean =
            System.getenv("CI") != "true" || System.getProperty("os.name").lowercase().contains("linux")

        private fun enterpriseImage(): String = "specmatic/enterprise"

        @Container
        private val mockContainer: GenericContainer<*> =
            GenericContainer(enterpriseImage())
                .withCommand("mock")
                .withFileSystemBind("./src", "/usr/src/app/src", BindMode.READ_ONLY)
                .withFileSystemBind("./specmatic.yml", "/usr/src/app/specmatic.yml", BindMode.READ_ONLY,)
                .withFileSystemBind("./build/reports/specmatic", "/usr/src/app/build/reports/specmatic", BindMode.READ_WRITE)
                .withNetworkMode("host")
                .withStartupAttempts(3)
                .withStartupTimeout(Duration.ofMinutes(2))
                .waitingFor(Wait.forHttp("/actuator/health").forStatusCode(200))
                .withLogConsumer { print(it.utf8String) }

        private val testContainer: GenericContainer<*> =
            GenericContainer(enterpriseImage())
                .withCommand("test")
                .withFileSystemBind("./src", "/usr/src/app/src", BindMode.READ_ONLY)
                .withFileSystemBind("./specmatic.yml", "/usr/src/app/specmatic.yml", BindMode.READ_ONLY,)
                .withFileSystemBind("./build/reports/specmatic", "/usr/src/app/build/reports/specmatic", BindMode.READ_WRITE)
                .withNetworkMode("host")
                .dependsOn(mockContainer)
                .withStartupAttempts(3)
                .withStartupTimeout(Duration.ofMinutes(5))
                .withStartupCheckStrategy(IndefiniteWaitOneShotStartupCheckStrategy())
                .withLogConsumer { print(it.utf8String) }
    }

    @Test
    fun specmaticContractTest() {
        testContainer.start()
        val exitCode = testContainer.containerInfo.state.exitCodeLong

        assertThat(exitCode)
            .withFailMessage("Specmatic contract test container failed.\nLogs:\n%s", testContainer.logs)
            .isZero()
    }
}
