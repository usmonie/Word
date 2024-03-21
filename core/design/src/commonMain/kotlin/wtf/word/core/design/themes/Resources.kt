package wtf.word.core.design.themes

import org.jetbrains.compose.resources.ExperimentalResourceApi
import word.core.design.generated.resources.Res
import word.core.design.generated.resources.de
import word.core.design.generated.resources.es
import word.core.design.generated.resources.fr
import word.core.design.generated.resources.ic_app_icon
import word.core.design.generated.resources.ic_apple_logo
import word.core.design.generated.resources.ic_google_logo
import word.core.design.generated.resources.ic_lifebuoy
import word.core.design.generated.resources.ic_rocket_launch_outline
import word.core.design.generated.resources.it
import word.core.design.generated.resources.pt
import word.core.design.generated.resources.ru
import word.core.design.generated.resources.ua
import wtf.word.core.design.themes.icons.RocketLaunchOutline

@OptIn(ExperimentalResourceApi::class)
public object Resources {
    val de = Res.drawable.de
    val es = Res.drawable.es
    val it = Res.drawable.it
    val fr = Res.drawable.fr
    val pt = Res.drawable.pt
    val ru = Res.drawable.ru
    val ua = Res.drawable.ua

    val ic_google_logo = Res.drawable.ic_google_logo
    val ic_apple_logo = Res.drawable.ic_apple_logo

    val ic_lifebuoy = Res.drawable.ic_lifebuoy
    val ic_rocket_launch_outline = RocketLaunchOutline


    val ic_app_icon = Res.drawable.ic_app_icon

}