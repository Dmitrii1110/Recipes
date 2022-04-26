package com.proect.recipes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.proect.recipes.pojo.Meal

//Вставка+обновление в БД
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    //Удаление
    @Delete
    suspend fun delete(meal:Meal)

    //Запрос
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}