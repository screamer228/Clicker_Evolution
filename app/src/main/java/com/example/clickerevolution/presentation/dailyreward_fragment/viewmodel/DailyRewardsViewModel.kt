package com.example.clickerevolution.presentation.dailyreward_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clickerevolution.common.Price
import com.example.clickerevolution.common.Currency
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
        Log.d("DailyRewards", "DailyViewModel: checkDailyLogin() - $streak")
    }

    fun claimDailyReward() {
        prefsRepository.setDailyRewardAvailable(false)
        _dailyRewardAvailable.value = false
        Log.d("DailyRewards", "DailyViewModel: claimDailyRewards() - ${_dailyRewardAvailable.value}")
    }

    private fun isSameDay(currentDate: Long, lastLoginDate: Long): Boolean {
        Log.d("DailyRewards", "DailyViewModel: isSameDay()")

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

    fun generateReward(streak: Int): Price {
        return when (streak) {
            1 -> Price(Currency.GOLD, 50)
            2 -> Price(Currency.GOLD, 100)
            3 -> Price(Currency.GOLD, 150)
            4 -> Price(Currency.GOLD, 200)
            5 -> Price(Currency.GOLD, 250)
            6 -> Price(Currency.GOLD, 300)
            7 -> Price(Currency.DIAMOND, 50)
            else -> Price(Currency.GOLD, 50)
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