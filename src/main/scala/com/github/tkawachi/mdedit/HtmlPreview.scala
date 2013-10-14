package com.github.tkawachi.mdedit

import org.pegdown.PegDownProcessor
import scala.swing.EditorPane

/**
 * HTMLでのプレビューペイン。
 */
class HtmlPreview extends EditorPane {
  val processor = new PegDownProcessor

  contentType = "text/html"
  peer.setEditable(false)
  peer.setText("ここにはHTMLが表示されます。")

  def setMarkdownSource(src: String) {
    val html = processor.markdownToHtml(src)
    peer.setText(html)
  }
}
