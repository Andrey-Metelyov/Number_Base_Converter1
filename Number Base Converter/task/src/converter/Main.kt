package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
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
                    System.err.println("**********************************************************************")
                    System.err.println("**********************************************************************")
                    System.err.println("**********************************************************************")
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
    if (sourceBase == targetBase) {
        return number
    }
    if (sourceBase == 10) {
        return convertFrom10(number, targetBase)
    }
    if (targetBase == 10) {
        return convertTo10(number, sourceBase)
    }
    return convertFrom10(convertTo10(number, sourceBase), targetBase)
}

fun convertTo10(number: String, sourceBase: Int): String {
    System.err.println("convert $number from $sourceBase base to 10 base")
    val numberParts = number.split(".")
    val intPart = numberParts[0]
    val fracPart = if (numberParts.size > 1) numberParts[1] else ""
    System.err.println("intPart = $intPart, fracPart = $fracPart")
    return (convertIntTo10(intPart, sourceBase) +
            if (fracPart.isNotEmpty())
                "."+ convertFracTo10(fracPart, sourceBase)
            else "")
}

fun convertFracTo10(fracPart: String, base: Int): String {
    var result = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_EVEN)
    var pow = BigDecimal(base).setScale(10, RoundingMode.HALF_EVEN)
    for (i in 0..fracPart.lastIndex) {
        System.err.println("${fracPart[i]}(${digits.indexOf(fracPart[i].uppercaseChar())}) / $pow")
        val value = digits.indexOf(fracPart[i].uppercaseChar()).toBigDecimal().setScale(10, RoundingMode.HALF_EVEN) / pow
        result += value
        System.err.println("result += $value = $result")
        pow *= base.toBigDecimal()
    }
    System.err.println("converting frac 0.$fracPart from base $base to 10 base: $result")
    var strResult = result.toPlainString().split(".")[1]
    while (strResult.length < 5) {
        strResult += "0"
    }
    return strResult
}

//fun to() {
//    println("Enter source number:")
//    val number = readLine()!!
//    println("Enter source base:")
//    val base = readLine()!!.toInt()
//    println("Conversion to decimal result: ${convertTo10(number, base)}")
//}

fun convertIntTo10(number: String, base: Int): String {
    var result = BigInteger.ZERO
    var pow = BigInteger.ONE
    for (i in number.lastIndex downTo 0) {
        System.err.println("${number[i]}(${digits.indexOf(number[i].uppercaseChar())}) * $pow")
        result += digits.indexOf(number[i].uppercaseChar()).toBigInteger() * pow
        pow *= base.toBigInteger()
    }
    System.err.println("converting $number from base $base to 10 base: $result")
    return result.toString()
}

//fun from() {
//    println("Enter number in decimal system:")
//    val number = readLine()!!.toBigInteger()
//    println("Enter target base:")
//    val targetBase = readLine()!!.toInt()
//    println("Conversion result: ${convertFrom10(number, targetBase)}")
//}

fun convertFrom10(number: String, targetBase: Int): String {
    val parts = number.split(".")
    var integerPart = parts[0].toBigInteger()
    val fractionalPart = if (parts.size > 1) parts[1] else ""
    var result = ""

    while (integerPart >= targetBase.toBigInteger()) {
        val rem = (integerPart % targetBase.toBigInteger()).toInt()
        result = "${digits[rem]}" + result
        integerPart = (integerPart - rem.toBigInteger()) / targetBase.toBigInteger()
    }
    result = "${digits[integerPart.toInt()]}" + result
    System.err.println("converting $number to base $targetBase from 10 base: $result")

    if (fractionalPart.isNotEmpty()) {
        result += "."
        var frac = BigDecimal("0.$fractionalPart")
        var fractionalResult = ""
        while (frac > BigDecimal.ZERO && fractionalResult.length < 5) {
            System.err.println("frac. part = $fractionalPart")
            val mul = frac * targetBase.toBigDecimal()
            val intPart = mul.toInt()
            frac = mul - intPart.toBigDecimal()
            fractionalResult += "${digits[intPart]}"
            System.err.println("result = $fractionalResult")
        }
        while (fractionalResult.length < 5) {
            fractionalResult += 0
        }
        result = result + fractionalResult
    }
    return result
}
