package br.org.cesar.discordtime.stickysessions.navigation

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException
import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.router.Router
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.ViewStarter
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RouterTest {

    private lateinit var mockRouteRepository: IRouteRepository
    private lateinit var mockViewStarter: ViewStarter
    private lateinit var router: IRouter
    val validRoute = Route(
            ViewNames.LOBBY_ACTIVITY,
            IRouter.CREATED_SESSION,
            ViewNames.SESSION_ACTIVITY,
            false)

    @Before
    fun setUp(){
        mockRouteRepository = mock()
        mockViewStarter = mock()
        router = Router(mockRouteRepository)
    }

    @Test(expected = InvalidRouteException::class)
    fun `should throw InvalidRouteException if the route is invalid`() {
        stubRouteRepositoryFind(ViewNames.LOBBY_ACTIVITY, IRouter.CREATED_SESSION)
        router.goNext(ViewNames.LOBBY_ACTIVITY, IRouter.ENTERED_SESSION)
    }

    @Test
    fun `should call goNext if the route is valid`() {
        stubRouteRepositoryFind(ViewNames.LOBBY_ACTIVITY, IRouter.CREATED_SESSION)
        var route = router.goNext(ViewNames.LOBBY_ACTIVITY, IRouter.CREATED_SESSION)
        assertEquals(validRoute, route);
    }


    private fun stubRouteRepositoryFind(from:String, event:String) {
        whenever(mockRouteRepository.findRoute(from, event))
                .thenReturn(validRoute)
    }

}