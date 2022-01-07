package com.example.calculatorapp

import android.annotation.SuppressLint
import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsViewModel>()

        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.ic_launcher_foreground, "Result: " + i))
        }

        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter


        val btn = findViewById<Button>(R.id.buEq)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btn.setOnClickListener {
            val intent = Intent(this, afterNotification::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            } else {

                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234, builder.build())
        }

    }

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitmap)
        }
    }


    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val currentPhotoPath = "@/Gallery"
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    var isNewOp=true
    var dot=false

    fun buNumberEvent(view: View)
    {
        if(isNewOp)
        {
            findViewById<TextView>(R.id.etShowNumber).setText("")
        }
        isNewOp=false
        val buSelect= view as Button
        var buClickValue:String=findViewById<TextView>(R.id.etShowNumber).text.toString()
        when(buSelect.id)
        {
            findViewById<TextView>(R.id.bu0).id->
            {
                buClickValue+="0"
            }
            findViewById<TextView>(R.id.bu1).id->
            {
                buClickValue+="1"
            }
            findViewById<TextView>(R.id.bu2).id->
            {
                buClickValue+="2"
            }
            findViewById<TextView>(R.id.bu3).id->
            {
                buClickValue+="3"
            }
            findViewById<TextView>(R.id.bu4).id->
            {
                buClickValue+="4"
            }
            findViewById<TextView>(R.id.bu5).id->
            {
                buClickValue+="5"
            }
            findViewById<TextView>(R.id.bu6).id->
            {
                buClickValue+="6"
            }
            findViewById<TextView>(R.id.bu7).id->
            {
                buClickValue+="7"
            }
            findViewById<TextView>(R.id.bu8).id->
            {
                buClickValue+="8"
            }
            findViewById<TextView>(R.id.bu9).id->
            {
                buClickValue+="9"
            }
            findViewById<TextView>(R.id.buDot).id->
            {
                if(dot==false)
                {
                    buClickValue += "."
                }
                dot=true
            }
            findViewById<TextView>(R.id.buPlusMinus).id->
            {
                buClickValue="-" + buClickValue
            }
        }
        findViewById<TextView>(R.id.etShowNumber).setText(buClickValue)
    }

    var op="X"
    var oldNumber=""

    fun buOpEvent(view: View)
    {
        val buSelect= view as Button
        when(buSelect.id)
        {
            findViewById<TextView>(R.id.buMul).id->
            {
                op="X"
            }
            findViewById<TextView>(R.id.buDiv).id->
            {
                op="÷"
            }
            findViewById<TextView>(R.id.buSub).id->
            {
                op="-"
            }
            findViewById<TextView>(R.id.buSum).id->
            {
                op="+"
            }
        }
        oldNumber=findViewById<TextView>(R.id.etShowNumber).text.toString()
        isNewOp=true
        dot=false
    }

    fun buEqualEvent(view: View)
    {
        val newNumber=findViewById<TextView>(R.id.etShowNumber).text.toString()
        var finalNumber:Double?=null
        when(op)
        {
            "X"->
            {
                finalNumber=oldNumber.toDouble() * newNumber.toDouble()
            }
            "÷"->
            {
                finalNumber=oldNumber.toDouble() / newNumber.toDouble()
            }
            "-"->
            {
                finalNumber=oldNumber.toDouble() - newNumber.toDouble()
            }
            "+"->
            {
                finalNumber=oldNumber.toDouble() + newNumber.toDouble()
            }
        }
        findViewById<TextView>(R.id.etShowNumber).setText(finalNumber.toString())
        isNewOp=true
    }

    fun buPercentEvent(view: View)
    {
        val number=(findViewById<TextView>(R.id.etShowNumber).text.toString().toDouble())/100
        findViewById<TextView>(R.id.etShowNumber).setText(number.toString())
        isNewOp=true
    }

    fun buCleanEvent(view: View)
    {
        findViewById<TextView>(R.id.etShowNumber).setText("")
        isNewOp=true
        dot=false
    }
}