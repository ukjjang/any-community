package com.anycommunity.usecase.point.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.point.port.command.model.PointGameCommand
import com.anycommunity.usecase.point.usecase.command.PointGameUsecase

sealed interface PointCommandPort {
    infix fun execute(command: PointGameCommand)
}

@Service
internal class PointCommandPortImpl(
    private val pointGameUsecase: PointGameUsecase,
) : PointCommandPort {
    override fun execute(command: PointGameCommand) = pointGameUsecase(command)
}
