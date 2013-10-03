package com.github.tkawachi.mdedit

import java.awt.event.{KeyEvent, KeyListener}
import javax.swing.JEditorPane

/**
 * Markdown のソースコードを書くペイン。
 */
class SourcePane(preview: HtmlPreview) extends JEditorPane {
  addKeyListener(new KeyListener {
    def keyTyped(e: KeyEvent) {}

    def keyPressed(e: KeyEvent) {}

    def keyReleased(e: KeyEvent) {
      preview.setMarkdownSource(getText)
    }
  })
}
