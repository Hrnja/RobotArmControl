package com.example.robotarm

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         init()

        connect.setOnClickListener {
            val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

            val request = WifiNetworkSpecifier.Builder()
                .setSsid("NodeMCU")
                .setWpa2Passphrase("12345678")
                .build()

            val requestInfo = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(request)
                .build()

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    // Connect to the network
                    val connectivityManager =
                        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    connectivityManager.bindProcessToNetwork(network)
                }
            }

            val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.requestNetwork(requestInfo, callback)
        }

        openHand.setOnClickListener {
            coroutineScope.launch {
                controlRobot(open)
            }
        }
        closeHand.setOnClickListener {
            coroutineScope.launch {
                controlRobot(close)
            }
        }

        motor4TurnOn.setOnClickListener {
            coroutineScope.launch {
                controlRobot(turnOn)
            }
        }

        motor4TurnOff.setOnClickListener {
            coroutineScope.launch {
                controlRobot(turnOff)
            }
        }

        motor3I0.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor3Send0)
            }
        }

        motor3I45.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor3Send45)
            }
        }

        motor3I90.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor3Send90)
            }
        }

        motor2I0.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor2Send0)
            }
        }
        motor2I45.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor2Send45)
            }
        }
        motor2I90.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor2Send90)
            }
        }

        motor1I180.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor1Send180)
            }
        }

        motor1I135.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor1Send135)
            }
        }

        motor1I90.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor1Send90)
            }
        }

        motor1I45.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor1Send45)
            }
        }

        motor1I0.setOnClickListener {
            coroutineScope.launch {
                controlRobot(motor1Send0)
            }
        }

    }

    private lateinit var connect: Button
    private lateinit var openHand:Button
    private lateinit var closeHand:Button
    private lateinit var motor4TurnOn:ImageView
    private lateinit var motor4TurnOff:ImageView
    private lateinit var motor3I0:ImageView
    private lateinit var motor3I45:ImageView
    private lateinit var motor3I90:ImageView
    private lateinit var motor2I0:ImageView
    private lateinit var motor2I45:ImageView
    private lateinit var motor2I90:ImageView
    private lateinit var motor1I180:ImageView
    private lateinit var motor1I135:ImageView
    private lateinit var motor1I90:ImageView
    private lateinit var motor1I45:ImageView
    private lateinit var motor1I0:ImageView

    private var open:String = "open"
    private var close:String = "close"
    private var turnOn:String = "turnOn"
    private var turnOff:String = "turnOff"
    private var motor3Send0:String = "motor3Send0"
    private var motor3Send45:String = "motor3Send45"
    private var motor3Send90:String = "motor3Send90"
    private var motor2Send0:String = "motor2Send0"
    private var motor2Send45:String = "motor2Send45"
    private var motor2Send90:String = "motor2Send90"
    private var motor1Send180:String = "motor1Send180"
    private var motor1Send135:String = "motor1Send135"
    private var motor1Send90:String = "motor1Send90"
    private var motor1Send45:String = "motor1Send45"
    private var motor1Send0:String = "motor1Send0"


    private fun init() {
        connect = findViewById(R.id.button_connect)
        openHand = findViewById(R.id.button_open)
        closeHand = findViewById(R.id.button_close)
        motor4TurnOn = findViewById(R.id.servo4_turnOn)
        motor4TurnOff = findViewById(R.id.servo4_turnOff)
        motor3I0 = findViewById(R.id.Servo3_0)
        motor3I45 = findViewById(R.id.Servo3_45)
        motor3I90 = findViewById(R.id.Servo3_90)
        motor2I0 = findViewById(R.id.Servo2_0)
        motor2I45 = findViewById(R.id.Servo2_45)
        motor2I90 = findViewById(R.id.Servo2_90)
        motor1I180 = findViewById(R.id.Servo1_180)
        motor1I135 = findViewById(R.id.Servo1_135)
        motor1I90 = findViewById(R.id.Servo1_90)
        motor1I45 = findViewById(R.id.Servo1_45)
        motor1I0 = findViewById(R.id.Servo1_0)

     }
}

private fun controlRobot(direction: String) {
    val url = URL("http://192.168.4.1/controlRobot")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "application/json")
    connection.doOutput = true

    val data = """
{
    "direction": "$direction"
}
"""
    val outputStream = connection.outputStream
    outputStream.write(data.toByteArray())
    outputStream.close()

    val responseCode = connection.responseCode
    /*
    if (responseCode == HttpURLConnection.HTTP_OK) {
        // Request was successful
    } else {
        // Request failed
    }
    */
}
