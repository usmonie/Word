package com.usmonie.core.kit.icons.service

import androidx.compose.ui.graphics.vector.ImageVector
import com.usmonie.core.kit.icons.Flag
import kotlin.collections.List as ____KtList

public object ServiceGroup

public val Flag.Flaggroup: ServiceGroup
    get() = ServiceGroup

private var _AllIcons: ____KtList<ImageVector>? = null

public val ServiceGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (_AllIcons != null) {
            return _AllIcons!!
        }
        _AllIcons = listOf()
        return _AllIcons!!
    }
