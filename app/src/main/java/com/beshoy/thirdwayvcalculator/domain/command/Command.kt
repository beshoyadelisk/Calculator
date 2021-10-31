package com.beshoy.thirdwayvcalculator.domain.command

interface Command {
    fun execute(): Boolean
}