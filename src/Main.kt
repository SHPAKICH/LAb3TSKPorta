fun main() {
    // Ввод данных
    println("Введите сообщение:")
    val inputMessage = readLine()!!.replace(" ", "").uppercase()

    println("Введите вспомогательный символ:")
    val auxiliaryChar = readLine()!!.uppercase()

    println("Использовать типовую таблицу (1) или сгенерировать случайную (2)?")
    val tableChoice = readLine()!!.toInt()

    // Формирование таблицы шифрования
    val cipherTable = if (tableChoice == 1) {
        generateDefaultTable()
    } else {
        generateRandomTable()
    }

    // Подготовка сообщения: если нечетное число символов - добавляем вспомогательный символ
    val formattedMessage = formatMessage(inputMessage, auxiliaryChar)

    // Шифрование
    val encryptedMessage = encryptMessage(formattedMessage, cipherTable)

    // Вывод результатов
    println("\nИсходное сообщение (разбито по парам):")
    formattedMessage.chunked(2).forEach { print("$it ") }

    println("\n\nЗашифрованное сообщение:")
    encryptedMessage.chunked(3).forEach { print("$it ") }

//    println("\n\nШифровальная таблица:")
//    cipherTable.forEach { (key, value) ->
//        println("$key: $value")
//    }
}

fun formatMessage(message: String, auxiliaryChar: String): String {
    var result = message
    if (result.length % 2 != 0) {
        result += auxiliaryChar
    }
    return result
}

// Функция для шифрования сообщения
fun encryptMessage(message: String, cipherTable: Map<String, String>): String {
    val encryptedMessage = StringBuilder()

    for (i in message.indices step 2) {
        val pair = message.substring(i, i + 2)
        val cipherValue = cipherTable[pair]

        if (cipherValue != null) {
            encryptedMessage.append(cipherValue)
        } else {
            // Вывод ошибки, если пара не найдена в таблице
            println("Ошибка: не найдена пара символов '$pair' в шифровальной таблице.")
        }
    }

    return encryptedMessage.toString()
}

// Генерация типовой таблицы для 32x32 алфавита
fun generateDefaultTable(): Map<String, String> {
    val alphabet = "АБВГДЕЖЗИКЛМНОПРСТУФХЦЧШЩЫЭЮЯ"
    val table = mutableMapOf<String, String>()

    var counter = 1
    for (i in alphabet.indices) {
        for (j in alphabet.indices) {
            val code = counter.toString().padStart(3, '0')  // Заполняем до 3 цифр
            table[alphabet[i].toString() + alphabet[j]] = code
            counter++
        }
    }

    return table
}

// Генерация случайной таблицы для 32x32 алфавита
fun generateRandomTable(): Map<String, String> {
    val alphabet = "АБВГДЕЖЗИКЛМНОПРСТУФХЦЧШЩЫЭЮЯ"
    val numbers = (1..1024).shuffled().map { it.toString().padStart(3, '0') }.toMutableList()
    val table = mutableMapOf<String, String>()

    for (i in alphabet.indices) {
        for (j in alphabet.indices) {
            val pair = alphabet[i].toString() + alphabet[j]
            table[pair] = numbers.removeAt(0)
        }
    }

    return table
}
