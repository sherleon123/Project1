package logic
import kotlin.random.Random
import android.util.Log
import utilities.Constants.GameLogic.CHANCE_OF_SUGAR
import utilities.Constants.GameLogic.SUGAR_END
import utilities.Constants.GameLogic.SUGAR_START
import utilities.SignalManager

class GameManager(private val lifeCount: Int = 3) {
    var score: Int = 0
        private set

    private var Characters=arrayOf(0, 1, 0)
    private var sugars = arrayOf(
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0)
    )
    var setofsugars=false

    var currentIndex: Int = 1
        private set

    var heartlost: Int = 0
        private set
    val getArrayCharacters: Array<Int>
        get()=Characters
    val getArraySugars: Array<Array<Int>>
        get()=sugars
    var getCharacter: Int = 0
        get() {
            var index = -1
            var i =0
            for (num in Characters) {
                if (num == 1) {
                    index = i
                }
                i++
            }
            return index
        }


    val isGameOver: Boolean
        get() = heartlost == lifeCount

    fun collisionCheck(row: Int) {
        if (!isGameOver)
        {
            if(row==getCharacter)
            {
                heartlost++
                toastAndVibrate()
            }
            else
                score++


        }

    }
    fun updateCharacter(expected: Int){
        var index=-1
        when (expected)
        {
            -1 -> if (getCharacter<2) {
                index=getCharacter
                Characters[index + 1] = 1
                Characters[index] = 0
            }
            1 -> if (getCharacter>0) {
                index=getCharacter
                Characters[index - 1] = 1
                Characters[index] = 0

            }
            else -> "nothing"
        }
    }
    fun updateSugar(){
        for (row in sugars.indices){
            for (i in 4 downTo 0)
            {
                    if (i==4&&sugars[row][i]==1)
                    {
                        collisionCheck(row)
                        sugars[row][i]=0
                    }
                    else if (sugars[row][i]==1)
                    {
                        sugars[row][i]=0
                        sugars[row][i+1]=1
                    }
            }
        }
    }

    fun generateSugar(){

        if (setofsugars==false)
        {
            var randomNumber = Random.nextInt(1, CHANCE_OF_SUGAR)
            when (randomNumber)
            {
                1 -> {
                    randomNumber = Random.nextInt(3)
                    CreateSugar(randomNumber)
                }
                2 -> {
                    setofsugars=true
                    var pair = generateDistinctNumbers()
                    CreateSugar(pair.first)
                    CreateSugar(pair.second)
                }
                else -> "No Sugar"
            }
        }
        else
        {
            setofsugars=false
        }


    }
    fun generateDistinctNumbers(): Pair<Int, Int> {
        val numbers = mutableSetOf<Int>()
        while (numbers.size < 2) {
            numbers.add(Random.nextInt(3))  // Generates numbers between 1 and 3 (inclusive)
        }
        val iterator = numbers.iterator()
        return Pair(iterator.next(), iterator.next()) // Return the two distinct numbers as a Pair
    }
    fun CreateSugar(row:Int)
    {
        sugars[row][SUGAR_START]=1
    }
    private fun toastAndVibrate() {
        SignalManager.getInstance().toast("OUCH NO SUGAR!")
        SignalManager.getInstance().vibrate()
    }
}