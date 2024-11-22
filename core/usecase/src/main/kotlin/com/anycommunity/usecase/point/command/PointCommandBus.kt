package com.anycommunity.usecase.point.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.point.command.usecase.PointGameCommand
import com.anycommunity.usecase.point.command.usecase.PointGameUsecase

sealed interface PointCommandBus {
    infix fun execute(command: PointGameCommand)
}

@Service
internal class PointCommandBusImpl(
    private val pointGameUsecase: PointGameUsecase,
) : PointCommandBus {
    override fun execute(command: PointGameCommand) = pointGameUsecase(command)
}
