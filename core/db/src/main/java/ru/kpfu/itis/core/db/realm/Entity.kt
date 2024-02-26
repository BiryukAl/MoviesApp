package ru.kpfu.itis.core.db.realm

import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Film() : RealmObject {
    @PersistedName("_id")
    var _id: ObjectId = ObjectId()

    @PrimaryKey
    @PersistedName("kinopoiskId")
    var kinopoiskId: Int = 0

    @PersistedName("name")
    var name: String = ""

    @PersistedName("previewUrl")
    var previewUrl: String = ""

    @PersistedName("imageUrl")
    var imageUrl: String = ""

    @PersistedName("genres")
    var genres: RealmSet<String> = realmSetOf()

    @PersistedName("countries")
    var countries: RealmSet<String> = realmSetOf()

    @PersistedName("year")
    var year: Int = 0

    @PersistedName("description")
    var description: String = ""
}
