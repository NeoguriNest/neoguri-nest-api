package com.neoguri.neogurinest.api.presentation.aspect

import com.neoguri.neogurinest.api.application.aspect.BoardContext
import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class BoardActorAspect(
    val userRepository: UserEntityRepositoryInterface
) {

    @Around("execution(public * com.neoguri.neogurinest.api.presentation.board.*Controller.*(..))")
    fun injectBoardActor(joinPoint: ProceedingJoinPoint): Any? {

        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication is AccessTokenAuthentication) {
            val loginUser = (authentication.details as LoginUserDto)

            val actorUser = userRepository.findByIdOrFail(loginUser.userId)
            val actor = BoardActor(loginUser.userId, actorUser.nickname, loginUser.nestIds.toList(), emptyList())

            BoardContext.getInstance().actor = actor
        }

        // request handler 호출
        val result = joinPoint.proceed()
        BoardContext.clear()

        return result
    }
}