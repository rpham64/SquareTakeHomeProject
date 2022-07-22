package com.example.squaretake_homeproject.data

import android.util.MalformedJsonException
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeType
import com.example.squaretake_homeproject.data.source.remote.EmployeeDirectoryRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultEmployeeDirectoryRepositoryTest {

    private lateinit var remoteDataSource: EmployeeDirectoryRemoteDataSource

    private lateinit var repository: EmployeeDirectoryRepository

    @Before
    fun setUp() {
        remoteDataSource = mock()
        repository = DefaultEmployeeDirectoryRepository(remoteDataSource)
    }

    @Test
    fun getEmployeesList_success() {
        runTest {
            val expectedResult = Result.success(testEmployeesList)
            whenever(remoteDataSource.getEmployeesList()).thenReturn(expectedResult)

            val actual = repository.getEmployeesList()

            assert(actual.isSuccess)
            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_emptyList() {
        runTest {
            val expectedResult = Result.success<List<Employee>>(emptyList())
            whenever(remoteDataSource.getEmployeesList()).thenReturn(expectedResult)

            val actual = repository.getEmployeesList()

            assert(actual.isSuccess)
            assert(actual == expectedResult)
        }
    }

    @Test
    fun getEmployeesList_failure() {
        runTest {
            val error = Throwable("error returned from backend")
            whenever(remoteDataSource.getEmployeesList()).thenReturn(Result.failure(error))

            val actual = repository.getEmployeesList()

            assert(actual.isFailure)
            assert(actual.exceptionOrNull()!! == error)
        }
    }

    @Test
    fun getMalformedEmployeesList() {
        runTest {
            val error = MalformedJsonException("Expected EOF at line 1")
            whenever(remoteDataSource.getEmployeesList()).thenReturn(Result.failure(error))

            val actual = repository.getEmployeesList()

            assert(actual.isFailure)
            assert(actual.exceptionOrNull()!! == error)
        }
    }

    @Test
    fun getEmptyEmployeesList() {
        runTest {
            val expectedResult = Result.success<List<Employee>>(emptyList())
            whenever(remoteDataSource.getEmptyEmployeesList()).thenReturn(expectedResult)

            val actual = repository.getEmptyEmployeesList()

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