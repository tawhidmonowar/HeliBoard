package helium314.keyboard.core.util

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure(
        val message: String,
        val isRewardAdRequired: Boolean = false,
        val isPremiumRequired: Boolean = false,
    ) : ResultWrapper<Nothing>()

    object Loading : ResultWrapper<Nothing>()
}

fun <T, R> ResultWrapper<T>.map(transform: (T) -> R): ResultWrapper<R> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(transform(this.data))
        is ResultWrapper.Failure -> ResultWrapper.Failure(this.message)
        is ResultWrapper.Loading -> ResultWrapper.Loading
    }
}
