package com.example.demoapps.fragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.FragmentNotificationBinding


class NotificationFragment : Fragment() {
    private lateinit var dataBinding: FragmentNotificationBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private val channelId = "channelId"
    private val channelName = "channelName"
    private val KEY_TEXT_REPLY = "reply_txt"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        createNotificationChannel()
        dataBinding.btnNotify.setOnClickListener {
            notificationContent()
        }
        onBackPressed()
    }

    private fun onBackPressed() {
        val backPressedCallback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent=Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }


    private fun notificationContent() {
        //notification
        val intent = Intent(context, MainActivity::class.java)
        intent.action = "REPLY_ACTION"
        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        }
        //notfication Reply
        val replyLabel: String = resources.getString(R.string.reply_label)

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
                .build()
        }
        val replyintent = Intent(requireContext(), MainActivity::class.java)
        val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(requireContext(), 101, replyintent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val action: NotificationCompat.Action = NotificationCompat.Action.Builder(R.drawable.ic_baseline_reply_24, "Reply", replyPendingIntent)
            .addRemoteInput(remoteInput)
            .setAllowGeneratedReplies(true)
            .build()

        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setContentTitle("Demo App")
            .setContentText("Demo App Notification")
            .setSmallIcon(R.drawable.ic_baseline_person_add_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(action)
            .build()
        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(0, notification)
    }


private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationChannel =
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
                enableVibration(true)
            }
        notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
}