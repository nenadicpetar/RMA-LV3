package com.example.inspiringpersonroom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inspiringpersonroom.ioThread

private const val DATABASE = "inspiring_persons"

@Database(entities = [InspiringPerson::class], version = 7)
@TypeConverters(Converters::class)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var instance: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PersonDatabase {
            return Room.databaseBuilder(context, PersonDatabase::class.java, DATABASE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).personDao().insertPersons(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()
        }

        val PREPOPULATE_DATA = listOf(
            InspiringPerson(
                "https://en.cryptonomist.ch/wp-content/uploads/2021/04/mvccopelonmusk.jpg",
                "Elon Musk",
                "28.06.1971.",
                "?",
                "Elon Musk je osnivač, glavni izvršni direktor i glavni tehnološki suradnik SpaceX-a, arhitekt proizvoda Tesla Inc.",
                "Upornost je jako važna. Ne bi trebali odustati osim ako niste prisiljeni na to."
            ),

            InspiringPerson(
                "https://www.worldsultimate.net/wp-content/uploads/2017/08/Legend-Dennis-Ritchie.jpg",
                "Dennis Ritchie",
                "09.09.1941.",
                "12.10.2011.",
                "Denis Ritchie je bio američki računalni znanstvenik, tvorac programskog jezika C i ključna osoba u razvoju operacijskog sustava UNIX.",
                "UNIX je zapravo jednostavan operacijski sustav, ali trebate biti genij da razumijete jednostavnost."
            ),

            InspiringPerson(
                "https://raw.githubusercontent.com/deepaksood619/TributeLinusTorvalds/master/torvald.jpg",
                "Linus Torvalds",
                "28.12.1969.",
                "?",
                "Finski znanstvenik, kreator Linux kernela. Također, kreirao je Git, sustav za verzioniranje koda.",
                "Računalo je kao klima uređaj, postaje beskorisno ako otvorite prozor (Windows)."
            ),

            InspiringPerson(
                "https://www.heidelberg-laureate-forum.org/fileadmin/laureaten/Liskov_2.jpg",
                "Barbara Liskov",
                "07.11.1939.",
                "?",
                "Barbara Liskov je američka računalna znanstvenica, profesorica na Massachusetts Institute od Technology (MIT).",
                "Kada sve ostalo zakaže, ljudi se okreću razumu."
            ),

            InspiringPerson(
                "https://static.dw.com/image/17321990_303.jpg",
                "Alan Turing",
                "23.06.1912.",
                "07.06.1950.",
                "Alan Mathison Turing je bio britanski matematičar, logičar i kriptoanalaitičar. Često se smatra ocem modernog računarstva.",
                "Računalo će zaslužiti da ga zovu inteligentnim ako zavara čovjeka da je i ono čovjek."
            )
        )
    }
}