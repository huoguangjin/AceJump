package com.johnlindquist.acejump.control

import org.jetbrains.concurrency.runAsync
import java.lang.System.currentTimeMillis

object Trigger : () -> Unit {
  private var delay = 750
  private var timer = currentTimeMillis()
  private var isRunning = false
  private var invokable: () -> Unit = {}

  override fun invoke() {
    timer = currentTimeMillis()
    if (isRunning) return
    synchronized(this) {
      isRunning = true

      while (currentTimeMillis() - timer <= delay) {
        Thread.sleep(Math.abs(delay - (currentTimeMillis() - timer)))
      }

      invokable.invoke()

      isRunning = false
    }
  }

  fun restart(delay: Int = 750, function: () -> Unit) {
    this.delay = delay
    invokable = function
    runAsync(this)
  }
}