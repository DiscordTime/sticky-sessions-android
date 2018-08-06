package br.org.cesar.discordtime.stickysessions.executor

import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ObservableUseCaseTest {

    private lateinit var observableUseCase: ObservableUseCase<Any, String>

    private lateinit var mockUseCase: UseCase<Any, String>
    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread

    private lateinit var mockDisposableSingleObserver: DisposableSingleObserver<Any>

    @Before
    fun setUp() {
        mockUseCase = mock()
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockDisposableSingleObserver = mock()

        observableUseCase = ObservableUseCase(mockUseCase,
                mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun `should dispose disposables`() {

        whenever(mockUseCase.execute("")).thenReturn(Single.just("mock"))
        whenever(mockPostExecutionThread.scheduler).thenReturn(Schedulers.trampoline())

        observableUseCase.execute(TestSingleObserver(), "")
        assertFalse(observableUseCase.disposables.isDisposed)
        observableUseCase.dispose()
        assertTrue(observableUseCase.disposables.isDisposed)

    }

    inner class TestSingleObserver: DisposableSingleObserver<Any>() {
        override fun onError(e: Throwable) {}
        override fun onSuccess(t: Any) {}
    }

}