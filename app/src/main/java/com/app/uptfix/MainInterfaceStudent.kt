package com.app.uptfix

import CustomComponents.CardAdapter
import CustomComponents.CategoryCard
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_main_interface_student.*
import kotlinx.android.synthetic.main.category_card.view.*

class MainInterfaceStudent : AppCompatActivity() {
    enum class categories {
        bed, blinds, chair, cupboard, desk, door, fridge, outlet, shelf, shower, sink, toilet, washing_machine, windows
    }

    lateinit var bottomBar: CustomComponents.BottomBar
    lateinit var categoryGrid: GridView

    var cardList: MutableList<CategoryCard> = ArrayList<CategoryCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_interface_student)
        bottomBar = blueBottomBar
        categoryGrid = cardsGrid
        var noOfCards = categories.values().size
        openProfilePage(bottomBar)
        openHistoryPage(bottomBar)
        addCardsToList(noOfCards)
        createCardLayout()
        openReportProblemPage(noOfCards)
    }

    fun addCardsToList(noOfCards: Int) {
        for (i in 0..noOfCards - 1) {
            var category = categories.values()[i].toString()
            var card = CategoryCard(this)
            card.setCardText(category)
            val id = this.resources.getIdentifier(category, "drawable", this.packageName)
            val drawable = this.resources.getDrawable(id)
            card.layout.mainIcon.setImageDrawable(drawable)
            cardList.add(card)
        }
    }

    fun openReportProblemPage(noOfCards: Int) {
        for (i in 0..noOfCards - 1) {
            cardList.get(i).setOnClickListener(){
                val intent = Intent(this, ProblemSender::class.java)
                intent.putExtra("category",cardList.get(i).categoryText.text.toString())
                startActivity(intent)


            }

        }


    }

    fun createCardLayout() {
        var cardAdapter = CardAdapter(this, cardList)
        cardsGrid.adapter = cardAdapter

    }

    fun openProfilePage(bottomBar: CustomComponents.BottomBar) {
        bottomBar.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileInfo::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    fun openHistoryPage(bottomBar: CustomComponents.BottomBar) {
        bottomBar.historyButton.setOnClickListener {
            val intent = Intent(this, ProblemHistoryStudent::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}

