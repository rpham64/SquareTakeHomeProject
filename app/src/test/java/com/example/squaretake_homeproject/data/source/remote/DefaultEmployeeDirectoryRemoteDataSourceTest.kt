package com.example.squaretake_homeproject.data.source.remote

import android.util.MalformedJsonException
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeType
import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
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
            onBlocking { getEmployeesList() } doReturn Result.success(testEmployeesList)
            onBlocking { getMalformedEmployeesList() } doReturn Result.failure(MalformedJsonException("Expected EOF"))
            onBlocking { getEmptyEmployeesList() } doReturn Result.success(emptyList())
        }
        remoteDataSource = DefaultEmployeeDirectoryRemoteDataSource(service)
    }

    @Test
    fun getEmployeesList_success() {
        runTest {
            val expectedResult = Result.success(testEmployeesList)
            val actual = remoteDataSource.getEmployeesList()

            assert(actual.isSuccess)
            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_emptyList() {
        runTest {
            val expectedResult = Result.success<List<Employee>>(emptyList())
            whenever(service.getEmployeesList()).thenReturn(expectedResult)

            val actual = remoteDataSource.getEmployeesList()

            assert(actual.isSuccess)
            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_failure() {
        runTest {
            val error = RuntimeException("error returned from backend")
            whenever(service.getEmployeesList()).thenReturn(Result.failure(error))

            val actual = remoteDataSource.getEmployeesList()

            assert(actual.isFailure)
            assert(actual.exceptionOrNull()!! == error)
        }
    }

    @Test
    fun getMalformedEmployeesList() {
        runTest {
            val actual = remoteDataSource.getMalformedEmployeesList()

            assert(actual.isFailure)
            assert(actual.exceptionOrNull()!! is MalformedJsonException)
        }
    }

    @Test
    fun getEmptyEmployeesList() {
        runTest {
            val expectedResult = Result.success<List<Employee>>(emptyList())
            val actual = remoteDataSource.getEmptyEmployeesList()

            assert(actual.isSuccess)
            assert(actual == expectedResult)
        }
    }
}

private val testEmployeesList: List<Employee> = listOf(
    Employee(
        uuid = UUID.randomUUID().toString(),
        name = "Rudolf",
        phoneNumber = "4081234567",
        email = "testemail@gmail.com",
        biography = "Hello my name is Rudolf",
        photoSmall = "small photo",
        photoLarge = "large photo",
        team = "team awesome",
        employeeType = EmployeeType.FULL_TIME
    )
)