package com.neoguri.neogurinest.api.persistence.exception

import com.neoguri.neogurinest.api.domain.common.exception.LogicException

class UnexpectedSpecificationTypeException(clazz: Class<*>) : LogicException("처리할 수 없는 Specification 타입입니다. ${clazz.name}") {
}