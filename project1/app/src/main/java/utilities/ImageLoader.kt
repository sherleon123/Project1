package utilities


import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.VIBRATOR_MANAGER_SERVICE
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.example.project1.R
import java.lang.ref.WeakReference

class ImageLoader private constructor(context: Context) {
    private val contextRef = WeakReference(context)

    fun loadImage(source: Drawable,
                  imageView: ImageView,
                  placeholder: Int = R.drawable.heart){
        contextRef.get()?.let {
                context ->
            Glide
                .with(context)
                .load(source)
                .centerCrop()
                .placeholder(placeholder)
                .into(imageView);
        }
    }

    fun loadImage(source: String,
                  imageView: ImageView,
                  placeholder: Int = R.drawable.heart){
        contextRef.get()?.let {
                context ->
            Glide
                .with(context)
                .load(source)
                .centerCrop()
                .placeholder(placeholder)
                .into(imageView);
        }
    }

    companion object {
        @Volatile
        private var instance: ImageLoader? = null

        fun init(context: Context): ImageLoader {
            return instance ?: synchronized(this) {
                instance ?: ImageLoader(context).also { instance = it }
            }
        }

        fun getInstance(): ImageLoader {
            return instance ?: throw IllegalStateException(
                "ImageLoader must be initialized by calling init(context) before use."
            )
        }
    }

}