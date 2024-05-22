package com.usmonie.compass.core

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()
