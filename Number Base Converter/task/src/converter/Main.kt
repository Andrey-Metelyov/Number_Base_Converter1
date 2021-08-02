package converter

import java.math.BigInteger
import kotlin.system.exitProcess

const val digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main() {
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        when (val command = readLine()!!) {
            "/exit" -> exitProcess(0)
            else -> {
                val (sourceBase, targetBase) = command.split(" ").map { it.toInt() }
                while (true) {
                    println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
                    val input = readLine()!!
                    if (input != "/back") {
                        println("Conversion result: ${convert(input, sourceBase, targetBase)}")
                    } else {
                        break
                    }
                }
            }
        }
    }
}

fun convert(number: String, sourceBase: Int, targetBase: Int): String {
    System.err.println("converting $number from base $sourceBase to $targetBase")
    if (sourceBase == 10) {
        return convertFrom10(number.toBigInteger(), targetBase)
    }
    if (targetBase == 10) {
        return convertTo10(number, sourceBase).toString()
    }
    return convertFrom10(convertTo10(number, sourceBase), targetBase)
}

//fun to() {
//    println("Enter source number:")
//    val number = readLine()!!
//    println("Enter source base:")
//    val base = readLine()!!.toInt()
//    println("Conversion to decimal result: ${convertTo10(number, base)}")
//}

fun convertTo10(number: String, base: Int): BigInteger {
    var result = BigInteger.ZERO
    var pow = BigInteger.ONE
    for (i in number.lastIndex downTo 0) {
        System.err.println("${number[i]}(${digits.indexOf(number[i].uppercaseChar())}) * $pow")
        result += digits.indexOf(number[i].uppercaseChar()).toBigInteger() * pow
        pow *= base.toBigInteger()
    }
    System.err.println("converting $number from base $base to 10 base: $result")
    return result
}

//fun from() {
//    println("Enter number in decimal system:")
//    val number = readLine()!!.toBigInteger()
//    println("Enter target base:")
//    val targetBase = readLine()!!.toInt()
//    println("Conversion result: ${convertFrom10(number, targetBase)}")
//}

fun convertFrom10(number: BigInteger, targetBase: Int): String {
    var num = number
    var result = ""

    while (num >= targetBase.toBigInteger()) {
        val rem = (num % targetBase.toBigInteger()).toInt()
        result = "${digits[rem]}" + result
        num = (num - rem.toBigInteger()) / targetBase.toBigInteger()
    }
    result = "${digits[num.toInt()]}" + result
    System.err.println("converting $number to base $targetBase from 10 base: $result")
    return result
}
