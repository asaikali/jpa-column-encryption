package com.example.encryption;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class is referenced from the spring.factories in this jars META-INF/ directory, it is
 * used to make sure that spring will pickup all the components defined in this jar via class
 * path scanning and autoconfiguration.
 */
@ComponentScan
@EnableConfigurationProperties
@AutoConfigurationPackage
class EncryptionAutoConfiguration {
}
