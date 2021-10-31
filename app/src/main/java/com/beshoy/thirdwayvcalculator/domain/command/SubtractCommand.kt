package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.UndoableCommand
import com.beshoy.thirdwayvcalculator.domain.service.Calculator


class SubtractCommand(
    private val calculator: Calculator,
    private val value: Int
) : UndoableCommand {
    override fun undo() {
        calculator.add(value)
    }

    override fun execute(): Boolean {
        calculator.subtract(value)
        return true
    }
}