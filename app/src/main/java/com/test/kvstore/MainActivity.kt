package com.test.kvstore

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.test.kvstore.`object`.Demo
import com.test.kvstore.databinding.ActivityMainBinding
import com.test.kvstore.databinding.ActivityMainBinding.inflate
import io.fastkv.fastkvdemo.util.runBlock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val serialChannel = Channel<Any>(1)
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        initClick()

    }

    private fun initClick() {
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        binding.tipsTv.text = "等待"
        stopButton()
        when (p0) {
            binding.button1 -> {
                upTOData()
            }
            binding.button2 -> {
                readFromData()
            }
        }

    }

    private fun upTOData() {
        serialChannel.runBlock {

            Demo.start(true)
            GlobalScope.launch(Dispatchers.Main) {
                binding.tipsTv.text = getString(R.string.test_tips)
                startButton()
            }
        }
    }

    private fun readFromData() {

        serialChannel.runBlock {
            Demo.start(false)
            GlobalScope.launch(Dispatchers.Main) {
                binding.tipsTv.text = getString(R.string.test_tips)
                startButton()
            }
        }
    }

    private fun stopButton() {
        binding.tipsTv.setTextColor(Color.parseColor("#FFFF8247"))
        binding.tipsTvs.setTextColor(Color.parseColor("#FFFF8247"))
        binding.button1.isEnabled = false
        binding.tipsTv.setTextColor(Color.parseColor("#FFFF8247"))
        binding.button2.isEnabled = false
    }

    private fun startButton() {
        binding.tipsTv.setTextColor(Color.parseColor("#FF009900"))
        binding.tipsTvs.setTextColor(Color.parseColor("#FF009900"))
        binding.button1.isEnabled = true
        binding.tipsTv.setTextColor(Color.parseColor("#FF009900"))
        binding.button2.isEnabled = true
    }

}