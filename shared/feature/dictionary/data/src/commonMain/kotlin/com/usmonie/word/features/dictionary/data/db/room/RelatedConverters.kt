package com.usmonie.word.features.dictionary.data.db.room

import androidx.room.TypeConverter
import com.usmonie.word.features.dictionary.data.api.models.CategoryDto
import com.usmonie.word.features.dictionary.data.api.models.DescendantDto
import com.usmonie.word.features.dictionary.data.api.models.EtymologyTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.FormDto
import com.usmonie.word.features.dictionary.data.api.models.HeadTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.InflectionTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.RelatedDto
import com.usmonie.word.features.dictionary.data.api.models.SenseDto
import com.usmonie.word.features.dictionary.data.api.models.SoundDto
import com.usmonie.word.features.dictionary.data.api.models.TranslationDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("TooManyFunctions")
class StringConverters {
    @TypeConverter
    internal fun fromStrings(value: List<String>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toStrings(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}

@Suppress("TooManyFunctions")
class StringsConverters {
    @TypeConverter
    internal fun fromStrings(value: List<List<String>>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toStrings(value: String): List<List<String>> {
        return Json.decodeFromString(value)
    }
}

@Suppress("TooManyFunctions")
class RelatedConverters {
    @TypeConverter
    internal fun fromRelated(value: List<RelatedDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toRelated(value: String): List<RelatedDto> {
        return Json.decodeFromString(value)
    }
}

class CategoryConverters {

    @TypeConverter
    internal fun fromCategory(value: List<CategoryDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toCategory(value: String): List<CategoryDto> {
        return Json.decodeFromString(value)
    }
}

class DescendantConverters {
    @TypeConverter
    internal fun fromDescendant(value: List<DescendantDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toDescendant(value: String): List<DescendantDto> {
        return Json.decodeFromString(value)
    }
}

class EtymologyTemplateConverters {

    @TypeConverter
    internal fun fromEtymologyTemplate(value: List<EtymologyTemplateDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toEtymologyTemplate(value: String): List<EtymologyTemplateDto> {
        return Json.decodeFromString(value)
    }
}

class HeadTemplateConverters {

    @TypeConverter
    internal fun fromHeadTemplate(value: List<HeadTemplateDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toHeadTemplate(value: String): List<HeadTemplateDto> {
        return Json.decodeFromString(value)
    }
}

class InflectionTemplateConverters {

    @TypeConverter
    internal fun fromInflectionTemplate(value: List<InflectionTemplateDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toInflectionTemplate(value: String): List<InflectionTemplateDto> {
        return Json.decodeFromString(value)
    }
}

class FormConverters {

    @TypeConverter
    internal fun fromForm(value: List<FormDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toForm(value: String): List<FormDto> {
        return Json.decodeFromString(value)
    }
}

class SenseConverters {

    @TypeConverter
    internal fun fromSense(value: List<SenseDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toSense(value: String): List<SenseDto> {
        return Json.decodeFromString(value)
    }
}

class SoundConverters {

    @TypeConverter
    internal fun fromSound(value: List<SoundDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toSound(value: String): List<SoundDto> {
        return Json.decodeFromString(value)
    }
}

class TranslationsConverters {

    @TypeConverter
    internal fun fromTranslation(value: List<TranslationDto>): String {
        return Json.encodeToString(value = value)
    }

    @TypeConverter
    internal fun toTranslation(value: String): List<TranslationDto> {
        return Json.decodeFromString(value)
    }
}
