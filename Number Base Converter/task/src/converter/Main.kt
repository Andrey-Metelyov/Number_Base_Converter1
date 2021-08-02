package converter

import kotlin.system.exitProcess

const val digits = "0123456789ABCDEF"

fun main() {
    while (true) {
        println("Do you want to convert /from decimal or /to decimal? (To quit type /exit)")
        val command = readLine()!!
        when (command) {
            "/from" -> from()
            "/to" -> to()
            "/exit" -> exitProcess(0)
            else -> println("error")
        }
    }
}

fun to() {
    println("Enter source number:")
    val number = readLine()!!
    println("Enter source base:")
    val base = readLine()!!.toInt()
    println("Conversion to decimal result: ${convertTo10(number, base)}")
}

fun convertTo10(number: String, base: Int): Int {
    var result = 0
    var pow = 1
    for (i in number.lastIndex downTo 0) {
        System.err.println("${number[i]}(${digits.indexOf(number[i])}) * $pow")
        result += digits.indexOf(number[i].uppercaseChar()) * pow
        pow *= base
    }
    return result
}

fun from() {
    println("Enter number in decimal system:")
    val number = readLine()!!.toInt()
    println("Enter target base:")
    val targetBase = readLine()!!.toInt()
    println("Conversion result: ${convert(number, targetBase)}")
}

fun convert(number: Int, targetBase: Int): String {
    var num = number
    var result = ""
    while (num >= targetBase) {
        result = "${digits[num % targetBase]}" + result
        num = (num - num % targetBase) / targetBase
    }
    result = "${digits[num]}" + result
    return result
}
