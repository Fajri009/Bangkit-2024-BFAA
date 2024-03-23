package com.fajri.bangkit_2024_bfaa.Testing.UnitTest

import com.fajri.bangkit_2024_bfaa.Testing.CuboidModel
import com.fajri.bangkit_2024_bfaa.Testing.MainUnitTestViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class MainUnitTestViewModelTest {
    private lateinit var viewModel: MainUnitTestViewModel
    private lateinit var cuboidModel: CuboidModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    // dummy hasil
    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before() {
        cuboidModel = mock(CuboidModel::class.java)
        viewModel = MainUnitTestViewModel(cuboidModel)
    }


    //JUnit
    @Test
    fun testVolume() {
        cuboidModel = CuboidModel()
        viewModel = MainUnitTestViewModel(cuboidModel)
        viewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = viewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testCircumference() {
        cuboidModel = CuboidModel()
        viewModel = MainUnitTestViewModel(cuboidModel)
        viewModel.save(dummyWidth, dummyLength, dummyHeight)
        val circumference = viewModel.getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testSurfaceArea() {
        cuboidModel = CuboidModel()
        viewModel = MainUnitTestViewModel(cuboidModel)
        viewModel.save(dummyWidth, dummyLength, dummyHeight)
        val surfaceArea = viewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    // Mock
    @Test
    fun testMockVolume() {
        `when`(viewModel.getVolume()).thenReturn(dummyVolume)
        val volume = viewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testMockCircumference() {
        `when`(viewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = viewModel.getCircumference()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testMockSurfaceArea() {
        `when`(viewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = viewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}