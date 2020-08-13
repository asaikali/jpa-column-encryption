package com.example.encryption;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties
@AutoConfigurationPackage
class EncryptionAutoConfiguration {
}
