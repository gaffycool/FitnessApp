package com.gaffy.apps.mvvm.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gaffy.apps.mvvm.presentation.feature.exercise.ExerciseFragment
import com.gaffy.apps.mvvm.presentation.feature.favorite.FavoriteFragment

const val EXERCISE_PAGE_INDEX = 0
const val FAVORITE_PAGE_INDEX = 1

class MvvmAppPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        EXERCISE_PAGE_INDEX to { ExerciseFragment() },
        FAVORITE_PAGE_INDEX to { FavoriteFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
