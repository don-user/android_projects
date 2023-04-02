package ru.yundon.rss.presantation.dialog

import android.app.AlertDialog
import android.view.LayoutInflater
import ru.yundon.rss.databinding.ExitDialogBinding
import ru.yundon.rss.presantation.activity.MainActivity

object ExitDialog {

    fun showDialog(activity: MainActivity){
        var dialog: AlertDialog? = null
        val binding = ExitDialogBinding.inflate(LayoutInflater.from(activity))
        val builder = AlertDialog.Builder(activity).setView(binding.root)

        binding.apply {
            buttonCloseApp.setOnClickListener {
                activity.finish()
            }
            buttonMenu.setOnClickListener {
                dialog?.dismiss()
            }
        }

        dialog = builder.create()
        dialog.apply {
            window?.setBackgroundDrawable(null)
            show()
        }
    }
}