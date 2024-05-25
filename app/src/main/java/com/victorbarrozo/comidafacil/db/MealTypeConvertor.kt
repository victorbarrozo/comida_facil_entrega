package com.victorbarrozo.comidafacil.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {
    @TypeConverter
    fun fromAnyToString(atribute: Any?): String {
        if ( atribute == null )
            return ""
        return atribute as String
    }
    @TypeConverter
    fun fromStringToAny(atribute: String): Any {
        return atribute
    }
}