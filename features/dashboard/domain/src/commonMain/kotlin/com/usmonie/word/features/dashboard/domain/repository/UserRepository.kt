package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.Theme

interface UserRepository {
    var currentTheme: Theme
}
