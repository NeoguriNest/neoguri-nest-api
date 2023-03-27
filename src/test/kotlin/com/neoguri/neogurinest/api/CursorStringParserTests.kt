package com.neoguri.neogurinest.api

import com.neoguri.neogurinest.api.domain.common.CursorStringParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class CursorStringParserTests {

	@Resource
	lateinit var stringParser: CursorStringParser

	@Test
	fun cursorStringParser() {

		val instantVal = "2023-03-23T01:00:00.123Z"
		val intVal = "12345"
		val floatVal = "1.1234"

		val plainCursorString = "instantVal desc=${instantVal},intVal asc=${intVal},floatVal desc=${floatVal}"

		println(stringParser.parse(plainCursorString))
	}

}
