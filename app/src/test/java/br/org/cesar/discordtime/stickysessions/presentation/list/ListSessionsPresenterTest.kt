package br.org.cesar.discordtime.stickysessions.presentation.list

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.logger.Logger
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before

class ListSessionsPresenterTest {
    private lateinit var listSessionsPresenter : ListSessionsPresenter
    private lateinit var mockLisSessions: IObservableUseCase<Void, List<Session>>
    private lateinit var mockLogger: Logger
    private lateinit var mockIBundleFactory: IBundleFactory
    private lateinit var mockIRouter: IRouter
    private lateinit var mockView: ListSessionsContract.View
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Session>>>

    @Before
    fun setUp() {
        mockLisSessions = mock()
        mockIRouter = mock()
        mockLogger = mock()
        mockIBundleFactory = mock()
        mockView = mock()
        listSessionsPresenter = ListSessionsPresenter(mockLisSessions, mockIRouter,
                mockLogger, mockIBundleFactory)

        captor = argumentCaptor<DisposableSingleObserver<List<Session>>>()
    }


}