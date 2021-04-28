package com.example.inspiringpersonroom.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class InspiringPerson(
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,

    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "birth_date")
    val birthDate: String,

    @ColumnInfo(name = "death_date")
    val deathDate: String,

    val description: String,

    val quote: String
) {
    fun lifeDates(): String {
        return "$birthDate - $deathDate"
    }
}