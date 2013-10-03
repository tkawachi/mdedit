package com.github.tkawachi.mdedit

import javax.swing.JEditorPane
import org.pegdown.PegDownProcessor

class HtmlPreview extends JEditorPane {
  val processor = new PegDownProcessor

  setContentType("text/html")
  setEditable(false)
  setText("ここにはHTMLが表示されます。")

  def setMarkdownSource(src: String) {
    val html = processor.markdownToHtml(src)
    setText(html)
  }
}
