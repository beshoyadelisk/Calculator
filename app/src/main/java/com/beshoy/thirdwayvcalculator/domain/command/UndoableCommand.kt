package com.beshoy.thirdwayvcalculator.domain.command

interface UndoableCommand : Command {
    fun undo()
}