package wtf.word.core.design.themes.icons

import androidx.compose.ui.graphics.vector.ImageVector
import wtf.word.core.design.themes.icons.myiconpack.AllIcons
import wtf.word.core.design.themes.icons.myiconpack.De
import wtf.word.core.design.themes.icons.myiconpack.Myiconpack
import kotlin.collections.List as ____KtList
import wtf.word.core.design.themes.icons.myiconpack.Es
import wtf.word.core.design.themes.icons.myiconpack.Fr
import wtf.word.core.design.themes.icons.myiconpack.IcAppleLogo
import wtf.word.core.design.themes.icons.myiconpack.IcGoogleLogo
import wtf.word.core.design.themes.icons.myiconpack.IcLifebuoy
import wtf.word.core.design.themes.icons.myiconpack.IcRocketLaunchOutline
import wtf.word.core.design.themes.icons.myiconpack.IcUpload
import wtf.word.core.design.themes.icons.myiconpack.It
import wtf.word.core.design.themes.icons.myiconpack.Pt
import wtf.word.core.design.themes.icons.myiconpack.Ru
import wtf.word.core.design.themes.icons.myiconpack.Ua

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= Myiconpack.AllIcons + listOf(IcAppleLogo, Ua, IcRocketLaunchOutline, IcUpload,
        IcLifebuoy, Ru, Fr, Es, Pt, De, IcGoogleLogo, It)
    return __AllIcons!!
  }
