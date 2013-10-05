package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{OpenAction, SaveAction}
import grizzled.slf4j.Logging
import java.awt.FileDialog
import java.io.{PrintWriter, File}
import javax.swing._
import scala.io.Source

/**
 * メインウィンドウ。
 */
class MainWindow extends JFrame("mdedit") with Logging {
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "mdedit")
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  var optFile: Option[File] = None

  val previewPane = new HtmlPreview
  val sourcePane = new SourcePane(previewPane)

  val splitPane = new JSplitPane()
  splitPane.add(new JScrollPane(sourcePane), JSplitPane.LEFT)
  splitPane.add(new JScrollPane(previewPane), JSplitPane.RIGHT)
  splitPane.setResizeWeight(0.5)

  getContentPane.add(splitPane)

  val saveAction = new SaveAction(this)
  val openAction = new OpenAction(this)

  setJMenuBar(createMenuBar)

  def createMenuBar: JMenuBar = {
    val menuBar = new JMenuBar
    val fileMenu = menuBar.add(new JMenu("File"))
    fileMenu.add(new JMenuItem(saveAction))
    fileMenu.add(new JMenuItem(openAction))
    val editMenu = menuBar.add(new JMenu("Edit"))
    editMenu.add(new JMenuItem(sourcePane.undoAction))
    editMenu.add(new JMenuItem(sourcePane.redoAction))
    menuBar
  }

  def openFile() {
    chooseFile().foreach { (file) =>
      optFile = Option(file)
      sourcePane.setMarkdownSource(Source.fromFile(file)("utf-8").mkString)
    }
  }

  def saveFile() {
    optFile.orElse {
      optFile = chooseFile()
      optFile
    }.foreach { (file) =>
      val writer = new PrintWriter(file, "utf-8")
      try writer.write(sourcePane.getText) finally writer.close()
    }
  }

  def chooseFile(): Option[File] = {
    val dialog = new FileDialog(this)
    dialog.setVisible(true)
    val files = dialog.getFiles
    if (files.size > 0) Option(files(0))
    else None
  }
}

object MainWindow {
  def main(args: Array[String]) {
    // メニューの一番左を設定できるはずだが、効かないようだ。
    // sys.props("com.apple.mrj.application.apple.menu.about.name") = "mdedit"
    sys.props("apple.laf.useScreenMenuBar") = "true"
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

    val win = new MainWindow()
    win.setSize(500, 400)
    win.setVisible(true)
  }
}
