fun main() {

    val input = getResourceAsText("input01.txt")
    val acc = mutableListOf<Int>(0)


    val folded = input.lines()
        .foldRight(acc,
            { s, accp ->
                if (s.isBlank()) acc.add(0) else acc[acc.size - 1] += s.toInt()
                accp
            })

    val maxCalories = folded.max()

    val topThreeMaxCalories = with(folded.sorted().reversed()) {
        component1() + component2() + component3()
    }

    println(maxCalories)

    println(topThreeMaxCalories)

}

