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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEachIndexed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNativeLanguagePage(
    onSelectLanguage: (Language) -> Unit,
    onBackClick: () -> Unit,
) {
    val flagDe = painterResource(DrawableResource("drawable/de.xml"))
    val flagIt = painterResource(DrawableResource("drawable/it.xml"))
    val flagPt = painterResource(DrawableResource("drawable/pt.xml"))
    val flagFr = painterResource(DrawableResource("drawable/fr.xml"))
    val flagEs = painterResource(DrawableResource("drawable/es.xml"))
    val flagRu = painterResource(DrawableResource("drawable/ru.xml"))
    val flagUa = painterResource(DrawableResource("drawable/ua.xml"))


    val languages = remember {
        listOf(
            LanguageType(
                Language.DE,
                "Deutsch",
                flagDe
            ),
            LanguageType(
                Language.IT,
                "Italiano",
                flagIt
            ),
            LanguageType(
                Language.PT,
                "Português",
                flagPt
            ),
            LanguageType(
                Language.FR,
                "Français",
                flagFr
            ),
            LanguageType(
                Language.ES,
                "Español",
                flagEs
            ),
            LanguageType(
                Language.RU,
                "Русский",
                flagRu
            ),
            LanguageType(
                Language.UA,
                "Український",
                flagUa
            ),
        )
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "[S]elect your native language",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
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
                            Divider(Modifier.fillMaxWidth().padding(horizontal = 32.dp))
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