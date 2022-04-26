package com.proect.recipes.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proect.recipes.db.MealDatabase
import com.proect.recipes.pojo.Meal
import com.proect.recipes.pojo.MealList
import com.proect.recipes.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MealViewModel(
    val mealDatabase:MealDatabase
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    private var favoritesLiveData = MutableLiveData<List<Meal>>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }

        })
    }

    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    //сохраняем еду в избранное
    fun insertMeal(meal:Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun observeFavoritesLiveData():LiveData<List<Meal>> {
        return favoritesLiveData
    }

}