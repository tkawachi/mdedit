package com.github.tkawachi.mdedit

import java.awt.{Frame, FileDialog}
import java.io.File

/**
 * ファイルを選択するダイアログを表示する関数たち。
 */
object FileChooser {
  /**
   * 開くファイルを選択する。
   * @param parent 親フレーム
   * @return 選択されたファイル
   */
  def chooseOpen(parent: Frame): Option[File] =
    showDialog(new FileDialog(parent, "開くファイルを選択", FileDialog.LOAD))

  /**
   * 保存するファイルを選択する。
   * @param parent 親フレーム
   * @return 選択されたファイル
   */
  def chooseSave(parent: Frame): Option[File] =
    showDialog(new FileDialog(parent, "保存するファイルを選択", FileDialog.SAVE))

  private[this] def showDialog(dialog: FileDialog): Option[File] = {
    dialog.setVisible(true)
    val files = dialog.getFiles
    if (files.size > 0) Option(files(0))
    else None
  }
}
