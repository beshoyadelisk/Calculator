package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.service.Calculator


class AddCommand(
    private val calculator: Calculator,
    private val value: Int
) : UndoableCommand {
    override fun undo() {
        calculator.subtract(value)
    }

    override fun execute(): Boolean {
        calculator.add(value)
        return true
    }
}