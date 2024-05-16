package com.usmonie.core.kit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.usmonie.core.kit.icons.flag.AllIcons
import com.usmonie.core.kit.icons.flag.FlagGroup
import com.usmonie.core.kit.icons.logos.IcAppleLogo
import com.usmonie.core.kit.icons.logos.IcGoogleLogo
import kotlin.collections.List as ____KtList

public object Logo

private var _AllIcons: ____KtList<ImageVector>? = null

public val Logo.AllIcons: ____KtList<ImageVector>
    get() {
        if (_AllIcons != null) {
            return _AllIcons!!
        }
        _AllIcons = FlagGroup.AllIcons + listOf(
            IcAppleLogo, IcGoogleLogo,
        )
        return _AllIcons!!
    }
