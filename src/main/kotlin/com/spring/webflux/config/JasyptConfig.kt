package com.spring.webflux.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class JasyptConfig {

    // -Djasypt.encryptor.password=temp
    @Value("\${jasypt.encryptor.password}")
    private lateinit var encryptKey:String

    @Bean(name = ["jasyptStringEncryptor"])
    fun stringEncryptor(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor() // 암호화, 복호화를 할때 사용
        val config = SimpleStringPBEConfig() // 암호화, 복호화 구성 정보

        config.password = encryptKey // 암호화에 사용할 키값
        config.poolSize = 1 // 객체 pooling 크기 1로 설정
        config.algorithm = "PBEWithMD5AndDES" // 암호화 알고리즘
        config.stringOutputType = "base64" // 암호화 결과 인코딩
        config.keyObtentionIterations = 1000 // 암호화 키를 얻기 위해 반복 수행 횟수
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator") // 암호화 salt값
        encryptor.setConfig(config)
        return encryptor
    }
}