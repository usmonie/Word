package com.usmonie.word.features.dashboard.data.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
  fun createDriver(): SqlDriver
}
