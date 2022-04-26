package com.proect.recipes.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.proect.recipes.R
import com.proect.recipes.adapters.CategoryMealsAdapter
import com.proect.recipes.databinding.ActivityCategoryMealsBinding
import com.proect.recipes.databinding.ActivityMealBinding
import com.proect.recipes.fragments.HomeFragment
import com.proect.recipes.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel

    //для показа элементов при нажатии на категорию
    lateinit var categoryMealsAdapter:CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //для показа элементов при нажатии на категорию
        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString() //показать кол-во блюд в списке
            categoryMealsAdapter.setMealsList(mealsList)

        })

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}