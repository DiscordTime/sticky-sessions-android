package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before

class ListSessionsTest {

    private lateinit var mSessionRepositoryMock: SessionRepository

    @Before
    fun setUp() {
        mSessionRepositoryMock = mock()
    }

}