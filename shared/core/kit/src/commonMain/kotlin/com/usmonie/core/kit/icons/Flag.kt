package com.usmonie.core.kit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.usmonie.core.kit.icons.flag.AllIcons
import com.usmonie.core.kit.icons.flag.De
import com.usmonie.core.kit.icons.flag.Es
import com.usmonie.core.kit.icons.flag.Fr
import com.usmonie.core.kit.icons.flag.It
import com.usmonie.core.kit.icons.flag.Flaggroup
import com.usmonie.core.kit.icons.flag.Pt
import com.usmonie.core.kit.icons.flag.Ru
import com.usmonie.core.kit.icons.flag.Ua
import kotlin.collections.List as ____KtList

public object Flag

private var _AllIcons: ____KtList<ImageVector>? = null

public val Flag.AllIcons: ____KtList<ImageVector>
    get() {
        if (_AllIcons != null) {
            return _AllIcons!!
        }
        _AllIcons = Flaggroup.AllIcons + listOf(
            Ru, Fr, Es, Pt, De, It, Ua,
        )
        return _AllIcons!!
    }
