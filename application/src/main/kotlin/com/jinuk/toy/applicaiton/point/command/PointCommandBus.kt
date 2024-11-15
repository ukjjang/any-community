package com.jinuk.toy.applicaiton.point.command

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.point.command.usecase.PointGameCommand
import com.jinuk.toy.applicaiton.point.command.usecase.PointGameUsecase
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessCommand
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessUsecase

sealed interface PointCommandBus {
    infix fun execute(command: PointProcessCommand)
    infix fun execute(command: PointGameCommand)
}

@Service
internal class PointCommandBusImpl(
    private val pointProcessUsecase: PointProcessUsecase,
    private val pointGameUsecase: PointGameUsecase,
) : PointCommandBus {
    override fun execute(command: PointProcessCommand) = pointProcessUsecase(command)
    override fun execute(command: PointGameCommand) = pointGameUsecase(command)
}
