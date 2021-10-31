package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.Command
import com.beshoy.thirdwayvcalculator.domain.service.Calculator
import com.beshoy.thirdwayvcalculator.domain.service.interfaces.ExecutionService


class ClearCommand(
    private val execService: ExecutionService,
    private val calculator: Calculator
) : Command {
    override fun execute(): Boolean {
        execService.clear()
        calculator.currentValue = 0
        return true
    }
}