package com.github.tkawachi.mdedit

import java.awt.event.{KeyEvent, KeyListener}
import javax.swing._

/**
 * メインウィンドウ。
 */
class MainWindow extends JFrame("mdedit") {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  val textArea = new JEditorPane()
  val htmlView = new HtmlPreview

  val splitPane = new JSplitPane()
  splitPane.add(new JScrollPane(textArea), JSplitPane.LEFT)
  splitPane.add(new JScrollPane(htmlView), JSplitPane.RIGHT)
  splitPane.setResizeWeight(0.5)

  getContentPane.add(splitPane)

  textArea.addKeyListener(new KeyListener {
    def keyTyped(e: KeyEvent) {}

    def keyPressed(e: KeyEvent) {}

    def keyReleased(e: KeyEvent) {
      htmlView.setMarkdownSource(textArea.getText)
    }
  })
}

object MainWindow {
  def main(args: Array[String]) {
    val win = new MainWindow()
    win.setSize(500, 400)
    win.setVisible(true)
  }
}
