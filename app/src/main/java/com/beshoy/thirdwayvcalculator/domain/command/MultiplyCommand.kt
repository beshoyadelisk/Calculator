package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.UndoableCommand
import com.beshoy.thirdwayvcalculator.domain.service.Calculator


class MultiplyCommand(
    private val calculator: Calculator,
    private val value: Int
) : UndoableCommand {
    override fun undo() {
        calculator.divide(value)
    }

    override fun execute(): Boolean {
        calculator.multiply(value)
        return true
    }
}