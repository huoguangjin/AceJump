package com.johnlindquist.acejump.ui

import com.johnlindquist.acejump.search.AceFont
import com.intellij.openapi.editor.colors.EditorColorsManager
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

object Canvas : JComponent() {
  var existingTags = hashSetOf<Pair<Int, Int>>()
  var jumpLocations: Collection<JumpInfo> = arrayListOf()
  val scheme = EditorColorsManager.getInstance().globalScheme

  init {
    font = Font(scheme.editorFontName, BOLD, scheme.editorFontSize)
  }

  override fun paint(graphics: Graphics) {
    if (jumpLocations.isEmpty())
      return

    super.paint(graphics)

    val g2d = graphics as Graphics2D
    existingTags = hashSetOf<Pair<Int, Int>>()
    jumpLocations.forEach { it.paintMe(g2d) }
  }

  fun registerTag(point: Pair<Int, Int>, tag: String) {
    (-1..(tag.length)).forEach {
      existingTags.add(Pair(point.first + it * AceFont.fontWidth, point.second))
    }
  }

  fun isFree(point: Pair<Int, Int>): Boolean {
    return !existingTags.contains(point)
  }
}