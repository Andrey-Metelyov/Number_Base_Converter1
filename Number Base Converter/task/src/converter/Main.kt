package converter

fun main() {
    println("Enter number in decimal system:")
    val number = readLine()!!.toInt()
    println("Enter target base:")
    val targetBase = readLine()!!.toInt()
    println("Conversion result: ${convert(number, targetBase)}")
}

fun convert(number: Int, targetBase: Int): String {
    val digits = "0123456789ABCDEF"
    var num = number
    var result = ""
    while (num >= targetBase) {
        result = "${digits[num % targetBase]}" + result
        num = (num - num % targetBase) / targetBase
    }
    result = "${digits[num]}" + result
    return result
}
