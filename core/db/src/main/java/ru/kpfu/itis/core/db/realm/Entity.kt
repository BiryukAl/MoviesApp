package ru.kpfu.itis.core.db.realm

import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Film() : RealmObject {
    var _id: ObjectId = ObjectId()
    @PrimaryKey
    var kinopoiskId: Int = 0
    var name: String = ""
    var previewUrl: String = ""
    var imageUrl: String = ""
    var genres: RealmSet<String> = realmSetOf()
    var countries: RealmSet<String> = realmSetOf()
    var year: Int = 0
    var description: String = ""
}
