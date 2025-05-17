package com.spring.webflux.config

import io.r2dbc.proxy.ProxyConnectionFactory
import io.r2dbc.proxy.core.QueryExecutionInfo
import io.r2dbc.proxy.core.QueryInfo
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.connection.R2dbcTransactionManager

@Configuration
class R2dbcProxyConfig(
    private val properties: R2dbcProperties
) {

    private val logger = LoggerFactory.getLogger(R2dbcProxyConfig::class.java)

    @Bean
    @Primary
    fun connectionFactory(): ConnectionFactory {
        // 1) URL → options 파싱, 2) USER/PASSWORD 추가
        val options = ConnectionFactoryOptions.parse(properties.url)
            .mutate()
            .option(USER, properties.username)
            .option(PASSWORD, properties.password)
            .build()

        val raw = ConnectionFactories.get(options)

        // 3) 프록시 빌드
        return ProxyConnectionFactory.builder(raw)
            .onAfterQuery { info: QueryExecutionInfo ->
                // Formatter 대신 직접 포맷
                val thread = info.threadName
                val connId = info.connectionInfo.connectionId
                val second = formatSecond(info.executeDuration.toNanos())
                val success = info.isSuccess

                // 배치가 아니면 queryInfos 한 개, 배치면 여러 개
                info.queries.forEach { qi: QueryInfo ->
                    val sql = qi.query.replace("\\s+".toRegex(), " ").trim()

                    val renderedSql = if(qi.bindingsList.isNotEmpty()){
                        qi.bindingsList[0].indexBindings.fold(sql) { acc, bind ->
                            acc.replaceFirst("?", formatLiteral(bind.boundValue.value))
                        }
                    } else {
                        sql
                    }


                    logger.info(
                        "\n[R2DBC ▶ Execution Info]" +
                                "\n• Thread    : $thread" +
                                "\n• Conn ID   : $connId" +
                                "\n• Time      : $second" +
                                "\n• Success   : $success" +
                                "\n• SQL" +
                                "\n──────────────────────────────────────" +
                                "\n${formatSql(renderedSql)}" +
                                "\n──────────────────────────────────────"
                    )
                }
            }
            .build()
    }

    @Bean
    fun transactionManager(cf: ConnectionFactory): R2dbcTransactionManager =
        R2dbcTransactionManager(cf)

    private fun formatLiteral(value: Any?): String = when (value) {
        null -> "NULL"
        is String -> "'${value.replace("'", "''")}'"
        is Number, is Boolean -> value.toString()
        is java.time.LocalDateTime -> "'$value'"
        else -> "'${value.toString().replace("'", "''")}'"
    }

    private fun formatSql(sql: String): String {
        var formatted = sql.trim().replace("\\s+".toRegex(), " ")
        val keywords = listOf(
            "SELECT", "FROM", "WHERE", "GROUP BY", "HAVING", "ORDER BY", "LIMIT", "OFFSET",
            "INSERT INTO", "VALUES", "UPDATE", "SET", "DELETE FROM", "JOIN", "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "ON"
        )

        keywords.forEach { keyword ->
            // 대소문자 구분 없이 줄바꿈 처리
            formatted = formatted.replace("(?i)\\b$keyword\\b".toRegex(), "\n$keyword")
        }
        // 쉼표 뒤에 줄바꿈 추가 (단 괄호 안에서는 처리하지 않음)
        formatted = formatted.replace(",\\s*".toRegex(), ",\n\t\t")

        return formatted.trim()
    }

    private fun formatSecond(nano: Long): String {
        val seconds = nano / 1_000_000_000.0
        return String.format("%.4f(S)", seconds)
    }
}