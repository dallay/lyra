package com.lyra.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LyraApplication

fun main(args: Array<String>) {
	runApplication<LyraApplication>(*args)
}
