package com.beshoy.thirdwayvcalculator.domain.command.interfaces

interface UndoableCommand : Command {
    fun undo()
}