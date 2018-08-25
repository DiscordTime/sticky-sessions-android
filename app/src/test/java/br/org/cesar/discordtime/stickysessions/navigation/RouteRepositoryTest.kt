package br.org.cesar.discordtime.stickysessions.navigation

import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository
import br.org.cesar.discordtime.stickysessions.navigation.repository.RouteRepository
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RouteRepositoryTest {

    private lateinit var routeRepository: IRouteRepository

    @Before
    fun setUp(){
        routeRepository = RouteRepository()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw IllegalArgument if from is null `() {
        var route = routeRepository.findRoute(null, "")
        Assert.assertNull(route)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw IllegalArgument if event is null `() {
        var route = routeRepository.findRoute("", null)
        Assert.assertNull(route)
    }

    @Test
    fun `should return null if route is invalid `() {
        var route = routeRepository.findRoute("d", "d")
        Assert.assertNull(route)
    }

    @Test
    fun `should return SessionActivity class if from is LobbyActivity and event is createdSession `() {
        var route = routeRepository.findRoute(ViewNames.LOBBY_ACTIVITY, IRouter.CREATED_SESSION)
        Assert.assertEquals(ViewNames.SESSION_ACTIVITY, route.to)
    }

    @Test
    fun `should return SessionActivity class if from is LobbyActivity and event is enteredSession `() {
        var route = routeRepository.findRoute(ViewNames.LOBBY_ACTIVITY, IRouter.ENTERED_SESSION)
        Assert.assertEquals(ViewNames.SESSION_ACTIVITY, route.to)
    }

}