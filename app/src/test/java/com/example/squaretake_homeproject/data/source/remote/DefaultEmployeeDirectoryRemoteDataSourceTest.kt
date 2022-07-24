package com.example.squaretake_homeproject.data.source.remote

import android.util.MalformedJsonException
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeListResult
import com.example.squaretake_homeproject.data.model.EmployeeType
import com.example.squaretake_homeproject.data.model.Response
import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultEmployeeDirectoryRemoteDataSourceTest {

    private lateinit var service: EmployeeDirectoryRetrofitService

    private lateinit var remoteDataSource: EmployeeDirectoryRemoteDataSource

    @Before
    fun setUp() {
        service = mock {
            onBlocking { getEmployeesList() } doReturn testResponse
            onBlocking { getMalformedEmployeesList() } doAnswer { throw MalformedJsonException("Expected EOF") }
            onBlocking { getEmptyEmployeesList() } doReturn testResponse.copy(emptyList())
        }
        remoteDataSource = DefaultEmployeeDirectoryRemoteDataSource(service)
    }

    @Test
    fun getEmployeesList_success() {
        runTest {
            val expectedResult = EmployeeListResult.Success(testEmployeesList)
            val actual = remoteDataSource.getEmployeesList()

            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_emptyList() {
        runTest {
            val expectedResult = EmployeeListResult.Success(emptyList())
            whenever(service.getEmployeesList()).thenReturn(Response(emptyList()))

            val actual = remoteDataSource.getEmployeesList()

            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_failure() {
        runTest {
            val error = RuntimeException("error returned from backend")
            whenever(service.getEmployeesList()).thenThrow(error)

            val expectedResult = EmployeeListResult.Error(error)
            val actual = remoteDataSource.getEmployeesList()

            assert(actual == expectedResult)
        }
    }

    @Test
    fun getMalformedEmployeesList() {
        runTest {
            val expectedResult = EmployeeListResult.Error(MalformedJsonException("Expected EOF"))
            val actual = remoteDataSource.getMalformedEmployeesList()

            assert(actual is EmployeeListResult.Error)

            val errorResult: EmployeeListResult.Error = actual as EmployeeListResult.Error
            assert(errorResult.error is MalformedJsonException)
        }
    }

    @Test
    fun getEmptyEmployeesList() {
        runTest {
            val expectedResult = EmployeeListResult.Success(emptyList())
            val actual = remoteDataSource.getEmptyEmployeesList()

            assert(actual == expectedResult)
        }
    }
}

private val testEmployeesList: List<Employee> = listOf(
    Employee(
        id = UUID.randomUUID().toString(),
        name = "Rudolf",
        phoneNumber = "4081234567",
        email = "testemail@gmail.com",
        summary = "Hello my name is Rudolf",
        photoSmall = "small photo",
        photoLarge = "large photo",
        teamName = "team awesome",
        employeeType = EmployeeType.FULL_TIME
    )
)

private val testResponse: Response = Response(testEmployeesList)