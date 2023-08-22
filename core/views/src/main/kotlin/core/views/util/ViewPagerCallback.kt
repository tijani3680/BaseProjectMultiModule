package core.views.util

import androidx.viewpager2.widget.ViewPager2

open class ViewPagerCallback : ViewPager2.OnPageChangeCallback() {

    interface PageSelectedListener {
        fun onPageSelected(position: Int)
    }

    interface PageScrolledListener {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

    interface PageScrollStateChanged {
        fun onPageScrollStateChanged(state: Int)
    }

    private val pageSelectedListeners = mutableListOf<(PageSelectedListener)>()

    fun addPageSelectedListener(listener: PageSelectedListener) {
        pageSelectedListeners.add(listener)
    }

    fun addPageSelectedListener(listener: (position: Int) -> Unit) {
        pageSelectedListeners.add(object : PageSelectedListener {
            override fun onPageSelected(position: Int) {
                listener(position)
            }
        })
    }

    fun removePageSelectedListener(listener: PageSelectedListener) {
        pageSelectedListeners.remove(listener)
    }

    private val pageScrolledListeners = mutableListOf<(PageScrolledListener)>()

    fun addPageScrolledListener(listener: PageScrolledListener) {
        pageScrolledListeners.add(listener)
    }

    fun addPageScrolledListener(
        listener: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit
    ) {
        pageScrolledListeners.add(object : PageScrolledListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                listener(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    fun removePageScrolledListener(listener: PageScrolledListener) {
        pageScrolledListeners.remove(listener)
    }

    private val pageScrollStateChanged = mutableListOf<(PageScrollStateChanged)>()

    fun addPageScrollStateChanged(listener: PageScrollStateChanged) {
        pageScrollStateChanged.add(listener)
    }
    fun addPageScrollStateChanged(listener: (state: Int) -> Unit) {
        pageScrollStateChanged.add(object : PageScrollStateChanged {
            override fun onPageScrollStateChanged(state: Int) {
                listener(state)
            }
        })
    }

    fun removePageScrollStateChanged(listener: PageScrollStateChanged) {
        pageScrollStateChanged.remove(listener)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        for (listener in pageScrolledListeners) {
            listener.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        for (listener in pageSelectedListeners) {
            listener.onPageSelected(position)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        super.onPageScrollStateChanged(state)
        for (listener in pageScrollStateChanged) {
            listener.onPageScrollStateChanged(state)
        }
    }
}
