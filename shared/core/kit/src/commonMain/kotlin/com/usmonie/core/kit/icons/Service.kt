package com.usmonie.core.kit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.usmonie.core.kit.icons.service.AllIcons
import com.usmonie.core.kit.icons.service.IcLifebuoy
import com.usmonie.core.kit.icons.service.IcRocketLaunchOutline
import com.usmonie.core.kit.icons.service.IcUpload
import com.usmonie.core.kit.icons.service.ServiceGroup
import kotlin.collections.List as ____KtList

public object Service

private var _AllIcons: ____KtList<ImageVector>? = null

public val Service.AllIcons: ____KtList<ImageVector>
    get() {
        if (_AllIcons != null) {
            return _AllIcons!!
        }
        _AllIcons = ServiceGroup.AllIcons + listOf(
            IcLifebuoy, IcUpload, IcRocketLaunchOutline
        )
        return _AllIcons!!
    }
