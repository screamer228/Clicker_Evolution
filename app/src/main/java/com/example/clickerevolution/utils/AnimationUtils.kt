package com.example.clickerevolution.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.clickerevolution.R
import java.util.Random

object AnimationUtils {

    private const val EDGE_PADDING_DP = 65 // Отступ от краев контейнера в dp

    fun startHomeCoinAnimation(container: ViewGroup) {
        val coin = ImageView(container.context).apply {
            setImageResource(R.drawable.ic_coin)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Получаем размеры контейнера
        val containerWidth = container.width
        val containerHeight = container.height

        // Получаем размеры монетки
        val coinWidth = coin.width
        val coinHeight = coin.height

        // Вычисляем отступ от краев контейнера
        val edgePadding = EDGE_PADDING_DP * container.resources.displayMetrics.density

        // Генерируем случайные начальные координаты с учетом отступа
        val random = Random()
        val startX = edgePadding + random.nextFloat() * (containerWidth - coinWidth - 2 * edgePadding)
        val startY = edgePadding + random.nextFloat() * (containerHeight - coinHeight - 2 * edgePadding)

        // Устанавливаем начальные координаты монетки
        coin.translationX = startX
        coin.translationY = startY

        container.addView(coin)

        // Анимация для движения монетки вверх и затухания
        val translationY = ObjectAnimator.ofFloat(coin, "translationY", startY, startY - (100..200).random().toFloat())
        val alpha = ObjectAnimator.ofFloat(coin, "alpha", 1f, 0f).apply {
            startDelay = 400 // Задержка перед началом затухания
        }

        val animatorSet = AnimatorSet().apply {
            playTogether(translationY, alpha)
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
        }

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                container.removeView(coin)
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animatorSet.start()
    }

    fun View.setTouchAnimation(percentage: Float) {
        this.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Вызов функции анимации уменьшения размера при нажатии
                    shrinkView(v, percentage)
                    false
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Вызов функции анимации восстановления размера при отпускании
                    resetViewSize(v)
                    false
                }

                else -> false
            }
        }
    }

    // Функция для уменьшения размера View
    fun shrinkView(view: View, percentage: Float) {
        view.animate()
            .scaleX(percentage)  // Уменьшение размера по оси X
            .scaleY(percentage)  // Уменьшение размера по оси Y
            .setDuration(50)  // Длительность анимации
            .start()
    }

    // Функция для восстановления исходного размера View
    fun resetViewSize(view: View) {
        view.animate()
            .scaleX(1f)  // Восстановление исходного размера по оси X
            .scaleY(1f)  // Восстановление исходного размера по оси Y
            .setDuration(50)  // Длительность анимации
            .start()
    }

    fun startHomeClickAnimation(view: View) {
        view.animate().apply {
            duration = 50
            scaleXBy(1.0F)
            scaleX(0.9F)
            scaleYBy(1.0F)
            scaleY(0.9F)
        }.withEndAction {
            view.animate().apply {
                duration = 50
                scaleXBy(0.9F)
                scaleX(1.0F)
                scaleYBy(0.9F)
                scaleY(1.0F)
            }
        }
    }

    fun startHostDiamondAnimation(view: View) {
        view.animate().apply {
            duration = 150
            scaleXBy(1.0F)
            scaleX(1.3F)
            scaleYBy(1.0F)
            scaleY(1.3F)
        }.withEndAction {
            view.animate().apply {
                duration = 150
                scaleXBy(1.3F)
                scaleX(1.0F)
                scaleYBy(1.3F)
                scaleY(1.0F)
            }
        }
    }
}