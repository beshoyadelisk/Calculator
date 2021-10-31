package com.beshoy.thirdwayvcalculator.domain.command.interfaces

interface Command {
    fun execute(): Boolean
}