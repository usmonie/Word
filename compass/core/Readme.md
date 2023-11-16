# Compass Multiplatform Navigation Library

## Overview

This library facilitates navigation for Compose Multiplatform applications on iOS, Android, Web, PC (Windows), and Mac. It provides a structured and type-safe way to navigate between screens, handle deep links, and manage screen states.

## Key Features

- **Route Management**: Define and manage routes for different screens.
- **Parameter Passing**: Pass parameters between routes.
- **Transition Animations**: Customize screen transition animations.
- **Back Navigation**: Handle back navigation with stack management.
- **Deep Linking**: Navigate directly to specific screens using URIs.
- **Extras**: Pass additional data during navigation.
- **Screen Builders**: Type-safe way to create screens with parameters.

## Getting Started

### 1. **Define a Screen**

All screens should implement the `Screen` interface. For screens that support deep linking, implement the `DeepLinkScreen` interface.

```kotlin
class HomeScreen : Screen() {
    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        Text("Home Screen Graph 1")
    }

    companion object Builder : ScreenBuilder {
        const val ID = "HomeScreen"
        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return HomeScreen()
        }
    }
}

class ArticleScreen(private val params: Map<String, String>?, private val extra: Extra?) : Screen() {

    override val id: String
        get() = ID

    @Composable
    override fun Content() {
        val id = params?.get("articleId") ?: ""
        Text("id: $id")
    }

    companion object Builder: BaseDeepLinkScreenBuilder() {
        const val ID = "ArticleScreen"
        override val deepLinkPattern: String
            get() = "app://article/{articleId}"

        override val id: String
            get() = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return ArticleScreen(params, extra)
        }
    }
}
```

### 2. **Setup the RouteManager**

The `RouteManager` is responsible for managing navigation. Initialize it with a set of routes:

```kotlin
val routeManager = RouteManager(
    routes = mapOf(
        "HomeScreen" to HomeScreen.Builder,
        "ArticleScreen" to ArticleScreen.Builder,
        // ... other routes ...
    )
)
```

### 3. **Navigate Between Screens**

Use the `navigateTo` method to navigate between screens:

```kotlin
routeManager.navigateTo("HomeScreen")
```

To pass parameters or extras:

```kotlin
routeManager.navigateTo("ArticleScreen", params = mapOf("articleId" to "12345"))
```

### 4. **Handle Back Navigation**

Use the `navigateBack` method to handle back navigation:

```kotlin
routeManager.navigateBack()
```

### 5. **Deep Linking**

To handle deep links, use the `handleDeepLink` method:

```kotlin
routeManager.handleDeepLink("app://article/12345")
```

## Advanced Features

### Screen Builders

For screens that require parameters, use a screen builder:

```kotlin
class ArticleScreenBuilder : ScreenBuilder<ArticleScreen> {
    override fun build(params: Map<String, String>?): ArticleScreen {
        val articleId = params?.get("articleId") ?: ""
        return ArticleScreen(articleId)
    }
}
```

### Extras

To pass additional data during navigation, use extras:

```kotlin
data class SpecialConfigExtra(val config: SpecialConfig) : Extra {
    override val key = "specialConfig"
    override val data get() = config
}
```

## Conclusion

The Compass Multiplatform Navigation Library provides a robust and flexible solution for navigating between screens in a multiplatform application. With support for deep linking, extras, and screen builders, it offers a comprehensive set of features to handle all your navigation needs.
