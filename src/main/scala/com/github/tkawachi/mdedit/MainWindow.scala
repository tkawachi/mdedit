package com.github.tkawachi.mdedit

import javax.swing._

/**
 * メインウィンドウ。
 */
class MainWindow extends JFrame("mdedit") {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  val previewPane = new HtmlPreview
  val sourcePane = new SourcePane(previewPane)

  val splitPane = new JSplitPane()
  splitPane.add(new JScrollPane(sourcePane), JSplitPane.LEFT)
  splitPane.add(new JScrollPane(previewPane), JSplitPane.RIGHT)
  splitPane.setResizeWeight(0.5)

  getContentPane.add(splitPane)
}

object MainWindow {
  def main(args: Array[String]) {
    val win = new MainWindow()
    win.setSize(500, 400)
    win.setVisible(true)
  }
}
