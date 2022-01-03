package com.example.bmical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.bmical.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadData()

        binding.resultButton.setOnClickListener {
            if (binding.weightEditText.text.isNotBlank() && binding.heightEditText.text.isNotBlank()) {

                // 마지막에 입력한 내용 저장
                saveData(
                    binding.heightEditText.text.toString().toFloat(),
                    binding.weightEditText.text.toString().toFloat()
                )

                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra(
                        "weight",
                        binding.weightEditText.text.toString().toFloat()
                    ) // 입력받은 문자열을 Float형으로 변경해 인텐트에 데이터 저장
                    putExtra("height", binding.heightEditText.text.toString().toFloat())
                }
                startActivity(intent)
            }
        }
    }

    private fun saveData(height: Float, weight: Float) {    // 데이터 저장 메서드
        val pref = PreferenceManager.getDefaultSharedPreferences(this)  // 객체 얻기
        val editor = pref.edit()                                // 에디터 객체 얻기 (프리퍼런스에 데이터 얻음)

        editor.putFloat("KEY_HEIGHT", height)   // 쌍으로 키-값 형태 저장
            .putFloat("KEY_WEIGHT", weight)
            .apply()                            // 설정값 반영
    }

    private fun loadData() {
        val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this) // 프리퍼런스 객체 얻기
        val height = pref.getFloat("KEY_HEIGHT", 0f)    // 저장된 값 불러오기
        val weight = pref.getFloat("KEY_WEIGHT", 0f)

        if(height != 0f && weight != 0f) {
            binding.heightEditText.setText(height.toString())
            binding.weightEditText.setText(weight.toString())
        }
    }
}