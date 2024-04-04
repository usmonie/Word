@file:OptIn(ExperimentalResourceApi::class)

package com.usmonie.word.features.onboarding.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.onboarding.ui.models.LanguageType
import wtf.word.core.design.themes.icons.myiconpack.De
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import word.shared.features.onboarding.ui.generated.resources.Res
import word.shared.features.onboarding.ui.generated.resources.lang_de_title
import word.shared.features.onboarding.ui.generated.resources.lang_es_title
import word.shared.features.onboarding.ui.generated.resources.lang_fr_title
import word.shared.features.onboarding.ui.generated.resources.lang_it_title
import word.shared.features.onboarding.ui.generated.resources.lang_pt_title
import word.shared.features.onboarding.ui.generated.resources.lang_ru_title
import word.shared.features.onboarding.ui.generated.resources.lang_ua_title
import word.shared.features.onboarding.ui.generated.resources.select_your_native_language_title
import wtf.speech.core.ui.BaseCard
import wtf.word.core.design.themes.icons.MyIconPack
import wtf.word.core.design.themes.icons.myiconpack.Es
import wtf.word.core.design.themes.icons.myiconpack.Fr
import wtf.word.core.design.themes.icons.myiconpack.It
import wtf.word.core.design.themes.icons.myiconpack.Pt
import wtf.word.core.design.themes.icons.myiconpack.Ru
import wtf.word.core.design.themes.icons.myiconpack.Ua
import wtf.word.core.domain.tools.fastForEachIndexed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNativeLanguagePage(
    onSelectLanguage: (Language) -> Unit,
    onBackClick: () -> Unit,
) {
    val flagDe = MyIconPack.De
    val flagIt = MyIconPack.It
    val flagPt = MyIconPack.Pt
    val flagFr = MyIconPack.Fr
    val flagEs = MyIconPack.Es
    val flagRu = MyIconPack.Ru
    val flagUa = MyIconPack.Ua
    val langDeTitle = stringResource(Res.string.lang_de_title)
    val langItTitle = stringResource(Res.string.lang_it_title)
    val langPtTitle = stringResource(Res.string.lang_pt_title)
    val langFrTitle = stringResource(Res.string.lang_fr_title)
    val langEsTitle = stringResource(Res.string.lang_es_title)
    val langRuTitle = stringResource(Res.string.lang_ru_title)
    val langUaTitle = stringResource(Res.string.lang_ua_title)

    val languages = remember {
        listOf(
            LanguageType(Language.DE, langDeTitle, flagDe),
            LanguageType(Language.IT, langItTitle, flagIt),
            LanguageType(Language.PT, langPtTitle, flagPt),
            LanguageType(Language.FR, langFrTitle, flagFr),
            LanguageType(Language.ES, langEsTitle, flagEs),
            LanguageType(Language.UA, langUaTitle, flagUa),
            LanguageType(Language.RU, langRuTitle, flagRu),
        )
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.select_your_native_language_title)) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) {
        Box(Modifier.fillMaxSize().padding(it)) {
            BaseCard(
                Modifier.fillMaxWidth()
                    .padding(24.dp)
            ) {
                Spacer(Modifier.height(24.dp))
                languages.fastForEachIndexed { index, lang ->
                    Column {
                        if (index > 0) {
                            HorizontalDivider(Modifier.fillMaxWidth().padding(horizontal = 32.dp))
                        }
                        LanguageItem({ lang }, onSelectLanguage)
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun LanguageItem(languageType: () -> LanguageType, onClick: (Language) -> Unit) {
    val lang = languageType()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick(lang.language) }
            .padding(horizontal = 24.dp)
    ) {
        Image(
            lang.flag,
            contentDescription = lang.title,
            modifier = Modifier.size(28.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(24.dp))
        Text(
            lang.title,
            modifier = Modifier.padding(vertical = 14.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}