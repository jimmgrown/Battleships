package com.example.battleships

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    private val colors = mutableListOf<String>(
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    )
    private val colorsTwo = mutableListOf<String>(
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    )
    private var target  = mutableListOf<String>(
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    )
    private var targetTwo = mutableListOf<String>(
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ", " ", " ", " ", " ", " "
    )

    var rotation = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val symbolsStroke = listOf<String>("А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К")
        val numbersStroke = listOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        // Initialize a new array adapter instance
        val adapter = ArrayAdapter(
            this, // Context
            R.layout.list_item, // Layout
            colors // List
        )
         val adapterTwo = ArrayAdapter(
            this, // Context
            R.layout.list_item, // Layout
            colorsTwo // List
        )
         val adapterTarget = ArrayAdapter(
            this, // Context
            R.layout.list_item, // Layout
            target // List
        )
         val adapterTargetTwo = ArrayAdapter(
            this, // Context
            R.layout.list_item, // Layout
            targetTwo // List
        )
        val handler = Handler()
        val adapterSymbols = ArrayAdapter(this, R.layout.list_item, symbolsStroke)
        val adapterNumbers = ArrayAdapter(this, R.layout.list_item, numbersStroke)
        var counter = 0
        var counterTwo = 0
        var gameFaze = 0
        val shipSize = listOf<Int>(4,3,3,2,2,2,1,1,1,1)
        var shipIndex = 0

        btnChange.setOnClickListener{
            text_view.text=changePerson(text_view.text as String)
            if (text_view.text == "ИГРОК 2") {
                grid_view.adapter = adapterTwo
            } else {
                grid_view.adapter = adapter
            }
        }

        buttonTry.setOnClickListener {
                if(text_view.text=="ИГРОК 1" && grid_view.adapter == adapter) {
                    grid_view.adapter = adapterTarget
                    grid_view.isEnabled = true
                }
                else if(text_view.text=="ИГРОК 1" && grid_view.adapter == adapterTarget) {
                    grid_view.adapter = adapter
                    grid_view.isEnabled = false
                }
                else if(text_view.text=="ИГРОК 2" && grid_view.adapter == adapterTwo) {
                    grid_view.adapter = adapterTargetTwo
                    grid_view.isEnabled = true
                }
                else {
                    grid_view.adapter = adapterTwo
                    grid_view.isEnabled = false
                }

        }
        // Set the grid view adapter/data source
        grid_view.adapter = adapter
        gridViewSymbols.adapter = adapterSymbols
        gridViewNumber.adapter = adapterNumbers
        btnChange.toggleVisibility()
        buttonHide.toggleVisibility()
        buttonTry.toggleVisibility()
        textView2.toggleVisibility()
        grid_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (gameFaze==2) {
                    if (text_view.text == "ИГРОК 2") {
                        if(colors[position]=="O"||colors[position]=="X" //&& tryToHit(colors,position)==1
                            ) {
                            targetTwo[position] = "X"
                            colors[position] = "X"
                            textView2.text="попал"
                        }
//                        else if(colors[position]=="O" && tryToHit(colors,position)==0) {
//                            targetTwo[position] = "X"
//                            colors[position] = "X"
//                            textView2.text="Попал"
//                        }
                        else{
                            targetTwo[position] = "."
                            colors[position] = "."
                            textView2.text="мимо"
                        }
                        if(win(colors,0)==20) {
                            toastMe(2)
                            handler.postDelayed({
                                endGame()
                            }, 1000)
                        }
                        else if(win(colorsTwo,0)==20) {
                            toastMe(1)
                            handler.postDelayed({
                                endGame()
                            }, 1000)
                        }
                        grid_view.adapter = adapterTargetTwo
                        handler.postDelayed({
                            grid_view.adapter = adapterTarget
                            text_view.text=changePerson(text_view.text as String)
                        }, 1000)

                    } else {
                        if(colorsTwo[position]=="O"||colorsTwo[position]=="X"//&& tryToHit(colorsTwo,position)==1
                             ) {
                            target[position] = "X"
                            colorsTwo[position] = "X"
                            textView2.text="попал"
                        }
//                        else if(colorsTwo[position]=="O"&& tryToHit(colorsTwo,position)==0) {
//                            target[position] = "X"
//                            colorsTwo[position] = "X"
//                            textView2.text="Попал"
//                        }
                        else{
                            target[position] = "."
                            colorsTwo[position] = "."
                            textView2.text="мимо"
                        }
                        //tryToKill(colorsTwo,0,0)
                        if(win(colors,0)==20) {
                            toastMe(2)
                            handler.postDelayed({
                                endGame()
                            }, 1000)
                        }
                        else if(win(colorsTwo,0)==20) {
                            toastMe(1)
                            handler.postDelayed({
                                endGame()
                            }, 1000)
                        }
                        grid_view.adapter = adapterTarget
                        handler.postDelayed({
                            grid_view.adapter = adapterTargetTwo
                            text_view.text=changePerson(text_view.text as String)
                        }, 1000)
                    }
                }
                if(gameFaze==1){
                    if(tryPutShip(shipSize[shipIndex],colorsTwo,0,position)==1){
                        putShipTwo(shipSize[shipIndex],rotation,position)
                        shipIndex+=1
                    }
                        grid_view.adapter = adapterTwo
                        counter = tryToStart(colorsTwo, 0)
                    if (counter == 20){
                        gameFaze = 2
                        counter = 0
                        shipIndex = 0
                        text_view.text=changePerson(text_view.text as String)
                        grid_view.adapter = adapterTarget
                        //grid_view.toggleVisibility()
                        //btnChange.toggleVisibility()
                        buttonHide.toggleVisibility()
                        buttonTry.toggleVisibility()
                        btnRotate.toggleVisibility()
                        textView2.toggleVisibility()
                    }
                }
                if(gameFaze==0){
                    if(tryPutShip(shipSize[shipIndex],colors,0,position)==1){
                        putShip(shipSize[shipIndex],rotation,position)
                        shipIndex+=1
                    }
                    grid_view.adapter = adapter
                    counter = tryToStart(colors, 0)
                    if (counter == 20) {
                        gameFaze = 1
                        counter = 0
                        shipIndex = 0
                        text_view.text=changePerson(text_view.text as String)
                        grid_view.adapter = adapterTwo
                    }
                }
                //grid_view.adapter
                //text_view.text = "GridView item clicked : $selectedItem \nAt index position : $position"
            }
    }

    private fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
        }
    }

    private fun tryPutShip(shipSize:Int, mas:List<String>, rotation:Int, position:Int): Int =
        when (shipSize) {
            0 -> {1}
            else -> {
                if(position%10 in 1..8  && position>=10 && position<89 && (position%10+shipSize<11&&rotation==0||position/10+shipSize<11&&rotation==1)) {
                    if (mas[position - 11] == " " && mas[position + 9] == " " && mas[position + 11] == " " && mas[position - 9] == " " &&
                        mas[position] == " " && //&& position / 10 != 0 && position < 90 && position % 10 != 0 && position % 10 != 9 &&
                        mas[position + 1] == " " && mas[position - 1] == " " && mas[position - 10] == " " && mas[position + 10] == " "
                    ) {
                        if (rotation == 0)
                            tryPutShip(shipSize - 1, mas, rotation, position + 1)
                        else
                            tryPutShip(shipSize - 1, mas, rotation, position + 10)
                    } else 0
                }
                else if(position%10==0 && position>9 && position<90 && mas[position - 9] == " "&& mas[position + 11] == " " && mas[position + 1] == " " &&
                    mas[position + 10] == " "&&mas[position - 10] == " "&&(position/10+shipSize<11&&rotation==1||rotation==0)){
                        if (rotation == 0)
                            tryPutShip(shipSize - 1, mas, rotation, position + 1)
                        else
                            tryPutShip(shipSize - 1, mas, rotation, position + 10)
                    }
                else if(position%10==9 && position>9 && position<90 && mas[position - 11] == " "&& mas[position + 9] == " " && mas[position - 1] == " " &&
                    mas[position + 10] == " " &&mas[position - 10] == " " &&(position/10+shipSize<11&&rotation==1||shipSize==1&&rotation==0)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position in 1..8 && mas[position + 11] == " "&& mas[position + 9] == " " && mas[position + 1] == " " && mas[position - 1] == " "&&
                    mas[position+10] == " "&&(position%10+shipSize<11&&rotation==0||rotation==1)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position in 91..98 && mas[position - 11] == " "&& mas[position - 9] == " " && mas[position + 1] == " " && mas[position - 1] == " "&&
                    mas[position-10] == " "&&(position%10+shipSize<11&&rotation==0||shipSize==1&&rotation==1)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position==0&&mas[position+1]==" "&&mas[position+10]==" "&&mas[position+11]==" "){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position==9&&mas[position-1]==" "&&mas[position+10]==" "&&mas[position+9]==" "&&(shipSize==1&&rotation==0||rotation==1)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position==90&&mas[position+1]==" "&&mas[position-10]==" "&&mas[position-9]==" "&&(shipSize==1&&rotation==1||rotation==0)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                }
                else if(position==99&&mas[position-1]==" "&&mas[position-10]==" "&&mas[position-11]==" "&&(shipSize==1&&rotation==1||shipSize==1&&rotation==0)){
                    if (rotation == 0)
                        tryPutShip(shipSize - 1, mas, rotation, position + 1)
                    else
                        tryPutShip(shipSize - 1, mas, rotation, position + 10)
                } else 0
            }
        }
    private fun putShip(shipSize:Int, rotation:Int, position:Int): Int =
        when(shipSize){
            0 -> {1}
            else -> {
                colors[position]="O"
                if(rotation==0)
                putShip(shipSize-1,rotation,position+1)
                else
                    putShip(shipSize-1,rotation,position+10)
            }
        }
    private fun putShipTwo(shipSize:Int, rotation:Int, position:Int): Int =
        when(shipSize){
            0 -> {1}
            else -> {
                colorsTwo[position]="O"
                if(rotation==0)
                    putShipTwo(shipSize-1,rotation,position+1)
                else
                    putShipTwo(shipSize-1,rotation,position+10)
            }
        }
    fun win(mas:List<String>,counter:Int): Int {
        return if(mas.isEmpty()) counter
        else if(mas.first()=="X") win(mas.drop(1),counter+1)
        else win(mas.drop(1),counter)
    }
    fun switchRotation(view: View) {
        if(this.rotation==0)
            this.rotation =1
        else
            this.rotation =0
    }
    fun showHide(view: View) {
        grid_view.toggleVisibility()
        grid_view.adapter
    }

    private fun changePerson(text: String): String {
        return if (text == "ИГРОК 2") {
            "ИГРОК 1"
        } else {
            "ИГРОК 2"
        }
    }
    fun endGame(){
        val gameIntent = Intent(this, MainActivity::class.java)
        startActivity(gameIntent)
    }
//    fun tryToHit(mas:List<String>,position:Int): Int {
//        if(mas[position]=="O" && position%10 !=0 && position%10 !=9 && position>9 && position<90)
//            if(mas[position-1] =="O" || mas[position+1] =="O" || mas[position-10] =="O"|| mas[position+10] =="O"
//                ||mas[position-1] =="X" || mas[position+1] =="X" || mas[position-10] =="X"|| mas[position+10] =="X")
//                return 1
//            else
//                return 0
//        else if(position in 1..8 && mas[position]=="O")
//            if(mas[position-1]=="O" || mas[position+1]=="O" || mas[position+10]=="O" ||
//                mas[position-1]=="X" || mas[position+1]=="X" || mas[position+10]=="X") return 1 else return 0
//        else if(position in 91..98 && mas[position]=="O")
//            if(mas[position-1]=="O" || mas[position+1]=="O" || mas[position-10]=="O"||
//                mas[position-1]=="X" || mas[position+1]=="X" || mas[position-10]=="X") return 1 else return 0
//        else if(position==0&& mas[position]=="O")
//            if(mas[position+1]=="O" || mas[position+10]=="O"||mas[position+1]=="X" || mas[position+10]=="X") return 1 else return 0
//        else if(position==9&& mas[position]=="O")
//            if(mas[position-1]=="O" || mas[position+10]=="O"||mas[position-1]=="X" || mas[position+10]=="X") return 1 else return 0
//        else if(position==90&& mas[position]=="O")
//            if(mas[position+1]=="O" || mas[position-10]=="O"||mas[position+1]=="X" || mas[position-10]=="X") return 1 else return 0
//        else if(position==99&& mas[position]=="O")
//            if(mas[position-1]=="O" || mas[position-10]=="O"||mas[position-1]=="X" || mas[position-10]=="X") return 1 else return 0
//        else if(position%10==0&&(mas[position+1]=="O" ||mas[position-10]=="O"||mas[position]+10=="O")) return 1
//        else if(position%10==9&&(mas[position-1]=="O" ||mas[position-10]=="O"||mas[position]+10=="O")) return 1
//        else return 2
//    }
//    fun tryToKill(mas:List<String>,counter:Int,index:Int): Int {
//        if(mas.isEmpty()&&counter==0) return 0
//        else if((mas.first()==" "||mas.first()=="X"||mas.first()==".")&&counter==0) return tryToKill(mas.drop(1),counter,index+1)
//        else if((mas.first()==" "||mas.first()=="X"||mas.first()=="."||mas.isEmpty())&&counter>1) {
//            kill(counter, index-1)
//            return tryToKill(mas.drop(1),0,index+1)
//        }
//        else return tryToKill(mas.drop(1),counter+1,index+1)
//    }
//    fun kill(counter:Int,index:Int): Int {
//        if(counter==0) return 0
//        else {
//            target[index]="X"
//            colorsTwo[index]="X"
//            return kill(counter-1,index-1)
//        }
//    }
fun toastMe(gamer:Int) {
    // val myToast = Toast.makeText(this, message, duration);
    if(gamer==1) {
        val myToast =
            Toast.makeText(this, "Игрок 1 победил!!!", Toast.LENGTH_SHORT)
        myToast.show()
    }
    if(gamer==2) {
        val myToast =
            Toast.makeText(this, "Игрок 2 победил!!!", Toast.LENGTH_SHORT)
        myToast.show()
    }
}
    private fun tryToStart(mas:List<String>, counter:Int): Int =
        when {
            mas.isEmpty() -> counter
            mas.first() != " " -> tryToStart(mas.drop(1),counter+1)
            else -> tryToStart(mas.drop(1),counter)
        }

}


