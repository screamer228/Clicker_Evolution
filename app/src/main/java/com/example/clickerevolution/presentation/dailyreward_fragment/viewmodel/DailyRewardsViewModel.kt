package com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.CurrencyType
import com.example.clickerevolution.common.RewardButtonState
import com.example.clickerevolution.data.repository.prefs.PrefsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

class DailyRewardsViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {


    private val _loginStreak = MutableStateFlow(0)
    val loginStreak: StateFlow<Int> = _loginStreak.asStateFlow()

    private val _dailyRewardAvailable = MutableStateFlow(false)
    val dailyRewardAvailable: StateFlow<Boolean> = _dailyRewardAvailable.asStateFlow()

    init {
        checkDailyLogin()
//        Log.d()
    }

    fun checkDailyLogin() {
        val lastLoginDate = prefsRepository.getLastExitTime()
        val currentDate = System.currentTimeMillis()

        if (isSameDay(currentDate, lastLoginDate)) {
            _loginStreak.value = prefsRepository.getLoginStreak()
            _dailyRewardAvailable.value = prefsRepository.isDailyRewardAvailable()
            return
        }

        val streak = if (isNextDay(currentDate, lastLoginDate)) {
            prefsRepository.getLoginStreak() + 1
        } else {
            1 // Сброс серии, если не на следующий день
        }

        prefsRepository.saveLastExitTime(currentDate)
        prefsRepository.saveLoginStreak(streak)
        prefsRepository.setDailyRewardAvailable(true)

        _loginStreak.value = streak
        _dailyRewardAvailable.value = true
        Log.d("DailyRewardsFragment", "viewModel: checkDailyLogin() - $streak")
    }

    fun claimDailyReward() {
        prefsRepository.setDailyRewardAvailable(false)
        _dailyRewardAvailable.value = false
    }

    private fun isSameDay(currentDate: Long, lastLoginDate: Long): Boolean {
        Log.d("DailyRewardsFragment", "viewModel: isSameDay()")

        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = currentDate

        val lastLoginCalendar = Calendar.getInstance()
        lastLoginCalendar.timeInMillis = lastLoginDate

        return currentCalendar.get(Calendar.YEAR) == lastLoginCalendar.get(Calendar.YEAR) &&
                currentCalendar.get(Calendar.DAY_OF_YEAR) == lastLoginCalendar.get(Calendar.DAY_OF_YEAR)
    }

    private fun isNextDay(currentDate: Long, lastLoginDate: Long): Boolean {
        Log.d("DailyRewardsFragment", "viewModel: isNextDay()")

        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = currentDate

        val lastLoginCalendar = Calendar.getInstance()
        lastLoginCalendar.timeInMillis = lastLoginDate

        lastLoginCalendar.add(Calendar.DAY_OF_YEAR, 1)

        return currentCalendar.get(Calendar.YEAR) == lastLoginCalendar.get(Calendar.YEAR) &&
                currentCalendar.get(Calendar.DAY_OF_YEAR) == lastLoginCalendar.get(Calendar.DAY_OF_YEAR)
    }

    fun generateReward(streak: Int): Currency {
        return when (streak) {
            1 -> Currency(CurrencyType.GOLD, 50)
            2 -> Currency(CurrencyType.GOLD, 100)
            3 -> Currency(CurrencyType.GOLD, 150)
            4 -> Currency(CurrencyType.GOLD, 200)
            5 -> Currency(CurrencyType.GOLD, 250)
            6 -> Currency(CurrencyType.GOLD, 300)
            7 -> Currency(CurrencyType.DIAMOND, 50)
            else -> Currency(CurrencyType.GOLD, 50)
        }
    }

    fun getButtonState(day: Int, currentStreak: Int, rewardAvailable: Boolean): RewardButtonState {
        return when {
            currentStreak > day -> RewardButtonState.CLAIMED
            currentStreak == day && rewardAvailable -> RewardButtonState.CLAIM
            else -> RewardButtonState.NOT_AVAILABLE
        }
    }
}