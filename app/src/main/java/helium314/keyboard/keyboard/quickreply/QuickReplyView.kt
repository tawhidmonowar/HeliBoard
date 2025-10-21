package helium314.keyboard.keyboard.quickreply

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import helium314.keyboard.core.util.ResultWrapper
import helium314.keyboard.feature.ai.domain.usecase.QuickReplyUseCase
import helium314.keyboard.latin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.context.GlobalContext
import java.util.Locale

@SuppressLint("CustomViewStyleable")
class QuickReplyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val msg1: TextView by lazy { findViewById<TextView>(R.id.message_1) }
    private val msg2: TextView by lazy { findViewById<TextView>(R.id.message_2) }
    private val msg3: TextView by lazy { findViewById<TextView>(R.id.message_3) }

    private val DEFAULT_MESSAGE_1 = "I want to go"
    private val DEFAULT_MESSAGE_2 = "I want to eat"
    private val DEFAULT_MESSAGE_3 = "I want to drink"

    fun populateQuickReplyTexts() {
        msg1.text = DEFAULT_MESSAGE_1
        msg2.text = DEFAULT_MESSAGE_2
        msg3.text = DEFAULT_MESSAGE_3

        sendDataAndRecive(DEFAULT_MESSAGE_1, "English")
    }

    fun sendDataAndRecive(message: String, language: String = Locale.getDefault().language) {
        val useCase: QuickReplyUseCase = GlobalContext.get().get()

        GlobalScope.launch(Dispatchers.Main) {
            // show loading state
            msg1.text = "Loading..."
            msg2.text = "Loading..."
            msg3.text = "Loading..."

            val result = withContext(Dispatchers.IO) {
                useCase.invoke(message, language)
            }

            when (result) {
                is ResultWrapper.Success -> {
                    val quickReply = result.data
                    val t1 = quickReply.positive.getOrNull(0)
                        ?: quickReply.neutral.getOrNull(0)
                        ?: quickReply.negative.getOrNull(0)
                        ?: DEFAULT_MESSAGE_1
                    val t2 = quickReply.positive.getOrNull(1)
                        ?: quickReply.neutral.getOrNull(1)
                        ?: quickReply.negative.getOrNull(1)
                        ?: DEFAULT_MESSAGE_2
                    val t3 = quickReply.positive.getOrNull(2)
                        ?: quickReply.neutral.getOrNull(2)
                        ?: quickReply.negative.getOrNull(2)
                        ?: DEFAULT_MESSAGE_3

                    msg1.text = t1
                    msg2.text = t2
                    msg3.text = t3
                }
                is ResultWrapper.Failure -> {
                    msg1.text = "Error loading quick replies"
                    msg2.text = ""
                    msg3.text = ""
                }
                is ResultWrapper.Loading -> {
                    // no-op: handled by pre-loading state above
                }
            }
        }
    }
}
