package com.jinuk.toy.applicaiton.point.command

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessCommand
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessUsecase

sealed interface PointCommandBus {
    infix fun execute(command: PointProcessCommand)
}

@Service
internal class PointCommandBusImpl(
    private val pointProcessUsecase: PointProcessUsecase,
) : PointCommandBus {
    override fun execute(command: PointProcessCommand) = pointProcessUsecase(command)
}
