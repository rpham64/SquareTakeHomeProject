package com.example.squaretake_homeproject.ui

import android.util.MalformedJsonException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeListResult
import com.example.squaretake_homeproject.data.model.EmployeeType
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeeDirectoryViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmployeeDirectoryRepository
    private lateinit var ioDispatcher: CoroutineDispatcher

    private lateinit var viewModel: EmployeeDirectoryViewModel

    @Before
    fun setUp() {
        repository = mock()
        ioDispatcher = StandardTestDispatcher()
        viewModel = EmployeeDirectoryViewModel(repository, ioDispatcher)
    }

    @Test
    fun fetchEmployees_success() {
        runTest(ioDispatcher) {
            val expectedResult = EmployeeListResult.Success(testEmployeesList)
            whenever(repository.getEmployeesList()).thenReturn(expectedResult)

            viewModel.fetchEmployeesList()

            viewModel.results.observeForTesting {
                // Yield test thread to let first livedata emission complete
                yield()

                // Check first value is Loading
                assertEquals(EmployeeListResult.Loading, viewModel.results.value)

                // Execute pending coroutines
                advanceUntilIdle()

                // Check second value is Success with non-empty employee list
                assertEquals(expectedResult, viewModel.results.value)
            }
        }
    }

    @Test
    fun fetchEmployees_empty() {
        runTest(ioDispatcher) {
            val expectedResult = EmployeeListResult.Success(emptyList())
            whenever(repository.getEmployeesList()).thenReturn(expectedResult)

            viewModel.fetchEmployeesList()

            viewModel.results.observeForTesting {
                // Yield test thread to let first livedata emission complete
                yield()

                // Check first value is Loading
                assertEquals(EmployeeListResult.Loading, viewModel.results.value)

                // Execute pending coroutines
                advanceUntilIdle()

                // Check second value is Success with empty employee list
                assertEquals(expectedResult, viewModel.results.value)
            }
        }
    }

    @Test
    fun fetchEmployees_malformedEmployeesList() {
        runTest(ioDispatcher) {
            val malformedJsonException = MalformedJsonException("backend returned a malformed list")
            val expectedResult = EmployeeListResult.Error(malformedJsonException)
            whenever(repository.getEmployeesList()).thenReturn(expectedResult)

            viewModel.fetchEmployeesList()

            viewModel.results.observeForTesting {
                // Yield test thread to let first livedata emission complete
                yield()

                // Check first value is Loading
                assertEquals(EmployeeListResult.Loading, viewModel.results.value)

                // Execute pending coroutines
                advanceUntilIdle()

                // Check second value is Error with MalformedJsonException
                assertEquals(expectedResult, viewModel.results.value)
            }
        }
    }

    @Test
    fun fetchEmployees_error() {
        runTest(ioDispatcher) {
            val error = Throwable("error returned from backend")
            val expectedResult = EmployeeListResult.Error(error)
            whenever(repository.getEmployeesList()).thenReturn(expectedResult)

            viewModel.fetchEmployeesList()

            viewModel.results.observeForTesting {
                // Yield test thread to let first livedata emission complete
                yield()

                // Check first value is Loading
                assertEquals(EmployeeListResult.Loading, viewModel.results.value)

                // Execute pending coroutines
                advanceUntilIdle()

                // Check second value is Error with Throwable
                assertEquals(expectedResult, viewModel.results.value)
            }
        }
    }

    /**
     * Observes a [LiveData] until the `block` is done executing.
     *
     * Taken from https://github.com/android/architecture-components-samples/blob/master/LiveDataSample/app/src/test/java/com/android/example/livedatabuilder/util/LiveDataTestUtil.kt#L61-L68
     */
    suspend fun <T> LiveData<T>.observeForTesting(block: suspend () -> Unit) {
        val observer = Observer<T> { }
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
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