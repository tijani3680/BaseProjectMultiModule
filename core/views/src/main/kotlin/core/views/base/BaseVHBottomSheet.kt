package core.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import core.views.util.OnBackPressListener
import core.views.util.instancestate.InstanceStateHandler
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseVHBottomSheet<VH : ViewHandler<*>, Args : NavArgs>(
    private val navArgsClass: KClass<Args>
) : BottomSheetDialogFragment(), HasAndroidInjector, OnBackPressListener, InstanceStateHandler {

    lateinit var viewHandler: VH

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    protected val args: Args by NavArgsLazy(navArgsClass) {
        requireArguments()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        restoreBundle(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = let {
        viewHandler = onCreateViewHandler(inflater, container, savedInstanceState)
        viewHandler.getRoot()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::viewHandler.isInitialized)
            viewHandler.saveBundle(outState)
        saveBundle(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.destroy()
    }

    override fun onBackPressed(): Boolean {
        val childFragment = (childFragmentManager.primaryNavigationFragment as? NavHostFragment)
            ?.childFragmentManager?.primaryNavigationFragment
            ?: childFragmentManager.primaryNavigationFragment
        return (childFragment as? OnBackPressListener)?.onBackPressed() ?: false
    }

    override fun androidInjector() = dispatchingAndroidInjector

    protected abstract fun onCreateViewHandler(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VH
}
