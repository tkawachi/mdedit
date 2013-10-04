package com.github.tkawachi.mdedit

object Platform {
  val isMac: Boolean = System.getProperty("os.name").startsWith("Mac")
}
