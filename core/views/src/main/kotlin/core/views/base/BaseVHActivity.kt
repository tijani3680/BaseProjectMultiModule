package core.views.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import core.views.util.OnBackPressListener
import core.views.util.instancestate.InstanceStateHandler
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseVHActivity<VH : ViewHandler<*>> :
    AppCompatActivity(),
    HasAndroidInjector,
    InstanceStateHandler {

    lateinit var viewHandler: VH

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    final override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        restoreBundle(savedInstanceState)
        super.onCreate(savedInstanceState)
        onConfigureWindow(savedInstanceState)
        viewHandler = onCreateViewHandler(savedInstanceState)
        setContentView(viewHandler.getRoot())
        onViewCreated(savedInstanceState)
    }

    protected open fun onConfigureWindow(savedInstanceState: Bundle?) {}

    protected open fun onViewCreated(savedInstanceState: Bundle?) {}

    protected abstract fun onCreateViewHandler(savedInstanceState: Bundle?): VH

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::viewHandler.isInitialized)
            viewHandler.saveBundle(outState)
        saveBundle(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewHandler.destroy()
    }

    // TODO: Remove
    override fun onBackPressed() {
        val fragment = (supportFragmentManager.primaryNavigationFragment as? NavHostFragment)
            ?.childFragmentManager?.primaryNavigationFragment
            ?: supportFragmentManager.primaryNavigationFragment
        if ((fragment as? OnBackPressListener)?.onBackPressed() != true) super.onBackPressed()
    }

    override fun androidInjector() = dispatchingAndroidInjector
}
