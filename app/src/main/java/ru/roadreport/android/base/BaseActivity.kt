package ru.roadreport.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<VB : ViewDataBinding>(val bindingFactory: (LayoutInflater) -> VB)
    : AppCompatActivity() {

    private var mViewBinding: VB? = null
    val binding get() = mViewBinding!!

    open fun setupViews() {}
    open fun observeData() {}

    /**
     * Нужен для переходов, в этот момент binding = null!!!!
     * */
    open fun configureActivity() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureActivity()
        mViewBinding = bindingFactory(layoutInflater)
        setupDecorView()
        setupViews()
        observeData()
        setContentView(binding.root)
    }

    private fun setupDecorView() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

//    //Свернуть клавиатуру по нажатии на пустое место экрана
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if (event.action == MotionEvent.ACTION_DOWN) { //Нажатие пальцем по экрану
//            val v = currentFocus //Получить текущий фокус
//            if (v is EditText) {   //Установлен ли фокус в поле EditText
//                val outRect = Rect() //Создание экзампляра класса прямоугольника
//                v.getGlobalVisibleRect(outRect)
//                val inputX = event.rawX.toInt()
//                val inputY = event.rawY.toInt()
//                if (!outRect.contains(inputX, inputY)
//                ) {    //Если коориднаты места нажатия не находятся в координатах прямоугольников EditText, то
//                    v.clearFocus() //Очисть фокус с поля EditText
//                    hideKeyboard(v) //Т.к. фокус потерян, то убрать клавиатуру
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event)
//    }
}