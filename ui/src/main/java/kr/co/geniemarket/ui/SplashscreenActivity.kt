package kr.co.geniemarket.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kr.co.geniemarket.core.GMLog
import kr.co.geniemarket.ui.databinding.ActivitySplashscreenBinding

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    companion object {
        private val TAG : String = SplashscreenActivity::class.java.simpleName
    }

    private var mBinding: ActivitySplashscreenBinding? = null

    private val binding get() = mBinding!!

    private lateinit var viewModelProvider : ViewModelProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GMLog.setLogLevel(GMLog.LogLevel.Verbose)
        GMLog.i("GenieMarket version: ${BuildConfig.GENIEMARKET_VERSION}")

        viewModelProvider = ViewModelProvider(
            this as ViewModelStoreOwner,
            ViewModelProvider.NewInstanceFactory())

        startAnimation()
    }

    private fun startAnimation() {
        var anim : Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_alpha_color)
        anim.reset()
        binding.linLay.clearAnimation()
        binding.linLay.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate)
        anim.reset()
        binding.splash.clearAnimation()
        binding.splash.startAnimation(anim)

        Thread() {
            try {
                var waited = 0
                while (waited < 2000) {
                    Thread.sleep(100)
                    waited += 100
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                this@SplashscreenActivity.finish()
            }catch (ex : Exception) {
                GMLog.e(ex.toString(), ex)
            }finally {
                this@SplashscreenActivity.finish()
            }
        }.start()
    }
}